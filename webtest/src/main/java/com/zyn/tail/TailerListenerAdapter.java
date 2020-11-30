package com.zyn.tail;

/**
 * @author zyn
 * @Description
 * @date 2020-09-18 10:53
 * <p>
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 */
public class TailerListenerAdapter implements TailerListener {
    /**
     * The tailer will call this method during construction,
     * giving the listener a method of stopping the tailer.
     * @param tailer the tailer.
     */
    public void init(Tailer tailer) {
    }

    /**
     * This method is called if the tailed file is not found.
     */
    public void fileNotFound() {
    }

    /**
     * Called if a file rotation is detected.
     *
     * This method is called before the file is reopened, and fileNotFound may
     * be called if the new file has not yet been created.
     */
    public void fileRotated() {
    }

    /**
     * Handles a line from a Tailer.
     * @param line the line.
     */
    public void handle(String line) {
    }

    /**
     * Handles an Exception .
     * @param ex the exception.
     */
    public void handle(Exception ex) {
    }

}
