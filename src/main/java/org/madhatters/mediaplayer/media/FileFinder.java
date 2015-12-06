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
    private static String [] fileExtensions = {"mp3"};

    public static Collection<Mp3File> findIn(String directory) {
        // empty lambda so nothing is done on callback
        return findIn(directory, f -> null);
    }

    public static Collection<Mp3File> findIn(String directory, Function<File, Void> onVisit) {
        ArrayList<Mp3File> files = new ArrayList<>();
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
                     File pathAsFile = file.toFile();
                     if (!FilenameUtils.isExtension(pathAsFile.getName(), fileExtensions)) {
                         return FileVisitResult.CONTINUE;
                     }

                     // File extension is valid, check if it's a valid MP3 file
                     if (Mp3File.isValid(pathAsFile)) {
                         files.add(new Mp3File(pathAsFile));
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
