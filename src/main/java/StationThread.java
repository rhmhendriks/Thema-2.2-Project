import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import Tools.FunctionLibary;
import Tools.XMLParser;

public class StationThread implements Runnable {
    private Socket connectionSocket;
    private Semaphore sem;
    private XMLParser parser;
    private Connection con;
    private MessurementValidator mv;

    public StationThread(Socket connectionSocket, Semaphore sem, Connection con, MessurementValidator mv) {
        this.connectionSocket = connectionSocket;
        this.sem = sem;
        this.con = con;
        this.mv = mv;
    }

    public void run() {
        try {
            parser = new XMLParser();

            InputStream inputToServer = connectionSocket.getInputStream();

            // Define the string
            String string;

            // Translate the input
            BufferedReader input = new BufferedReader(new InputStreamReader(inputToServer));

            // Lets put the XML file together
            StringBuilder sb = new StringBuilder();
            sb.append("");
            string = input.readLine();

            // While we still have weatherdata we continue to assemble the XML
            while (!string.equals("</WEATHERDATA>")) {
                sb.append(string.trim()); // remove tabs and spacing in order to process the XML stream right later-on
                string = input.readLine();
            }

            if (string.equals("</WEATHERDATA>")) {
                sb.append(string.trim()); // add the ending-tag to make sure everything is complete
                processXMLstream(sb.toString(), sem, mv); // process the datastream
                connectionSocket.close(); // Close the connection when we are done. 
            }

        // THE CATCHES BELOW ARE USED TO INFORM THE USER ON A NEAT WAY THAT AN ERROR OCCURED
        } catch (IOException ioe) {
            FunctionLibary.errorCLI("We encountered an IOExeption during this thread! Below are mor details: ");
            ioe.printStackTrace();
        } 
    }

    private void processXMLstream(String XMLstream, Semaphore sem, MessurementValidator mv) {
        ArrayList<HashMap<String, String>> xmldata = parser.parseStream("MEASUREMENT", XMLstream); // convert the cluster of XML messuraments to HashMaps

        for (HashMap<String, String> hm : xmldata) {
            try {
                HashMap<String, String> nhm = mv.validate(hm, con);
                sem.acquire();
                SQLSystem.addMessurement(nhm, sem, con); // Add the data to the database
                sem.release();
            } catch (InterruptedException ie) {
                FunctionLibary.errorCLI("This thread couldn't complete the actions. This is either an resource problem or a timed out connection: " + ie.getMessage());
            }
            
            
        }
    }
}
