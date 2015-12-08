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

    public AudioFile produceAudioFile(File f, String fileExtension) {

            return determineParser(f, fileExtension);
    }

    public static AudioFile determineParser(File file, String fileExtension) {
        if(fileExtension.equalsIgnoreCase(".mp3")) {
            return new Mp3File(file);
        } else if(fileExtension.equalsIgnoreCase(".mid")) {
            return new MidiFile(file);
        }
        return null;
    }


}
