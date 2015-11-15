package org.madhatters.mediaplayer.media;

import javafx.scene.media.Media;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.*;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    public MediaFile(File f)
    {
        Metadata metadata = parse(f);

        if (metadata == null) {
            throw new IllegalArgumentException("f is not a valid MP3 file");
        }

        this.fileName = f.getName();
        this.artistName = metadata.get("xmpDM:artist");
        this.songTitle = metadata.get("title");
        this.album = metadata.get("xmpDM:album");
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

    public static boolean isValid(File file) {
        return parse(file) != null;
    }

    protected static Metadata parse(File file) {
        Parser parser = new Mp3Parser();
        InputStream stream;
        Metadata metadata = new Metadata();


        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }

        try {
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
        } catch (Exception e) {
            return null;
        } finally {
            // java is freaking stupid. why would this ever throw an exception?
            try {
                stream.close();
            } catch (Exception e) {}
        }

        return metadata;
    }
}
