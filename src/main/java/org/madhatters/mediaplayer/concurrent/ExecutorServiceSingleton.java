package org.madhatters.mediaplayer.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Lander Brandt on 12/6/15.
 */
public class ExecutorServiceSingleton {
    private static final ExecutorService service;

    static {
        service = Executors.newSingleThreadExecutor();
    }

    public static ExecutorService instance() {
        return service;
    }
}
