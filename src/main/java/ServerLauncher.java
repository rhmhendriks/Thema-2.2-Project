import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Tools.XMLParser;

public class ServerLauncher {
    public static void main(String[] args) throws Exception {
        XMLParser parser = new XMLParser("output.xml");
        SQLSystem sql = new SQLSystem();

        // set the to ignore tags
        ArrayList<String> ntu = new ArrayList<>();
        ntu.add("WEATHERDATA");
        ntu.add("MEASUREMENT");

        parser.setNotToUseTags(ntu);

        // Convert CSV to Hashmap
        HashMap<String, String> data = parser.parse();

        //print Hashmap
        // Iterator it = data.entrySet().iterator();
        // while (it.hasNext()) {
        // Map.Entry pair = (Map.Entry)it.next();
        // System.out.println(pair.getKey() + " = " + pair.getValue());
        // it.remove(); // avoids a ConcurrentModificationException
        // }

        // Put to Database
        sql.addMessurement(data);

    }
}
