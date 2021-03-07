package me.ultimate.timerutils;

public class TimerAlreadyRunningException extends Exception {
    private String msg;

    public TimerAlreadyRunningException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TimerAlreadyRunningException [msg=\"" + msg + "\"]";
    }
}
