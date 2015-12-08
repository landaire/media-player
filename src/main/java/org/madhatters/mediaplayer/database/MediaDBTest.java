package org.madhatters.mediaplayer.database;

import org.madhatters.mediaplayer.media.AudioFile;
import org.madhatters.mediaplayer.media.FileFinder;

import java.util.Collection;
import java.util.Iterator;

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
        Collection<AudioFile> allSongs = mediaPlayer.getAudioFiles();

        Iterator<AudioFile> itr = allSongs.iterator();
        while (itr.hasNext()) {
            AudioFile curSong = itr.next();
            System.out.println("Song: " + curSong.getSongTitle());
            System.out.println("Artist: " + curSong.getArtistName());
            System.out.println("Album: " + curSong.getAlbum());
            System.out.println("Filepath: " + curSong.getFilePath());
            System.out.println();
        }

        mediaPlayer.closeDB();
    }
}
