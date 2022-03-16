package no.arnemunthekaas.engine.utils;

public class Time {
    public static final float TIME_STARTED = System.nanoTime();

    /**
     * Returns the time elapsed since application was started in seconds.
     *
     * @return time since startup (in seconds)
     */
    public static float getTime() {
        return (float) ((System.nanoTime() - TIME_STARTED) * 1E-9);
    }

}
