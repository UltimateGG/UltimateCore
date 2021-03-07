package me.ultimate.timerutils;

import java.util.TimerTask;

public class Timer {
    private java.util.Timer internalTimer;
    private TimerFunction function;
    private boolean running;
    private boolean isRepeatingTask;
    private long startedAt;
    private long currentDelay;

    public void setInterval(TimerFunction runnable, int intervalMs) throws TimerAlreadyRunningException {
        checkIfRunning();
        isRepeatingTask = true;

        this.function = runnable;
        java.util.Timer timer = new java.util.Timer();

        timer.schedule(new TimerTask() {
            int round = 0;

            @Override
            public void run() {
                runnable.run(round++);
            }
        }, 0, intervalMs);

        internalTimer = timer;
        running = true;
    }

    public void setTimeout(TimerFunction runnable, long delayMs) throws TimerAlreadyRunningException {
        checkIfRunning();

        this.function = runnable;
        java.util.Timer timer = new java.util.Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run(-1);
            }
        }, delayMs);

        startedAt = System.currentTimeMillis();
        currentDelay = delayMs;
        internalTimer = timer;
        running = true;
    }

    private void checkIfRunning() throws TimerAlreadyRunningException {
        if (running) {
            cancel();
            throw new TimerAlreadyRunningException("Timer attempted to be scheduled without being cancelled first!");
        }
    }

    public void cancel() {
        if (internalTimer != null) {
            internalTimer.cancel();
            internalTimer = null;

            running = false;
            isRepeatingTask = false;
        }
    }

    public TimerFunction getFunction() {
        return function;
    }

    public boolean isRunning() {
        return running;
    }

    public long getTimeTillRun() {
        if (isRepeatingTask || !running) return -1;

        final long tillRun = (startedAt + currentDelay) - System.currentTimeMillis();
        return tillRun < 0 ? -1 : tillRun;
    }
}
