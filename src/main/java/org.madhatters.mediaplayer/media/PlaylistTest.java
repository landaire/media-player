package org.madhatters.mediaplayer.media;

import org.madhatters.mediaplayer.models.Mp3;

import static groovy.util.GroovyTestCase.assertEquals;
import static org.junit.Assert.*;


// These imports are for creating ObservableList

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.madhatters.mediaplayer.media.FileFinder;
import org.madhatters.mediaplayer.media.Playlist;
import org.madhatters.mediaplayer.models.Mp3;

import java.util.stream.Collectors;

/**
 * Created by RyanMalmoe on 11/26/15.
 *
 * 4 of 4 unit tests complete
 *
 */
public class PlaylistTest {


    @org.junit.Test
    public void testGetCurrentSong() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());
    }

    @org.junit.Test
    public void testSkipTrack() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        playlist.skipTrack();
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", playlist.getCurrentSong().getPath());

        //After skipping the track again it should loop back to the beginning track.
        playlist.skipTrack();
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());

    }

    @org.junit.Test
    public void testPreviousTrack() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        playlist.previousTrack();
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", playlist.getCurrentSong().getPath());

        //After skipping the track again it should loop back to the beginning track.
        playlist.previousTrack();
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());

    }

    @org.junit.Test
    public void testRemoveSong() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());
        playlist.removeSong(playlist.getCurrentSong());
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", playlist.getCurrentSong().getPath());

    }
}