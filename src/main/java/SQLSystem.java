//package tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.javatuples.*;

import Tools.FunctionLibary;

public class SQLSystem {
    // Global Variables
    
    
    public SQLSystem(){};

    public void retrieveStationData(){};

    public static ArrayList<HashMap<String, String>> retrieveAverageMessurementData(String statement, Connection con){
        ArrayList<HashMap<String, String>> al = new ArrayList<>();

        // Lets create the statement and save the results
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
      
        // Loop trough the data
        while (rs.next()){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("stn", rs.getString("stn"));
            hm.put("Temperatuur", rs.getString("Temperatuur"));
            hm.put("Dauwpunt ", rs.getString("Dauwpunt"));
            hm.put("Luchtdruk_Station", rs.getString("Luchtdruk_Station"));
            hm.put("Luchtdruk_Zee", rs.getString("Luchtdruk_Zee"));
            hm.put("Zicht", rs.getString("Zicht"));
            hm.put("Windsnelheid" , rs.getString("Windsnelheid"));
            hm.put("Neerslag", rs.getString("Neerslag"));
            hm.put("Gebeurtenis", rs.getString("Gebeurtenis"));
            hm.put("Bewolking", rs.getString("Bewolking"));
            hm.put("Windrichting", rs.getString("Windrichting"));
            
            al.add(hm);
        }

        st.close();
    } catch (SQLException e) {
        FunctionLibary.errorCLI("The server encountered an SQL-error ");
        e.printStackTrace();
    }

        return al;
    }

    public static void addMessurement(HashMap<String, String> messurementData, Semaphore sem, Connection con){
        
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
            sem.tryAcquire(10, TimeUnit.SECONDS); // try to get a lock, with a time-out rate of 10 seconds

            // Get a connection with the database
            Statement stmt = con.createStatement();

            // makeup statement
            String theStatement = "INSERT INTO `Meting` (`stn`, `Datum`, `Tijd`, `Temperatuur`, `Dauwpunt`, `Luchtdruk_Station`, `Luchtdruk_Zee`, `Zicht`, `Windsnelheid`, `Neerslag`, `Sneeuwval`, `Gebeurtenis`, `Bewolking`, `Windrichting`)" + 
                    "VALUES ('" + station + "','" + datum + "','" + time + "','" + temp + "','" + dauwpunt + "','"
                    + luchtdrukstn + "','" + luchtdruksea + "','" + visabititeit + "','" + windsnelheid + "','"
                    + neerslag + "','" + sneeuw + "'," + gebeurtenis + ",'" + bewolking + "','" + windrichting + "')";

            //System.out.println(theStatement);
            FunctionLibary.debuggerOutput(Configuration.DEBUG_MODE, 2, theStatement, new Exception("DUMMY"));

            stmt.executeUpdate(theStatement);
            Thread.sleep(10);
            sem.release();
            
        } catch (SQLException e) {
                System.err.println("The server encountered an SQL-error ");
                e.printStackTrace();
        } catch (InterruptedException ie) {
            FunctionLibary.errorCLI("This thread couldn't complete the actions. This is either an resource problem or a timed out connection: " + ie.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }
}