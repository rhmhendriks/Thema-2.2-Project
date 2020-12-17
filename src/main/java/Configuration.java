
/**
 * This class is the home of some global application wide 
 * settings like the SQL data and the logger. 
 */

public class Configuration {
    /**
     * The constants below specify the SQL settings. 
     */
    public static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String SQL_URL = "jdbc:mysql://192.168.156.213:3306/unwdmi_ron" + "?autoReconnect=true&useSSL=false";
    public static final String SQL_USER = "java";
    public static final String SQL_PASSWORD = "J@va2020";

    /**
     * The constants below are needed for debug.
     */
    public static final int DEBUG_MODE = 2;
    
}
