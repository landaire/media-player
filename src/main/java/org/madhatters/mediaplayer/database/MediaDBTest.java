package org.madhatters.mediaplayer.database;

import org.madhatters.mediaplayer.media.AudioFile;
import org.madhatters.mediaplayer.media.FileFinder;

import java.util.Collection;

public class MediaDBTest {
    public static void main(String[] args) {
        MediaDB mediaPlayer;
        mediaPlayer = new MediaDB();

        FileFinder allFiles;
        allFiles = new FileFinder();

        System.out.println("START");
        Collection<AudioFile> songs;
        songs = allFiles.findIn("C:/Users/Joey/Music/AllMusic/Linkin Park");
        System.out.println("DONE");

        mediaPlayer.addAudioFiles(songs);

        mediaPlayer.closeDB();
    }
}
