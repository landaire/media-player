package org.madhatters.mediaplayer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.madhatters.mediaplayer.media.Mp3File;
import org.madhatters.mediaplayer.models.AudioFile;

import java.util.Collection;
import java.util.stream.Collectors;

public class MainController extends AbstractController {
    @FXML private TableView<AudioFile> mediaTable;
    @FXML private TableColumn<AudioFile, String> filePathColumn;
    @FXML private TableColumn<AudioFile, String> artistColumn;
    @FXML private TableColumn<AudioFile, String> titleColumn;

    ObservableList<AudioFile> files;

    public void initialize() {
        filePathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    public void setFiles(Collection<Mp3File> mp3Files) {
        files = FXCollections.observableArrayList(mp3Files
                .stream()
                .map(f -> new AudioFile(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        mediaTable.setItems(files);
    }
}
