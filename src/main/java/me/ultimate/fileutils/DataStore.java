package me.ultimate.fileutils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;

public class DataStore {
    private final File file;
    private final JsonElement defaultValues;
    private long lastUpdated;

    /**
     * Creates a new data store object. If the file did not exist,
     * it is created and the default values are written as an empty JSON object {}.
     * @param filePath The location of the data store, preferably a .json file
     */
    public DataStore(String filePath) {
        this(filePath, null);
    }

    /**
     * Creates a new data store object. If the file did not exist,
     * it is created and the default values are written. If default values
     * were null, it is set to an empty JSON object - {}
     * @param filePath The location of the data store, preferably a .json file
     * @param defaultValues The default values used when the file is corrupted or called to write defaults
     */
    public DataStore(String filePath, JsonElement defaultValues) {
        file = FileUtils.getFile(filePath);
        this.defaultValues = defaultValues == null ? new JsonObject() : defaultValues;

        if (!file.exists()) {
            try {
                writeDefaults();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads this data store from disk and parses it as JSON
     * @return JSON from the file, never null
     * @throws IOException From invalid JSON, file being moved/deleted, security exceptions, error reading
     */
    public JsonElement readFile() throws IOException {
        return FileUtils.readJSONFile(file);
    }

    /**
     * Writes to the file with the set JSON.
     * @param values The new JSON to overwrite the file with
     * @return The initial values passed in for quick one line operations
     * @throws IOException From invalid JSON or file writing operations
     */
    public JsonElement write(JsonElement values) throws IOException {
        FileUtils.writeFile(file, values);
        lastUpdated = System.currentTimeMillis();

        return values;
    }

    /**
     * Writes the default values passed in on instantiation of this data store object
     * @throws IOException From security exceptions, file write exceptions
     */
    public void writeDefaults() throws IOException {
        FileUtils.writeFile(file, defaultValues == null ? new JsonObject() : defaultValues);
        lastUpdated = System.currentTimeMillis();
    }

    /**
     * @return The default values passed in on instantiation of this data store object
     */
    public JsonElement getDefaultValues() {
        return defaultValues;
    }

    /**
     * @return The file object of this data store
     */
    public File getFile() {
        return file;
    }

    /**
     * @return The absolute file path of this data store, generally a .json file.
     */
    public String getPath() {
        return file.getAbsolutePath();
    }

    /**
     * @return The date in milliseconds when the file was last updated
     */
    public long getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return "DataStore [loc=\"" + getPath() + "\", defaults=" + getDefaultValues().toString() + "]";
    }
}
