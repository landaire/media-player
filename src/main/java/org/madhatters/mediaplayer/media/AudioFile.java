package org.madhatters.mediaplayer.media;

import javafx.scene.media.Media;
import org.apache.tika.metadata.Metadata;

import java.io.File;

/**
 * Created by RyanMalmoe on 12/6/15.
 */
public abstract class AudioFile {

    abstract public boolean isValid(File file);
    abstract protected Metadata parse(File file);
    abstract public void printFileName();
    abstract public void printSongTitle();
    abstract public void printArtistName();
    abstract public Media getMedia();
    abstract public String getFilePath();
    abstract public String getArtistName();
    abstract public String getSongTitle();
    abstract public String getAlbum();

}
