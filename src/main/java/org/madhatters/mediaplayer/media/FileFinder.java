package org.madhatters.mediaplayer.media;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Created by RyanMalmoe on 11/6/15.
 */


public class FileFinder {
    private static String [] fileExtensions = {"mp3", "mp4", "mid"};

    public static Collection<AudioFile> findIn(String directory) {
        // empty lambda so nothing is done on callback
        return findIn(directory, f -> null);
    }

    public static Collection<AudioFile> findIn(String directory, Function<File, Void> onVisit) {
        ArrayList<AudioFile> files = new ArrayList<>();
         try {
             Files.walkFileTree(new File(directory).toPath(), new FileVisitor<Path>() {
                 @Override
                 public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                     if (Thread.currentThread().isInterrupted()) {
                         return FileVisitResult.SKIP_SUBTREE;
                     }

                     onVisit.apply(dir.toFile());

                     return FileVisitResult.CONTINUE;
                 }

                 @Override
                 public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                     // Validate the file extension
                     boolean validExtension = false;
                     for (String extension : fileExtensions) {
                         if (file.endsWith("." + extension)) {
                             validExtension = true;
                             break;
                         }
                     }
                     if (!validExtension) {
                         return FileVisitResult.CONTINUE;
                     }

                     //Create AudioFactory object
                     //Produce an audio file from the factory based on file extension
                     //If AudioFile is valid then add to array list.
                     AudioFactory audioFactory = new AudioFactory();
                     AudioFile af = audioFactory.produceAudioFile(file.toFile(), FilenameUtils.getExtension(file.toString()));
                     if (af.isValid(file.toFile())) {
                         files.add(af);
                     }

                     return FileVisitResult.CONTINUE;
                 }

                 @Override
                 public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                     return FileVisitResult.CONTINUE;
                 }

                 @Override
                 public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                     return FileVisitResult.CONTINUE;
                 }
             });
         } catch (IOException e) {
             return null;
         }

        return files;
    }
}
