package org.madhatters.mediaplayer.media;

import javafx.scene.media.Media;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by RyanMalmoe on 12/6/15.
 */
public abstract class AudioFile {
    private String filePath;
    private String artistName;
    private String songTitle;
    private String album;
    private Media media;
    private Double duration;

    protected void populateFields(Metadata metadata, File file) {
        this.filePath = file.getAbsolutePath();
        this.artistName = metadata.get("xmpDM:artist");
        this.songTitle = metadata.get("title");
        this.album = metadata.get("xmpDM:album");
        this.duration = Double.parseDouble(metadata.get("xmpDM:duration"));
    }

    public Double getDuration() {
        return duration;
    }

    public Media getMedia() {
        return this.media;
    }
    public String getFilePath() {
        return this.filePath;
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

    public boolean isValid(File file) { return parse(file) != null; }

    protected Metadata parse(File file) {
        Parser parser = getParser();
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

    abstract protected Parser getParser();
}
