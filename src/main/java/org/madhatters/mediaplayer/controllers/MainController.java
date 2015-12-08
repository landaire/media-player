package org.madhatters.mediaplayer.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import org.madhatters.mediaplayer.concurrent.ExecutorServiceSingleton;
import org.madhatters.mediaplayer.media.AudioFile;
import org.madhatters.mediaplayer.media.Playlist;
import org.madhatters.mediaplayer.models.Audio;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.madhatters.mediaplayer.util.TimeUtilities;

import java.io.File;
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
    @FXML
    public TableColumn<Audio, String> lengthColumn;
    @FXML
    private TableView<Audio> mediaTable;
    @FXML private TableColumn<Audio, String> filePathColumn;
    @FXML private TableColumn<Audio, String> artistColumn;
    @FXML private TableColumn<Audio, String> titleColumn;

    MediaPlayer currentMediaPlayer;
    Playlist playlist;

    ObservableList<Audio> masterFiles = FXCollections.observableArrayList();

    static final String PAUSE_TEXT = "Pause";
    static final String PLAY_TEXT = "Play";

    static final Object pauseLock = new Object();
    boolean paused = false;

    public void initialize() {
        songInfoLabel.setText("");
        songDurationLabel.setText("");

        /**
         * This listener is here to change the playlist when the files are updated
         */
        masterFiles.addListener(new ListChangeListener<Audio>() {
            @Override
            public void onChanged(Change<? extends Audio> c) {
                playlist = new Playlist(masterFiles);
            }
        });

        /**
         * Background thread which updates the UI as songs play
         */
        ExecutorServiceSingleton.instance().execute(() -> {
            Double lastTime = -1.0;
            while (!Thread.interrupted()) {
                if (currentMediaPlayer == null) {
                    continue;
                }

                Double currentTime = currentMediaPlayer.getCurrentTime().toSeconds();

                if (lastTime.equals(currentTime)) {
                    continue;
                }

                lastTime = currentTime;

                Platform.runLater(() -> {
                    if (currentMediaPlayer == null) {
                        return;
                    }

                    synchronized (pauseLock) {
                        if (!paused) {
                            seekSlider.setMax(playlist.getCurrentSong().getDuration());
                            seekSlider.setValue(currentTime * 1000);
                        }
                    }
                });
            }
        });

        seekSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean wasChanging, Boolean changing) {
                if (changing) {
                    pause();
                }

                if (!changing) {
                    seekCurrentSongFromSlider();
                }
            }
        });

        seekSlider.setOnMousePressed(c -> {
                pause();
        });

        seekSlider.setOnMouseReleased(c -> {
            seekCurrentSongFromSlider();

            play();
        });

        seekSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (currentMediaPlayer == null) {
                    return;
                }

                songDurationLabel.setText(String.format("%s - %s", TimeUtilities.formatMilliseconds(newValue.doubleValue()),
                        TimeUtilities.formatMilliseconds(playlist.getCurrentSong().getDuration())));
            }
        });

        initializeTableView();

        initializeButtonHandlers();
    }

    /**
     * Handles the initialization of the table view. Sets the cell value factories, where it sources data from,
     * filter listener, etc.
     */
    private void initializeTableView() {
        filePathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));

        /**
         * Double-click handler for the table view
         */
        mediaTable.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                Audio selectedAudioFile = mediaTable.getSelectionModel().getSelectedItem();

                changeSong(selectedAudioFile);
            }
        });

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

    private void initializeButtonHandlers() {
        previousButton.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }

            if (event.getClickCount() == 2) {
                playlist.previousTrack();
            }

            changeSong(playlist.getCurrentSong());
        });

        nextButton.setOnAction(event -> {
            playlist.skipTrack();

            changeSong(playlist.getCurrentSong());
        });

        pauseButton.setOnAction(event -> {
            if (paused) {
                play();
            } else {
                pause();
            }
        });
    }

    private void pause() {
        if (currentMediaPlayer == null) {
            return;
        }

        synchronized (pauseLock) {
            paused = true;

            pauseButton.setText(PLAY_TEXT);

            currentMediaPlayer.pause();
        }
    }

    private void play() {
        if (currentMediaPlayer == null) {
            return;
        }

        synchronized (pauseLock) {
            paused = false;

            pauseButton.setText(PAUSE_TEXT);

            currentMediaPlayer.play();
        }
    }

    /**
     * Changes the currently playing song and updates all UI components
     * @param audio
     */
    private synchronized void changeSong(Audio audio) {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.stop();
        }

        try {
            Media newMedia = new Media(new File(audio.getPath()).toURI().toString());
            currentMediaPlayer = new MediaPlayer(newMedia);
            currentMediaPlayer.setOnEndOfMedia(() -> {
                playlist.skipTrack();
                changeSong(playlist.getCurrentSong());
            });

            play();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return;
        }

        songInfoLabel.setText(String.format("%s - %s", audio.getArtist(), audio.getTitle()));
    }

    public void setFiles(Collection<AudioFile> audioFiles) {
        masterFiles.setAll(audioFiles
                .stream()
                .map(Audio::fromAudioFile)
                .collect(Collectors.toList())
        );
    }


    private void seekCurrentSongFromSlider() {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.seek(new Duration(seekSlider.getValue()));
        }
    }
}
