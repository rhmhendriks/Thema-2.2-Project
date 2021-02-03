import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Tools.FunctionLibary;



public class MessurementValidator {
    Connection con;

    

    public MessurementValidator() {}

    public HashMap<String, String> validate(HashMap<String, String> data, Connection con){
        Iterator it = data.entrySet().iterator();
        HashMap<String, String> correctData = new HashMap<>();
        ArrayList<String> colmnToCheck = new ArrayList<>();
        Double avTemp = null;
        String stn = data.get("STN");

        // The statement to retrieve the data of the last 30 messurements
        String statement = "SELECT * FROM `Meting` WHERE `stn` =" + stn + " ORDER BY `Tijd` ASC LIMIT 30";

        // Execute the statement and get an hashmap the average values
        // NOTE the Date and Time are not used. 
        // System.out.println(statement);
        ArrayList<HashMap<String, String>> al = SQLSystem.retrieveAverageMessurementData(statement, con);

        // First we have to find the average temreature to use. 
        // the others will come later 
        Double sum = 0.00;
        for (HashMap<String, String> hm : al){
            sum += Double.valueOf(hm.get("Temperatuur"));
        }

        avTemp = sum / al.size(); // avergae temprature

        // loop trough the raw data
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            // save key/value in seperate variables
            String key = pair.getKey().toString();
            String value = pair.getValue().toString();

            if (key.equals("Temperatuur")){
                Double tmp = Double.valueOf(value); // make it a double for calculations 
                if (tmp > (avTemp-(avTemp*0.2)) && tmp < (avTemp+(avTemp*0.2))){ // if temp is whitin a window 20%
                    correctData.put("Temperatuur", tmp.toString()); // add data to retun
                } else {
                    correctData.put("Temperatuur", avTemp.toString());
                }
            } else if (value.toString().isEmpty() || value == null || value == " NaN"){
                colmnToCheck.add(key.toString()); // add to checkColumns for later check
            } else if (key.equals("Windrichting")){
                if (value == "NaN"  || value == null){
                    colmnToCheck.add(key.toString());
                } else {
                    correctData.put(key, value);
                }
            } 
            else {
                correctData.put(key, value);
            }
            it.remove(); // avoids exception
        }

        for (String column : colmnToCheck){
            if (!column.equals("Gebeurtenis")){
                Double som = 0.00;

                for (HashMap<String, String> hm : al){
                    //FunctionLibary.debuggerOutput(Configuration.DEBUG_MODE, 2, column + "  hm value is  " + hm.get(column), new Exception("DUMMY"));
                    String value;
                    if (hm.get(column) == null){value = "0.00";} else {value = hm.get(column);}
                    som += som + Double.valueOf(value);
                }

                som = som/al.size();
                correctData.put(column, som.toString());
            }
        }
        return correctData;
    } 
}
