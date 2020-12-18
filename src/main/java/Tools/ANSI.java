package Tools;

/**
 * De klasse ANSI
 * 
 * De klasse ANSI bevind zich
 * binnen het package "tools".
 * 
 * De klasse ansi bevat constanten die door het hele project gebruikt kunnen
 * worden voor kleuren in de CLI.
 * 
 * LET OP: de kleuren werken alleen op: Linux, MAC OSX, Windows 10 (Terminal
 * APP)
 * 
 * @author Luc Willemse
 * @author Ronald H.M. Hendriks
 * @author Jurre de Vries
 * @author Rienan Poortvliet
 * 
 * @version 4.5
 * 
 */
public class ANSI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BOLD = "\u001b[1m";
    public static final String ANSI_UNDERLINE = "\u001B[1m";

    // Hieronder staan de lichte kleuren
    public static final String ANSI_GREY = "\u001b[30;1m";
    public static final String ANSI_BGREEN = "\u001b[32;1m";
    public static final String ANSI_BRED = "\u001b[31;1m";
    public static final String ANSI_BYELLOW = "\u001b[33;1m";
    public static final String ANSI_BBLUE = "\u001b[34;1m";
    public static final String ANSI_BMAGENTA = "\u001b[35;1m";

    private ANSI() {
    }
}