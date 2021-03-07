package me.ultimate;

import java.nio.charset.StandardCharsets;

public class Utils {
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String padInt(int i) {
        return i < 10 ? "0"+i : i+"";
    }
}
