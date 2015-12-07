package org.madhatters.mediaplayer.media;

import javafx.collections.ObservableList;
import org.madhatters.mediaplayer.models.AudioFile;

/**
 * Created by RyanMalmoe on 11/24/15.
 */
public class Playlist {

    private static final int FIRSTSONG = 0;


    private ObservableList<AudioFile> files;
        private AudioFile currentSong;
        private int currentSongIndex;


    /**
     * Constructor for Playlist class, initializes a current playlist from
     * the located mp3 files.
     * @param foundFiles
     */
    public Playlist(ObservableList<AudioFile> foundFiles) {

        //  Check with lander on this base case.
        //  Do we want to create a playlist that is empty if no files are found?
        if(foundFiles == null || foundFiles.size() == 0) {
            throw new IllegalArgumentException("Can not create a playlist with 0 mp3 files");
        }
            this.files = foundFiles;
            currentSongIndex = 0;
            this.currentSong = this.files.get(FIRSTSONG);
        }

        public AudioFile getCurrentSong() { return this.currentSong; }


    public AudioFile getSongAtIndex(int songIndex) {
        if(songIndex < 0 || songIndex > files.size() - 1) {
            throw new IndexOutOfBoundsException("Song index out of bounds");
        }
        return files.get(songIndex);
    }

    /**
     * Skips to the next track in the playlist
     */
    public void skipTrack() {
            if(files.size() == 0) {
                throw new IllegalArgumentException("Cannot skip an empty playlist");
            } else if(this.currentSongIndex == this.files.size() - 1) {
                this.currentSongIndex = 0;
                this.currentSong = files.get(this.currentSongIndex);
            } else {
                this.currentSongIndex++;
                this.currentSong = this.files.get(currentSongIndex);
            }
        }

    /**
     * Skips to the previous track in the playlist
     */
    public void previousTrack() {
             if(files.size() == 0) {
                throw new IllegalArgumentException("Cannot skip an empty playlist");
             } else if(this.currentSongIndex == 0) {
                this.currentSongIndex = files.size() - 1;
                this.currentSong = files.get(this.currentSongIndex);
            } else {
                this.currentSongIndex--;
                this.currentSong = this.files.get(currentSongIndex);
            }
        }

    /**
     * Removes specified song from the playlist
     * @param audioFile
     */
    public void removeSong(AudioFile audioFile) {
            if(this.files.size() == 0) {
                return;
            } else if(this.files.size() == 1) {
                this.files.remove(audioFile);
                this.files = null;
                this.currentSong = null;
                return;
            }
            skipTrack();
            this.files.remove(audioFile);
            this.currentSongIndex--;
        }

    /**
     * Removes song by given index from playlist
     * @param songIndex
     */

    public void removeSongByIndex(int songIndex) {
        if(this.files.size() == 0 || songIndex < 0 || songIndex > this.files.size() - 1) {
            throw new IllegalArgumentException("No song at this index");
        } else {
            skipTrack();
            files.remove(files.get(songIndex));
            this.currentSongIndex--;
        }
    }


}
