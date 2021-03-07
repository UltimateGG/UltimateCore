package me.ultimate.mathutils;

public class MathHelper {
    private MathHelper() {}

    public static int clampInt(int val, int min, int max) {
        return val < min ? min : Math.min(val, max);
    }

    public static float clampFloat(float val, float min, float max) {
        return val < min ? min : Math.min(val, max);
    }

    public static float clampLong(long val, long min, long max) {
        return val < min ? min : Math.min(val, max);
    }

    public static double clampDouble(double val, double min, double max) {
        return val < min ? min : Math.min(val, max);
    }
}
