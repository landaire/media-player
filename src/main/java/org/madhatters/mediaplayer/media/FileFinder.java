package org.madhatters.mediaplayer.media;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;


/**
 * Created by RyanMalmoe on 11/6/15.
 */


public class FileFinder {
    private static String [] fileExtensions = {"mp3", "mp4"};
                                //Parameters of file start location
    public static Collection<Mp3File> findIn(String directory) {
        return FileUtils.listFiles(new File(directory), fileExtensions, true)
                .stream()
                .filter(Mp3File::isValid)
                .map(f -> new Mp3File(f))
                .collect(Collectors.toList());
    }
}
