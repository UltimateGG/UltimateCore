package me.ultimate.logging;

import me.ultimate.Utils;
import me.ultimate.fileutils.FileUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Locale;

public class Logger {
    private final String name;
    private final boolean writeToFile;
    private long lastLogTime;

    /**
     * Instantiate a new logger object for calling .logInfo, .logWarn, .log, etc.
     * This will ONLY print to the console. Use the other constructor and specify true to
     * write to physical files.
     *
     * @param name The name to show up in logs, also used as a key in Logger.getLogger
     */
    public Logger(String name) {
        this(name, false);
    }

    /**
     * Instantiate a new logger object for calling .logInfo, .logWarn, .log, etc.
     * @param name The name to show up in logs, also used as a key in Logger.getLogger
     * @param writesToFile If this logger only shows in SOUT or appends to the log file in the folder made by instantiating LoggingManager
     */
    public Logger(String name, boolean writesToFile) {
        this.name = name;
        this.writeToFile = writesToFile;

        lastLogTime = System.currentTimeMillis();
    }

    /**
     * Get a logger from the LoggingManager, same method is available in LoggingManager
     * class, but this is used for ease of access.
     * @param name The key/name of the logger to obtain
     * @return The logger object or null if it was not registered by that key
     */
    public static Logger getLogger(String name) {
        return LoggingManager.getLogger(name);
    }

    public String getName() {
        return name;
    }

    public boolean writesToFile() {
        return writeToFile;
    }

    public void logInfo(String message) {
        log(LogLevel.INFO, message);
    }

    public void logWarn(String message) {
        log(LogLevel.WARN, message);
    }

    public void logError(String message) {
        log(LogLevel.ERROR, message);
    }

    // https://stackoverflow.com/questions/1149703
    public void logException(String msg, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        log(LogLevel.STACK, msg + "\n" + sw.toString());
    }

    public void logException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        log(LogLevel.STACK, sw.toString());
    }

    public void log(LogLevel level, String message) {
        final String log = "[" + getDate() + " D: " + getLastLogTimeDiff() + "s] " +
                "[" + Thread.currentThread().getName() + "] " +
                "[" + level.logName() + "] " +
                "[" + name + "] " +
                message;

        System.out.println(log);
        writeToLog(log);
        lastLogTime = System.currentTimeMillis();
    }

    private void writeToLog(String logMsg) {
        if (writeToFile) {
            try {
                FileUtils.appendFile(LoggingManager.INSTANCE.getCurrentLogFile(), logMsg + "\n");
            } catch (IOException e) {
                System.out.println("Error while writing to log file! From logger: '" + name + "', skipped.");
                e.printStackTrace();
            }
        }
    }

    /**
     * @return Current date formatted nicely
     */
    public static String getDate() {
        final Calendar inst = Calendar.getInstance();
        return inst.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH).substring(0, 3).toUpperCase() + " "
                + inst.get(Calendar.DAY_OF_MONTH) + "/" + (inst.get(Calendar.MONTH) + 1) + "/" + inst.get(Calendar.YEAR) +
                " " + Utils.padInt(inst.get(Calendar.HOUR)) + ":" + Utils.padInt(inst.get(Calendar.MINUTE)) + ":" + Utils.padInt(inst.get(Calendar.SECOND));
    }

    private int getLastLogTimeDiff() {
        return Math.round((System.currentTimeMillis() - lastLogTime) / 1000F);
    }

    /**
     * @return Time since the logger was last called
     */
    public long getLastLogTime() {
        return lastLogTime;
    }
}
