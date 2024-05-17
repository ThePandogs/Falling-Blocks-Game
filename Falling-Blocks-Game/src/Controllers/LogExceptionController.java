package Controllers;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Controls logging of exceptions to a file.
 * 
 * <p>Created by Carlos Fraile - ThePandogs</p>
 * 
 * <p>The LogExceptionController class manages logging of exceptions to a specified file.
 * It records the type of exception, the date and time when it occurred, and the reason for the exception.</p>
 * 
 * @author Carlos Fraile - ThePandogs
 */
public class LogExceptionController {

    private LocalDateTime data;
    private LocalTime time;
    File exceptionsFile;

    /**
     * Constructs a LogExceptionController object.
     */
    public LogExceptionController() {
        exceptionsFile = new File("Exception.txt");
        try {
            // Check if the file doesn't exist and create it if necessary
            if (!exceptionsFile.exists()) {
                exceptionsFile.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(LogExceptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adds an exception log entry to the exceptions file.
     * 
     * @param e the exception to log
     */
    public void addExceptionLog(Exception e) {
        data = LocalDateTime.now();
        time = LocalTime.now();
        PrintWriter output = null;

        try {
            output = new PrintWriter((new FileWriter(exceptionsFile, true)));
            output.println("Exception: " + e.getClass());
            output.println("Date:  " + data.format(DateTimeFormatter.ISO_DATE));
            output.println("Time:   " + time.truncatedTo(ChronoUnit.SECONDS));
            output.println("Reason: " + e.getLocalizedMessage());
            output.println();
        } catch (IOException ex) {
            Logger.getLogger(LogExceptionController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
}
