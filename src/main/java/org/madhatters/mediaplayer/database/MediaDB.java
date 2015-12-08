package org.madhatters.mediaplayer.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.madhatters.mediaplayer.media.AudioFactory;
import org.madhatters.mediaplayer.media.AudioFile;

public class MediaDB {
    private Connection con;
    private String dbName;
    
    public MediaDB() {
        this("MediaPlayer");
    }

    public MediaDB(String name) {
        this.dbName = name;
        this.con = null;
        
        setupDatabase();
    }

    public void addAudioFiles(Collection<AudioFile> files) {
        if (files.isEmpty()) {
            throw new IllegalArgumentException("Cannot add zero files");
        } else {
            Iterator<AudioFile> itr = files.iterator();

            while (itr.hasNext()) {
                AudioFile curFile = itr.next();

                String insert = "INSERT INTO audio VALUES (?, ?, ?, ?, ?)";

                try {
                    PreparedStatement stmt = con.prepareStatement(insert);
                    stmt.setQueryTimeout(30);

                    stmt.setString(1, curFile.getFilePath());
                    stmt.setString(2, curFile.getArtistName());
                    stmt.setString(3, curFile.getSongTitle());
                    stmt.setString(4, curFile.getAlbum());
                    stmt.setString(5, FilenameUtils.getExtension(curFile.getFilePath()));

                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        }
    }

    public Collection<AudioFile> getAudioFiles() {
        String sql = "SELECT filepath FROM audio";
        ArrayList<AudioFile> files = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            AudioFactory audioFactory = new AudioFactory();

            while (result.next()) {
                String filePath = result.getString("filepath");
                AudioFile audioFile = audioFactory.produceAudioFile(new File(filePath), FilenameUtils.getExtension(filePath));
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

    public String getDBName() {
        return this.dbName;
    }

    private void setupDatabase() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + this.dbName + ".db");
            createSongTable("audio");
            
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    private void createDBTable(String name, String create) {
        try {
            String drop = "DROP TABLE IF EXISTS " + name;
            PreparedStatement stmt = con.prepareStatement(drop);
            stmt.setQueryTimeout(30);
            stmt.execute();

            stmt = con.prepareStatement(create);
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
                            "type String" +
                        ")";
        createDBTable(name, sql);
    }
}
