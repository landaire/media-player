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
    protected String filePath;
    protected String artistName;
    protected String songTitle;
    protected String album;
    protected Media media;
    protected Double duration;

    private Metadata metaData;

    public void populateFields() {
        File file = new File(this.filePath);

        populateFields(this.parse(file), file);
    }

    /**
     * Populates all of the fields for an AudioFile with the correct metadata
     * @param metadata
     * @param file
     */
    protected void populateFields(Metadata metadata, File file) {
        this.filePath = file.getAbsolutePath();
        this.artistName = metadata.get("xmpDM:artist");
        this.songTitle = metadata.get("title");
        this.album = metadata.get("xmpDM:album");
        this.duration = Double.parseDouble(metadata.get("xmpDM:duration"));
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getDuration() {
        return duration;
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

    /**
     * Checks if a the given file is valid
     * @param file
     */
    public boolean isValid(File file) {
        if (this.metaData != null) {
            return true;
        }

        this.metaData = parse(file);

        return this.metaData != null;
    }

    /**
     * Parses a file at a given path and extracts the metadata from
     * the file.
     * @param file
     */
    protected Metadata parse(File file) {
        if (this.metaData != null) {
            return metaData;
        }

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
