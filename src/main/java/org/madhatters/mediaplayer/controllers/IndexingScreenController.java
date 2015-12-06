package org.madhatters.mediaplayer.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.madhatters.mediaplayer.Main;
import org.madhatters.mediaplayer.media.FileFinder;
import org.madhatters.mediaplayer.models.Mp3;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by Lander Brandt on 11/21/15.
 */
public class IndexingScreenController extends AbstractController {
    @FXML
    public Button chooseDirectoryButton;
    @FXML
    public Label messageLabel;
    @FXML
    private TextField scanDirTextField;
    @FXML
    private Button scanButton;
    @FXML
    public Label currentDirectoryLabel;
    @FXML
    private ProgressBar fileStatusProgressBar;

    public void initialize() {
        scanDirTextField.setText(System.getProperty("user.home"));

        scanButton.setOnAction(scanButtonHandler());
        chooseDirectoryButton.setOnAction(chooseDirectoryButtonHandler());
    }

    /**
     * Event handler for the choose directory button
     * @return
     */
    private EventHandler<ActionEvent> chooseDirectoryButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                DirectoryChooser chooser = new DirectoryChooser();
                File directory = new File(scanDir());

                if (directory.exists()) {
                    chooser.setInitialDirectory(directory);
                }

                directory = chooser.showDialog(((Node)e.getTarget()).getScene().getWindow());

                // directory could be null if the user did not select any directory
                if (directory == null) {
                    return;
                }

                scanDirTextField.setText(directory.getAbsolutePath());
            }
        };
    }

    /**
     * Event handler for the scan button
     * @return
     */
    private EventHandler<ActionEvent> scanButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Control[] disableControls = new Control[] {
                        scanButton, scanDirTextField, chooseDirectoryButton,
                };

                // disable any controls which the user should not be able to interact with when indexing is occurring
                for (Control c : disableControls) {
                    c.setDisable(true);
                }

                // -1.0f signals that this is indeterminite
                fileStatusProgressBar.setProgress(-1.0f);

                executorService.execute(() -> {
                    Collection<Mp3> files =  FileFinder.findIn(scanDir(), f -> {
                        Platform.runLater( new Runnable() {
                            @Override public void run() {
                                currentDirectoryLabel.setText(f.getName());
                            }
                        });

                        return null;
                    })
                            .stream()
                            .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                            .collect(Collectors.toList());

                    scanFinished(files);
                });
            }
        };
    }

    /**
     * Current root scan directory
     * @return scan directory
     */
    private String scanDir() {
        return scanDirTextField.getText();
    }

    /**
     * Called when scanning files finishes
     * @param files
     */
    private void scanFinished(Collection<Mp3> files) {
        System.out.println("Finished scanning");
    }
}
