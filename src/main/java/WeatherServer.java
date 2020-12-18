import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

import Tools.ANSI;
import Tools.FunctionLibary;

public class WeatherServer {

    public WeatherServer() {};

    public static void main(String[] args) {
        Semaphore sem = new Semaphore(50, true);
        // Connection variable
        Socket conSock;
        MessurementValidator mv = new MessurementValidator();

        try {
            ServerSocket srvSock = new ServerSocket(7789);

            // Select right driver
            Class.forName(Configuration.SQL_DRIVER);

            // setup create SQL connection
            // Setup the connection itself
            Connection con = DriverManager.getConnection(Configuration.SQL_URL, Configuration.SQL_USER,
                    Configuration.SQL_PASSWORD);

            System.out.println(ANSI.ANSI_BYELLOW + ANSI.ANSI_BOLD
                    + "The UNWDMI-server has been started, and is ready to use" + ANSI.ANSI_RESET);

            while (true) {
                conSock = srvSock.accept();
                Thread stationThread = new Thread(new StationThread(conSock, sem, con, mv));
                stationThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            FunctionLibary.debuggerOutput(Configuration.DEBUG_MODE, 2, "The SQL driver could not be found!", e);
        } catch (SQLException e) {
            FunctionLibary.debuggerOutput(Configuration.DEBUG_MODE, 2, "We encountered an SQL error: ", e);
        }
    }
}
