package org.madhatters.mediaplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.madhatters.mediaplayer.controllers.IndexingScreenController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    public final ExecutorService executorService;

    public Main() {
        executorService = Executors.newSingleThreadExecutor();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/indexing_screen.fxml"));

        Parent root = loader.load();
        ((IndexingScreenController)loader.getController()).setExecutorService(executorService);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Mad Hatter Media Player");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() {
        executorService.shutdownNow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
