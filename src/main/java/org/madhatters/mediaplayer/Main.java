package org.madhatters.mediaplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.madhatters.mediaplayer.controllers.IndexingScreenController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    public final ExecutorService executorService;
    private Stage stage;
    private Scene indexingScreen, audioViewScene;

    public Main() {
        executorService = Executors.newSingleThreadExecutor();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/indexing_screen.fxml"));

        Parent root = loader.load();
        ((IndexingScreenController)loader.getController()).setExecutorService(executorService);


        primaryStage.setResizable(false);
        primaryStage.setTitle("Mad Hatter Media Player");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Called when the JavaFX application is exiting. All threads need to be signaled to shut down so that the app
     * doesn't hang and wait for them to come to completion. A scenario where this may happen is when the user is indexing.
     * If the threads are not signaled to stop then Java will wait for them to finish before actually exiting
     */
    @Override
    public void stop() {
        executorService.shutdownNow();
    }

    /**
     * Replaces the content of a scene with the content of the resource given by fxml
     * @param fxml
     * @return
     * @throws Exception
     */
    private Parent replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        Parent page = loader.load();

        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(page);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();

        return page;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
