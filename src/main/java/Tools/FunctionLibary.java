package Tools;
public class FunctionLibary{

    public FunctionLibary(){};

    /**
     * This method prints an error on the CLI of this application, 
     * the text is Red and Bold on the windows 10 Terminal App and
     * Unix based systems.
     * 
     * @param message The error message to print
     */
    public static void errorCLI(String message){
        System.out.println(ANSI.ANSI_RED + ANSI.ANSI_BOLD + message + ANSI.ANSI_RESET);
    }

    /**
     * This method is used for some debug output based on the globally set debug mode
     * in the configuration class. 
     * 
     * This method also returns if the asked debug mode matches in order to give 
     * more flexibility: it is now possible to add some extra output when the debug 
     * mode is on. 
     * 
     * This method is mosty used to keep the code more readable. 
     * 
     * @param DEBUG_MODE DBUG constant as specified in Configuration
     * @param debugLevel On what debug level do we have to send your message?
     * @param message The message to display
     * @param e An optional exeption. 
     * @return
     */
    public static boolean debuggerOutput(int DEBUG_MODE, int debugLevel, String message, Exception e) {
        if(debugLevel == DEBUG_MODE){
            switch (debugLevel){
                case 1:
                    // Notice Block. The message is less important, and gives more detail about some exeptions
                    // for example some SQL messages and results.
                    System.err.println(ANSI.ANSI_BBLUE + message);
                    if(!e.getMessage().equals("DUMMY")){System.err.println(e.getMessage());}
                    System.err.println(ANSI.ANSI_RESET);
                case 2:
                    // Test Block. For some more printing like the statements and their results to give more insight 
                    System.err.println(ANSI.ANSI_PURPLE + message + ANSI.ANSI_RESET);
                    if(!e.getMessage().equals("DUMMY")){e.printStackTrace();}
                    System.err.println(ANSI.ANSI_RESET);
                case 3:
                    // Full Debug. Print every possible detail in the application to detect an problem
                    // this is mostly used in combination with an if clause around this method.
                    System.err.println(ANSI.ANSI_BMAGENTA + message + ANSI.ANSI_RESET);
                    if(!e.getMessage().equals("DUMMY")){e.printStackTrace();}
                    System.err.println(ANSI.ANSI_RESET);
            }
            return true;
        }
        return false;
    }


}