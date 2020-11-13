public class FrameTimer {
    private static final float startOfRun;
    private static float frameStartTime;
    private static float frameEndTime;
    private static float frameDeltaTime;
    private static float fps;

    static {
        startOfRun = System.nanoTime();
    }

    /**
     * Measures the time elapsed since program start in nanoseconds, returns time in seconds.
     *
     * @return Elapsed time in seconds since program start.
     */
    public static float getTime() {
        return (float) ((System.nanoTime() - startOfRun) * 1e-9);
    }

    /**
     * Must be called before first frame to initialize the start of FPS measurements.
     */
    public static void initFrameTimer() {
        frameStartTime = FrameTimer.getTime();
    }

    /**
     * @return Measure the FPS rate.
     */
    public static float getMeasurement() {
        frameEndTime = FrameTimer.getTime();
        frameDeltaTime = frameEndTime - frameStartTime;
        frameStartTime = frameEndTime;
        fps = (1.0f / frameDeltaTime);
        return fps;
    }

    /**
     * @return Last measured frames per second.
     */
    public static float getFps() {
        return fps;
    }
}
