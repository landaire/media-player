package org.madhatters.mediaplayer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.madhatters.mediaplayer.media.AudioFile;
import org.madhatters.mediaplayer.models.Audio;

import java.util.Collection;
import java.util.stream.Collectors;

public class MainController {
    @FXML private TableView<Audio> mediaTable;
    @FXML private TableColumn<Audio, String> filePathColumn;
    @FXML private TableColumn<Audio, String> artistColumn;
    @FXML private TableColumn<Audio, String> titleColumn;

    ObservableList<Audio> files;

    public void initialize() {
        filePathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    public void setFiles(Collection<AudioFile> audioFiles) {
        files = FXCollections.observableArrayList(audioFiles
                .stream()
                .map(f -> new Audio(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        mediaTable.setItems(files);
    }
}
