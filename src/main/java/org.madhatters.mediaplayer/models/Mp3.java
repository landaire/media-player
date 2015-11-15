package org.madhatters.mediaplayer.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Lander Brandt on 11/15/15.
 */
public class Mp3 {
    private final SimpleStringProperty path;
    private final SimpleStringProperty artist;
    private final SimpleStringProperty title;
    private final SimpleStringProperty album;

    public Mp3(String path, String artist, String title, String album) {
        this.path = new SimpleStringProperty(path);
        this.artist = new SimpleStringProperty(artist);
        this.title = new SimpleStringProperty(title);
        this.album = new SimpleStringProperty(album);
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
}
