import org.madhatters.mediaplayer.models.Mp3;

// These imports are for creating ObservableList
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.madhatters.mediaplayer.media.FileFinder;
import org.madhatters.mediaplayer.media.Playlist;
import org.madhatters.mediaplayer.models.Mp3;

import java.util.stream.Collectors;

/**
 * Created by RyanMalmoe on 11/26/15.
 *
 * 7 of 8 unit tests complete
 *
 * Next job:
 *  -Test get current song (Done)
 *  -Test skip track (Done)
 *  -Test previous track (Done)
 *  -Test remove song (Done)
 *  -Test create playlist with 0 files or null observable list (Done)
 *  -Test skip track with 0 or 1 files in playlist (Done)
 *  -Test previous track with 0 or 1 files in playlist (Done)
 *  -Test getSongAtIndex for all cases
 *
 */
public class PlaylistTest {



    @org.junit.Test
            (expected=IndexOutOfBoundsException.class) public void testGetSongAtIndex() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        playlist.getSongAtIndex(0);
        playlist.getSongAtIndex(-1);
        playlist.getSongAtIndex(3);

    }

    @org.junit.Test
            (expected=IllegalArgumentException.class) public void testPlaylistInitialization() throws Exception {
        ObservableList<Mp3> files = null;
        Playlist playlist = new Playlist(files);
    }

    @org.junit.Test
    public void testGetCurrentSong() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());
    }

    @org.junit.Test
            (expected=IllegalArgumentException.class) public void testSkipTrack() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        playlist.skipTrack();
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", playlist.getCurrentSong().getPath());

        playlist.skipTrack();
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());

        //EDGE CASE FOR SKIPPING TRACK
        playlist.removeSongByIndex(0);
        playlist.removeSongByIndex(0);
        playlist.skipTrack();


    }

    @org.junit.Test
            (expected=IllegalArgumentException.class) public void testPreviousTrack() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        playlist.previousTrack();
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", playlist.getCurrentSong().getPath());

        playlist.previousTrack();
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());

        //EDGE CASE FOR PREVIOUS TRACK
        playlist.removeSongByIndex(0);
        playlist.removeSongByIndex(0);
        playlist.previousTrack();


    }

    @org.junit.Test
    public void testRemoveSong() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());
        playlist.removeSong(playlist.getCurrentSong());
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", playlist.getCurrentSong().getPath());
    }

    @org.junit.Test
            (expected=IllegalArgumentException.class) public void testRemoveSongByIndex() throws Exception {
        ObservableList<Mp3> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new Mp3(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());
        playlist.removeSongByIndex(0);
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", playlist.getCurrentSong().getPath());

        //EDGE CASE FOR REMOVING INDEX OUT OF RANGE / ZERO
        playlist.removeSongByIndex(10);

        playlist.removeSongByIndex(0);
        playlist.removeSongByIndex(0);
    }

    private <T> void assertEquals(T a, T b) {
        assert(a.equals(b));
    }
}
