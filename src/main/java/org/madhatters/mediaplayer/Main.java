package org.madhatters.mediaplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.madhatters.mediaplayer.builder.StageBuilder;
import org.madhatters.mediaplayer.concurrent.ExecutorServiceSingleton;
import org.madhatters.mediaplayer.controllers.IndexingScreenController;
import org.madhatters.mediaplayer.database.MediaDB;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        StageBuilder.setStage(primaryStage);

        if (MediaDB.databaseExists()) {
            MediaDB db = new MediaDB();

            StageBuilder.getAudioMediaStage(db.getAudioFiles()).show();

            db.closeDB();
        } else {
            StageBuilder.getIndexingStage().show();
        }
    }

    /**
     * Called when the JavaFX application is exiting. All threads need to be signaled to shut down so that the app
     * doesn't hang and wait for them to come to completion. A scenario where this may happen is when the user is indexing.
     * If the threads are not signaled to stop then Java will wait for them to finish before actually exiting
     */
    @Override
    public void stop() {
        ExecutorServiceSingleton.instance().shutdownNow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
