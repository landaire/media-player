package org.madhatters.mediaplayer.media;

/**
 * Created by RyanMalmoe on 12/6/15.
 */

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.audio.MidiParser;

import java.io.File;

/**
 * Created by RyanMalmoe on 11/6/15.
 * Stores Metadata information from media files
 */


//SONG NAME IS THE FILENAME MINUS THE EXTENSION.

public class MidiFile extends AudioFile {

    /**
     * Sets the filepath for an MidiFile object
     * @param filePath
     */
    public MidiFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Parses the MidiFile and populates the fields with the parsed metadata
     * @param f
     */
    public MidiFile(File f)
    {
        Metadata metadata = parse(f);

        if (metadata == null) {
            throw new IllegalArgumentException("f is not a valid Midi file");
        }

        populateFields(metadata, f);
    }

    protected Parser getParser() {
        return new MidiParser();
    }
}
