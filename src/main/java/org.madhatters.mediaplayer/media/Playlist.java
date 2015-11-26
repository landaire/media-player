package org.madhatters.mediaplayer.media;

import javafx.collections.ObservableList;
import org.madhatters.mediaplayer.models.Mp3;

/**
 * Created by RyanMalmoe on 11/24/15.
 */
public class Playlist {

    private static final int FIRSTSONG = 0;


    private ObservableList<Mp3> files;
        private Mp3 currentSong;
        private int currentSongIndex;


    /**
     * Constructor for Playlist class, initializes a current playlist from
     * the located mp3 files.
     * @param foundFiles
     */
    public Playlist(ObservableList<Mp3> foundFiles) {

        //  Check with lander on this base case.
        //  Do we want to create a playlist that is empty if no files are found?
        if(foundFiles == null || foundFiles.size() == 0) {
            throw new IllegalArgumentException("Can not create a playlist with 0 mp3 files");
        }
            this.files = foundFiles;
            currentSongIndex = 0;
            this.currentSong = this.files.get(FIRSTSONG);
        }

        public Mp3 getCurrentSong() { return this.currentSong; }


    /**
     * Skips to the next track in the playlist
     */
    public void skipTrack() {
            if(this.currentSongIndex == this.files.size() - 1) {
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
            if(this.currentSongIndex == 0) {
                this.currentSongIndex = files.size() - 1;
                this.currentSong = files.get(this.currentSongIndex);
            } else {
                this.currentSongIndex--;
                this.currentSong = this.files.get(currentSongIndex);
            }
        }

    /**
     * Removes specified song from the playlist
     * @param mp3
     */
    public void removeSong(Mp3 mp3) {
            if(this.files.size() == 0) {
                return;
            } else if(this.files.size() == 1) {
                this.files.remove(mp3);
                this.files = null;
                this.currentSong = null;
                return;
            }
            skipTrack();
            files.remove(mp3);
        }


}
