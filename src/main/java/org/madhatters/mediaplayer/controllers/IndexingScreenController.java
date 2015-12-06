package org.madhatters.mediaplayer.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import org.madhatters.mediaplayer.media.FileFinder;
import org.madhatters.mediaplayer.models.Mp3;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Lander Brandt on 11/21/15.
 */
public class IndexingScreenController {
    @FXML public Button chooseDirectoryButton;
    @FXML private TextField scanDirTextField;
    @FXML private Button scanButton;
    @FXML private Label currentFileLabel;
    @FXML private ProgressBar fileStatusProgressBar;

    public void initialize() {
        scanDirTextField.setText(System.getProperty("user.home"));

        scanButton.setOnAction(scanButtonHandler());
    }

    private EventHandler<ActionEvent> scanButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                scanButton.setDisable(true);
                fileStatusProgressBar.setProgress(-1.0f);

                Collection<Mp3> files = FileFinder.findIn(scanDirTextField.getText())
                        .stream()
                        .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                        .collect(Collectors.toList())
                ;

                System.out.println(files.size());
            }
        };
    }
}
