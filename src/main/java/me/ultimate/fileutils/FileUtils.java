package me.ultimate.fileutils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {
    private FileUtils() {}

    public static File getFile(String path) {
        return new File(path);
    }

    public static String inputStreamToString(InputStream stream) throws IOException {
        char[] buffer = new char[1024];
        StringBuilder out = new StringBuilder();

        Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
        for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0;) {
            out.append(buffer, 0, numRead);
        }

        return out.toString();
    }

    private static String readFileToString(File file) throws IOException {
        if (file.exists()) return inputStreamToString(new FileInputStream(file));
        return null;
    }

    public static String readFile(String path) throws IOException {
        return readFile(getFile(path));
    }

    public static String readFile(File file) throws IOException {
        return readFileToString(file);
    }

    /**
     * Reads the file at the set path to a string and parses it as JSON
     * @param path The exact file path to read from
     * @return The serialized JSON from the file
     */
    public static JsonElement readJSONFile(String path) throws IOException {
        return JsonParser.parseString(readFile(getFile(path)));
    }

    /**
     * Reads the file at the set path to a string and parses it as JSON
     * @param file The file to read
     * @return The serialized JSON from the file
     */
    public static JsonElement readJSONFile(File file) throws IOException {
        return JsonParser.parseString(readFile(file));
    }

    /**
     * Completely overwrites the current file with a new file
     * with the specified contents. Use appendFile to add to an
     * existing file.
     * @param path The exact file path to write to. Will be created if it does not exist.
     * @param contents The contents to write to the file.
     */
    public static void writeFile(String path, String contents) throws IOException {
        writeFile(getFile(path), contents);
    }

    /**
     * Completely overwrites the current file with a new file
     * with the specified contents. Use appendFile to add to an
     * existing file.
     * @param file The file to write to. Will be created if it does not exist.
     * @param contents The contents to write to the file.
     */
    public static void writeFile(File file, String contents) throws IOException {
        if (file.exists()) deleteFile(file);
        file.createNewFile();

        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(contents.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    /**
     * Same as writeFile but automatically stringifies the JSON
     * @param path Exact file path to write to
     * @param json JSON to write into file
     */
    public static void writeFile(String path, JsonElement json) throws IOException {
        writeFile(path, json.toString());
    }

    /**
     * Same as writeFile but automatically stringifies the JSON
     * @param file File to write to
     * @param json JSON to write into file
     */
    public static void writeFile(File file, JsonElement json) throws IOException {
        writeFile(file, json.toString());
    }

    /**
     * Appends to the bottom of the file specified with the set contents.
     * Throws an error if the file does not exist.
     * @param path The exact file path to append to
     * @param content The contents to append to the file
     */
    public static void appendFile(String path, String content) throws IOException {
        appendFile(getFile(path), content);
    }

    /**
     * Appends to the bottom of the file specified with the set contents.
     * Throws an error if the file does not exist.
     * @param file The file to append to
     * @param content The contents to append to the file
     */
    public static void appendFile(File file, String content) throws IOException {
        if (!file.exists()) throw new FileNotFoundException("File could not be found to append to");

        Files.write(Paths.get(file.getAbsolutePath()), content.getBytes(), StandardOpenOption.APPEND);
    }

    public static void deleteFile(String path) {
        deleteFile(getFile(path));
    }

    public static void deleteFile(File file) {
        file.delete();
    }
}
