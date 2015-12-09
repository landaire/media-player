package org.madhatters.mediaplayer.media;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.audio.MidiParser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.parser.mp4.MP4Parser;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by RyanMalmoe on 12/6/15.
 */


public class AudioFactory {

    /**
     * Calls the determineParser (builder) function from a given file and extension.
     * @param f
     * @param fileExtension
     */
    public AudioFile produceAudioFile(File f, String fileExtension) {
        return determineParser(f, fileExtension);
    }

    /**
     * Determines what media type to build based on the
     * given file extension given.
     * @param file
     * @param fileExtension
     */
    public static AudioFile determineParser(File file, String fileExtension) {
        if (fileExtension.equalsIgnoreCase("mp3")) {
            return new Mp3File(file.getPath());
        } else if (fileExtension.equalsIgnoreCase("mid")) {
            return new MidiFile(file.getPath());
        }

        return null;
    }


}
