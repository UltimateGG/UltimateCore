package me.ultimate.logging;

import me.ultimate.Utils;
import me.ultimate.fileutils.FileUtils;
import me.ultimate.timerutils.Timer;
import me.ultimate.timerutils.TimerFunction;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class LoggingManager {
    private static final HashMap<String, Logger> loggers = new HashMap<>();
    public static LoggingManager INSTANCE;
    private final String logsFolder;

    /**
     * Instantiate a new LoggingManager. Should only be used once per project.
     * @param logsFolder The folder where .log files are stored.
     */
    public LoggingManager(String logsFolder) {
        INSTANCE = this;
        this.logsFolder = logsFolder.endsWith("/") ? logsFolder : logsFolder + "/";

        makeCurrentLog();

        //Schedule new internal task to create new log file incase the application is running
        //into next day
        final Timer checker = new Timer();

        try {
            checker.setInterval(new TimerFunction() {
                @Override
                public void run(int round) {
                    makeCurrentLog();
                }
            }, 5000);

            if (getCurrentLogFile().exists()) {
                FileUtils.appendFile(getCurrentLogFile(), "\n[" + Logger.getDate() + "] Application restarted.\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeCurrentLog() {
        final File folder = FileUtils.getFile(logsFolder);
        final File logFile = getCurrentLogFile();

        if (!folder.isDirectory() || folder.exists()) folder.mkdirs();

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();

                //Write format stamp at top
                FileUtils.appendFile(logFile, getNextLogName() + " - Started At: " + Logger.getDate()
                        + "\nFORMAT: [DAY_NAME DAY/MONTH/YEAR HOUR:MINUTE:SECOND D: TIME_SINCE_LAST_LOG] [THREAD_NAME] [LOG_LEVEL] [LOGGER_NAME] MESSAGE\n\n");
            } catch (IOException ignored) {}
        }
    }

    /**
     * Adds the logger into the stored logger array
     * @param logger The logger to register
     */
    public void registerLogger(Logger logger) {
        loggers.put(logger.getName(), logger);
    }

    /**
     * @param name The name of the logger to get
     * @return The logger object, or null if it was not found by that key
     */
    public static Logger getLogger(String name) {
        return loggers.get(name);
    }

    /**
     * @return The current .log file being used for common loggers, never null
     */
    public File getCurrentLogFile() {
        return FileUtils.getFile(logsFolder + getNextLogName());
    }

    /**
     * @return The file path set on instantiation of this class, or null if not yet
     * instantiated.
     */
    public String getLogsFolder() {
        return logsFolder;
    }

    private String getNextLogName() {
        final Calendar inst = Calendar.getInstance();
        return Utils.padInt(inst.get(Calendar.DAY_OF_MONTH)) + "-" + Utils.padInt(inst.get(Calendar.MONTH) + 1) + "-" + Utils.padInt(inst.get(Calendar.YEAR))
                + ".log";
    }
}
