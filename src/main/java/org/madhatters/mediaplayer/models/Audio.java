package org.madhatters.mediaplayer.models;

import javafx.beans.property.SimpleStringProperty;
import org.madhatters.mediaplayer.media.AudioFile;
import org.madhatters.mediaplayer.util.TimeUtilities;

/**
 * Created by Lander Brandt on 11/15/15.
 */
public class Audio {
    private final SimpleStringProperty path;
    private final SimpleStringProperty artist;
    private final SimpleStringProperty title;
    private final SimpleStringProperty album;
    private SimpleStringProperty length;

    private Double duration;

    public Audio(String path, String artist, String title, String album) {
        artist = defaultValue(artist, "Artist");
        title = defaultValue(title, "Title");
        album = defaultValue(title, "Album");

        this.path = new SimpleStringProperty(path);
        this.artist = new SimpleStringProperty(artist);
        this.title = new SimpleStringProperty(title);
        this.album = new SimpleStringProperty(album);
    }

    public static Audio fromAudioFile(AudioFile file) {
        Audio that = new Audio(file.getFilePath(), file.getArtistName(), file.getSongTitle(), file.getAlbum());
        that.duration = file.getDuration();
        that.length = new SimpleStringProperty(TimeUtilities.formatMilliseconds(file.getDuration()));

        return that;
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public String getArtist() {
        return artist.get();
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAlbum() {
        return album.get();
    }

    public void setAlbum(String album) {
        this.album.set(album);
    }

    public Double getDuration() {
        return duration;
    }

    private String defaultValue(String value, String name) {
        if (value == null || value.isEmpty()) {
            return String.format("Unknown %s", name);
        }

        return value;
    }

    public String getLength() {
        return length.get();
    }
}
