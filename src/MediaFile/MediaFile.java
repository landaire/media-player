package MediaFile;

import javafx.application.Application;
import javafx.scene.media.Media;
import java.nio.file.Paths;

/**
 * Created by RyanMalmoe on 11/6/15.
 * Stores Metadata information from media files
 */


//SONG NAME IS THE FILENAME MINUS THE EXTENSION.

public class MediaFile {
    private String fileName;
    private String artistName;
    private String songTitle;
    private String album;
    private Media media;


    public MediaFile(String fileLocation, String fileName, String artistName, String songTitle, String album) {
        this.fileName = fileName;
        this.artistName = artistName;
        this.songTitle = songTitle;
        this.album = album;
        this.media = new Media(Paths.get(fileLocation).toUri().toString());
    }

    public void printFileName() {
        System.out.println(this.fileName);
    }
    public void printSongTitle() {
        System.out.println(this.songTitle);
    }
    public void printArtistName() { System.out.println(this.artistName); }
    public Media getMedia() {
        return this.media;
    }
    public String getFileName() {
        return this.fileName;
    }
    public String getArtistName() {
        return this.artistName;
    }
    public String getSongTitle() {
        return this.songTitle;
    }
    public String getAlbum() {
        return this.album;
    }

}
