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


        public Playlist(ObservableList<Mp3> foundFiles) {
            this.files = foundFiles;
            currentSongIndex = 0;
            this.currentSong = this.files.get(FIRSTSONG);
        }

        public Mp3 getCurrentSong() { return this.currentSong; }

        public void skipTrack() {
            if(this.currentSongIndex == this.files.size() - 1) {
                this.currentSongIndex = 0;
                this.currentSong = files.get(this.currentSongIndex);
            } else {
                this.currentSongIndex++;
                this.currentSong = this.files.get(currentSongIndex);
            }
        }

        public void previousTrack() {
            if(this.currentSongIndex == 0) {
                this.currentSongIndex = files.size() - 1;
                this.currentSong = files.get(this.currentSongIndex);
            } else {
                this.currentSongIndex--;
                this.currentSong = this.files.get(currentSongIndex);
            }
        }

        //Need to create edge case where there is 0 and 1 songs in playlist.
        public void removeSong(Mp3 mp3) {
            skipTrack();
            files.remove(mp3);
        }


}
