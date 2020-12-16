//package tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.*;

public class SQLSystem {
    // Global Variables
    Connection con;
    
    public SQLSystem(){};

    public void retrieveStationData(){};

    public void retrieveMessurementData(Pair<String, String> condition){};

    public void addMessurement(HashMap<String, String> messurementData){
        
        // Parse the hashmap to more usable variables
        String station = messurementData.get("STN");
        String datum = messurementData.get("DATE");
        String time = messurementData.get("TIME");
        String temp = messurementData.get("TEMP");
        String dauwpunt = messurementData.get("DEWP");
        String luchtdrukstn = messurementData.get("STP");
        String luchtdruksea = messurementData.get("SLP");
        String visabititeit = messurementData.get("VISIB");
        String windsnelheid = messurementData.get("WDSP");
        String neerslag = messurementData.get("PRCP");
        String sneeuw = messurementData.get("SNDP");
        String gebeurtenis = "0x" + messurementData.get("FRSHTT");
        String bewolking = messurementData.get("CLDC");
        String windrichting = messurementData.get("WNDDIR");

        try{
            // Select right driver 
            Class.forName(Configuration.SQL_DRIVER);

            con = DriverManager.getConnection(Configuration.SQL_URL, Configuration.SQL_USER, Configuration.SQL_PASSWORD);

            // Get a connection with the database
            Statement stmt = con.createStatement();

            // makeup statement
            String theStatement = "INSERT INTO `Meting` (`stn`, `Datum`, `Tijd`, `Temperatuur`, `Dauwpunt`, `Luchtdruk_Station`, `Luchtdruk_Zee`, `Zicht`, `Windsnelheid`, `Neerslag`, `Sneeuwval`, `Gebeurtenis`, `Bewolking`, `Windrichting`)" + 
            "VALUES (" + station + "," + datum + "," + time + "," + temp+ "," + dauwpunt + "," + luchtdrukstn + "," + luchtdruksea + "," + visabititeit + "," + windsnelheid + "," + neerslag + "," + sneeuw + "," + gebeurtenis + "," + bewolking + "," + windrichting + ")";

            System.out.println(theStatement);

            stmt.executeUpdate(theStatement);
            
        } catch (ClassNotFoundException e) {
                System.err.println("Could not find the database driver ");
                e.printStackTrace();
        } catch (SQLException e) {
                System.err.println("The server encountered an SQL-error ");
                e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }
}