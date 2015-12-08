package org.madhatters.mediaplayer.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.madhatters.mediaplayer.media.AudioFile;
import org.madhatters.mediaplayer.models.Audio;

import java.util.Collection;
import java.util.stream.Collectors;

public class MainController {
    public Button previousButton;
    public Button pauseButton;
    public Button nextButton;
    public Label songInfoLabel;
    public Slider seekSlider;
    public Label songDurationLabel;
    public TextField searchField;
    @FXML private TableView<Audio> mediaTable;
    @FXML private TableColumn<Audio, String> filePathColumn;
    @FXML private TableColumn<Audio, String> artistColumn;
    @FXML private TableColumn<Audio, String> titleColumn;

    ObservableList<Audio> masterFiles = FXCollections.observableArrayList();

    public void initialize() {
        filePathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // The predicate here displays all files
        FilteredList<Audio> filteredFiles = new FilteredList<>(masterFiles, mp3 -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredFiles.setPredicate(audio -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowercaseFilter = newValue.toLowerCase();
                String[] filterFields = new String[] {
                        audio.getTitle(), audio.getAlbum(), audio.getArtist(),
                };

                for (String field : filterFields) {
                    if (field == null) {
                        continue;
                    }
                    
                    if (field.toLowerCase().contains(lowercaseFilter)) {
                        return true;
                    }
                }

                return false;
            });
        });

        SortedList<Audio> sortedData = new SortedList<>(filteredFiles);
        sortedData.comparatorProperty().bind(mediaTable.comparatorProperty());

        mediaTable.setItems(sortedData);
    }

    public void setFiles(Collection<AudioFile> audioFiles) {
        masterFiles.setAll(audioFiles
                .stream()
                .map(f -> new Audio(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );
    }
}
