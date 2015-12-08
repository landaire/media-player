package org.madhatters.mediaplayer.builder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.madhatters.mediaplayer.controllers.MainController;
import org.madhatters.mediaplayer.media.AudioFile;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Lander Brandt on 12/6/15.
 */
public class StageBuilder {
    private static final String indexingResource = "/fxml/indexing_screen.fxml";
    private static final String audioMediaResource = "/fxml/main_view.fxml";
    private static Stage primaryStage;

    public static Stage getIndexingStage() throws IOException {
        Parent root = getLoaderForResource(indexingResource).load();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Mad Hatter Media Player");
        primaryStage.setScene(new Scene(root));

        return primaryStage;
    }

    public static Stage getAudioMediaStage(Collection<AudioFile> files) throws IOException {
        FXMLLoader loader = getLoaderForResource(audioMediaResource);
        Parent root  = loader.load();

        ((MainController)loader.getController()).setFiles(files);

        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();

        return primaryStage;
    }

    public static void setStage(Stage primaryStage) {
        StageBuilder.primaryStage = primaryStage;
    }

    private static FXMLLoader getLoaderForResource(String resource) {
        return new FXMLLoader(StageBuilder.class.getResource(resource));
    }
}
