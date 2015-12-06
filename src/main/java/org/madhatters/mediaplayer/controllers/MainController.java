package org.madhatters.mediaplayer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.madhatters.mediaplayer.media.FileFinder;
import org.madhatters.mediaplayer.models.Mp3;

import java.util.stream.Collectors;

public class MainController {
    @FXML private TableView mediaTable;
    @FXML private TableColumn<Mp3, String> filePathColumn;
    @FXML private TableColumn<Mp3, String> artistColumn;
    @FXML private TableColumn<Mp3, String> titleColumn;

    ObservableList<Mp3> files;

    public void initialize() {
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/lander/Downloads/Black Hippy - T.D.E. (2014) [MP3 320]")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        mediaTable.setItems(files);

        filePathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }
}
