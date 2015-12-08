package org.madhatters.mediaplayer.media;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;

import java.io.File;

/**
 * Created by RyanMalmoe on 11/6/15.
 * Stores Metadata information from media files
 */


//SONG NAME IS THE FILENAME MINUS THE EXTENSION.

public class Mp3File extends AudioFile {
    public Mp3File(String filePath) {
        this.filePath = filePath;
    }

    public Mp3File(File f)
    {
        Metadata metadata = parse(f);

        if (metadata == null) {
            throw new IllegalArgumentException("f is not a valid MP3 file");
        }

        populateFields(metadata, f);
    }

    protected Parser getParser() {
        return new Mp3Parser();
    }

}
