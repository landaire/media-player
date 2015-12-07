package org.madhatters.mediaplayer.media;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import org.madhatters.mediaplayer.models.*;

import java.io.File;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by RyanMalmoe on 12/6/15.
 */
public class AudioFactoryTest {


    @Test
    public void testProduceAudioFile() throws Exception {
        //Test regular .mp3
        File testFile1 = new File("/Users/RyanMalmoe/Documents/THEPRAYER.mp3");
        String fileExtension1 = ".mp3";
        AudioFactory audioFactory1 = new AudioFactory();
        AudioFile audioFile1 = audioFactory1.produceAudioFile(testFile1, fileExtension1);
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", audioFile1.getFilePath());

        //Test case sensitive .MP3
        File testFile2 = new File("/Users/RyanMalmoe/Documents/THEPRAYER.mp3");
        String fileExtension2 = ".MP3";
        AudioFactory audioFactory2 = new AudioFactory();
        AudioFile audioFile2 = audioFactory2.produceAudioFile(testFile2, fileExtension2);
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", audioFile1.getFilePath());

        //Test regular .midi
        File testFile3 = new File("/Users/RyanMalmoe/Documents/HarryPotter.mid");
        String fileExtension3 = ".mid";
        AudioFactory audioFactory3 = new AudioFactory();
        AudioFile audioFile3 = audioFactory3.produceAudioFile(testFile3, fileExtension3);
        assertEquals("/Users/RyanMalmoe/Documents/HarryPotter.mid", audioFile3.getFilePath());

    }

    @Test
    public void testDetermineParser() throws Exception {

        //Test regular .mp3
        File testFile1 = new File("/Users/RyanMalmoe/Documents/THEPRAYER.mp3");
        String fileExtension1 = ".mp3";
        AudioFactory audioFactory1 = new AudioFactory();
        AudioFile audioFile1 = audioFactory1.determineParser(testFile1, fileExtension1);
        Mp3File mp3File = new Mp3File(new File("/Users/RyanMalmoe/Documents/THEPRAYER.mp3"));
        assertEquals(mp3File.getClass(), audioFile1.getClass());

        //Test midi file
        File testFile2 = new File("/Users/RyanMalmoe/Documents/HarryPotter.mid");
        String fileExtension2 = ".mid";
        AudioFactory audioFactory2 = new AudioFactory();
        AudioFile audioFile2 = audioFactory2.determineParser(testFile2, fileExtension2);
        MidiFile midiFile = new MidiFile(new File("/Users/RyanMalmoe/Documents/HarryPotter.mid"));
        assertEquals(midiFile.getClass(), audioFile2.getClass());

    }
}