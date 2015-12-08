package org.madhatters.mediaplayer.database;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.madhatters.mediaplayer.media.AudioFactory;
import org.madhatters.mediaplayer.media.AudioFile;

public class MediaDB {
    private Connection con;
    private final static String dbName = "MediaPlayer";

    public MediaDB() {
        this.con = null;
        setupDatabase();
    }

    public void addAudioFiles(Collection<AudioFile> files) {
        if (files.isEmpty()) {
            throw new IllegalArgumentException("Cannot add zero files");
        } else {

            for (AudioFile curFile : files) {
                String insert = "INSERT INTO audio VALUES (?, ?, ?, ?, ?, ?)";

                try {
                    PreparedStatement stmt = con.prepareStatement(insert);
                    stmt.setQueryTimeout(30);

                    stmt.setString(1, curFile.getFilePath());
                    stmt.setString(2, curFile.getArtistName());
                    stmt.setString(3, curFile.getSongTitle());
                    stmt.setString(4, curFile.getAlbum());
                    stmt.setString(5, FilenameUtils.getExtension(curFile.getFilePath()));
                    stmt.setDouble(6, curFile.getDuration());

                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        }
    }

    public Collection<AudioFile> getAudioFiles() {
        String sql = "SELECT * FROM audio";
        ArrayList<AudioFile> files = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            AudioFactory audioFactory = new AudioFactory();

            while (result.next()) {
                String filePath = result.getString("filepath");

                AudioFile audioFile = audioFactory.produceAudioFile(new File(filePath), FilenameUtils.getExtension(filePath));
                audioFile.setAlbum(result.getString("album"));
                audioFile.setSongTitle(result.getString("title"));
                audioFile.setArtistName(result.getString("artist"));
                audioFile.setDuration(result.getDouble("duration"));

                files.add(audioFile);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e);
        }

        return files;
    }


    public void closeDB() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static String getDbPath() {
        return FilenameUtils.concat(FilenameUtils.concat(defaultDirectory(), "MHMP"), dbName + ".db");
    }

    public static boolean databaseExists() {
        return new File(getDbPath()).exists();
    }

    private void setupDatabase() {
        File dbFile = new File(getDbPath());
        File parentDirectory = dbFile.getParentFile();

        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        try {
            boolean databaseNeedsSetup = !databaseExists();

            con = DriverManager.getConnection("jdbc:sqlite:" + getDbPath());

            if (databaseNeedsSetup) {
                createSongTable("audio");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    private void createDBTable(String create) {
        try {
            PreparedStatement stmt = con.prepareStatement(create);
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    private void createSongTable(String name) {
        String sql =    "CREATE TABLE " + name + " (" +
                            "filepath String PRIMARY KEY, " +
                            "artist String, " +
                            "title String, " +
                            "album String, " +
                            "type String, " +
                            "duration real" +
                        ")";

        createDBTable(sql);
    }

    /**
     * http://stackoverflow.com/questions/6561172/find-directory-for-application-data-on-linux-and-macintosh
     * @return
     */
    private static String defaultDirectory()
    {
        String OS = System.getProperty("os.name").toUpperCase();

        if (OS.contains("WIN"))
            return System.getenv("APPDATA");
        else if (OS.contains("MAC"))
            return System.getProperty("user.home") + "/Library/Application "
                    + "Support";
        else if (OS.contains("NUX"))
            return System.getProperty("user.home") + "/.config";
        return System.getProperty("user.dir");
    }
}
