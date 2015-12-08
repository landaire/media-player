package org.madhatters.mediaplayer.media;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void addMp3(Mp3File mediaFile) {
        String insert = "INSERT INTO audio VALUES (" +
                            "\"" + mediaFile.getFilePath() + "\", " +
                            "\"" + mediaFile.getArtistName() + "\", " +
                            "\"" + mediaFile.getSongTitle() + "\", " +
                            "\"" + mediaFile.getAlbum() + "\", " +
                            "\"mp3\"" +
                        ")";

        try {
            PreparedStatement stmt = con.prepareStatement(insert);
            stmt.setQueryTimeout(20);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
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

    /*public String getDBName() {
        return this.dbName;
    }*/
}
