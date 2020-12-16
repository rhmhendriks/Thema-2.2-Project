import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Semaphore;

import Tools.XMLParser;

public class StationThread implements Runnable {
    private Socket connectionSocket;
    private Semaphore sem;
    private XMLParser parser;
    private SQLSystem sqls;

    public StationThread(Socket connectionSocket, Semaphore sem) {
        this.connectionSocket = connectionSocket;
        this.sem = sem;
    }

    public void run() {
        try {
            parser = new XMLParser();
            sqls = new SQLSystem();

            InputStream inputToServer = connectionSocket.getInputStream();
            OutputStream outputFromServer = connectionSocket.getOutputStream();

            // Define the string
            String string;

            // Translate the input
            PrintWriter output = new PrintWriter(outputFromServer, true);
            BufferedReader input = new BufferedReader(new InputStreamReader(inputToServer));

            // Lets put the XML file together
            StringBuilder sb = new StringBuilder();

            sb.append("");
            string = input.readLine();

            // Define string as the input
            while (!string.equals("</WEATHERDATA>")) {
                sb.append(string.trim());
                // System.out.println(string);
                string = input.readLine();
            }

            if (string.equals("</WEATHERDATA>")) {
                // Make sure that the stream is complete
                sb.append(string.trim());

                // process the data
                processXMLstream(sb.toString());

                // Close the connection
                connectionSocket.close();
                // JavaHTTPServer.semafoor.verhoog();
                System.out.println("De connectie is afgesloten.");
            }

            // If the connection fails
            // sem.acquire(); // try to get a lock

        } catch (IOException ioe) {
        }
        // catch (InterruptedException ie) { }
    }

    private void processXMLstream(String XMLstream) {
        ArrayList<HashMap<String, String>> xmldata = parser.parseStream("MEASUREMENT", XMLstream);

        for (HashMap<String, String> hm : xmldata) {
            sqls.addMessurement(hm);
        }
    }
}
