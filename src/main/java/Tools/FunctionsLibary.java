public class FunctionsLibary{

    public FunctionsLibary();

    /**
     * This method converts a date string (format YYYY-MM-DD) into a
     * SQL Date format. 
     * 
     * The source for this method is Mr.Chowdary on StackOverflow
     * 
     * @param strdate
     * @return
     */
    public static java.sql.Date stringdateToSqldate(String strdate){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = format.parse(strdate);
        return (new java.sql.Date(parsed.getTime()));
    }
}