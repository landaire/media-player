package org.madhatters.mediaplayer.controllers;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * Created by Lander Brandt on 12/6/15.
 */
public class AbstractController {
    protected ExecutorService executorService;

    public void setExecutorService(ExecutorService service) {
        this.executorService = service;
    }
}
