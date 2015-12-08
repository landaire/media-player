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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URLEncoder;
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

    MediaPlayer currentMediaPlayer;

    ObservableList<Audio> masterFiles = FXCollections.observableArrayList();

    public void initialize() {
        mediaTable.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                Audio selectedAudioFile = mediaTable.getSelectionModel().getSelectedItem();

                if (currentMediaPlayer != null) {
                    currentMediaPlayer.stop();
                }

                try {
                    Media newMedia = new Media(new File(selectedAudioFile.getPath()).toURI().toString());
                    currentMediaPlayer = new MediaPlayer(newMedia);
                    currentMediaPlayer.play();
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });

        initializeTableView();
    }

    private void initializeTableView() {
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
