package org.madhatters.mediaplayer.media;

import java.util.Collection;
import java.util.Iterator;

public class MediaDBTest {
    public static void main(String[] args) {
        MediaDB mediaPlayer = new MediaDB();

        FileFinder allFiles = new FileFinder();
        System.out.println("START");
        Collection<Mp3File> songs = allFiles.findIn("C:/Users/Joey/Music");
        System.out.println("DONE");

        Iterator<Mp3File> itr = songs.iterator();
        while (itr.hasNext()) {
            Mp3File song = itr.next();
            System.out.println("Filepath: " + song.getFilePath());
            System.out.println("Title: " + song.getSongTitle());
            System.out.println("Artist: " + song.getArtistName());
            System.out.println("Album: " + song.getAlbum());
            System.out.println();
            mediaPlayer.addMp3(song);
        }

        mediaPlayer.closeDB();
    }
}
