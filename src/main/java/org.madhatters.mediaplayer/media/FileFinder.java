package org.madhatters.mediaplayer.media;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Iterator;


/**
 * Created by RyanMalmoe on 11/6/15.
 */


public class FileFinder {
    private static String [] fileExtentions = {"mp3", "mp4"};
                                //Parameters of file start location
    public static Iterator<File> findIn(String directory) {
        Iterator files = FileUtils.iterateFiles(new File(directory), fileExtentions, true);

        return files;
    }
}
