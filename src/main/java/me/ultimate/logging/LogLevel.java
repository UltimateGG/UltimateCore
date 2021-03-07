package me.ultimate.logging;

public enum LogLevel {
    INFO("INFO"),
    WARN("WARN"),
    ERROR("ERROR"),
    STACK("STACK TRACE");


    private String ln;

    LogLevel(String ln) {
        this.ln = ln;
    }

    public String logName() {
        return this.ln;
    }
}
