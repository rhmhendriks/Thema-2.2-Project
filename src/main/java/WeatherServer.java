import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import Tools.ANSI;

public class WeatherServer {
    
    public WeatherServer() {
    };

    public static void main(String[] args) {
        Semaphore sem = new Semaphore(50, true);
        // Connection variable
        Socket conSock;
        try {
            ServerSocket srvSock = new ServerSocket(8299);
            System.out.println(ANSI.ANSI_BYELLOW + ANSI.ANSI_BOLD
                    + "The UNWDMI-server has been started, and is ready to use" + ANSI.ANSI_RESET);

            while (true) {
                conSock = srvSock.accept();
                Thread stationThread = new Thread(new StationThread(conSock, sem));
                stationThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
