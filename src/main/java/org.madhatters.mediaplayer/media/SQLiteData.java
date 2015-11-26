package org.madhatters.mediaplayer.media;

/**
 * Created by RyanMalmoe on 11/20/15.
 */


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLiteData {

    File databaseFile;

    public SQLiteData() {
        createDatabase();
    }

    public void deleteMediaFile() {

    }

    //Create a database file for user
    public void createDatabase() {
                                //This eventually could be the given path for media files.
        File dbFile = new File("/Users/RyanMalmoe/Documents/mediafiles.db");
        this.databaseFile = dbFile;
    }

    public void createMediaTable() {
        Connection connection = null;
        try {

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseFile);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists mediafiles");
            statement.executeUpdate("create table mediafiles (path string, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
