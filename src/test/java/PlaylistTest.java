import org.junit.Test;
import org.madhatters.mediaplayer.models.AudioFile;

// These imports are for creating ObservableList
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.madhatters.mediaplayer.media.FileFinder;
import org.madhatters.mediaplayer.media.Playlist;

import java.util.stream.Collectors;

/**
 * Created by RyanMalmoe on 11/26/15.
 *
 * 8 of 8 unit tests complete
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



    @Test
            (expected=IndexOutOfBoundsException.class) public void testGetSongAtIndex() throws Exception {
        ObservableList<AudioFile> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new AudioFile(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        playlist.getSongAtIndex(0);
        playlist.getSongAtIndex(-1);
        playlist.getSongAtIndex(3);

    }

    @Test
            (expected=IllegalArgumentException.class) public void testPlaylistInitialization() throws Exception {
        ObservableList<AudioFile> files = null;
        Playlist playlist = new Playlist(files);
    }

    @Test
    public void testGetCurrentSong() throws Exception {
        ObservableList<AudioFile> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new AudioFile(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());
    }

    @Test
            (expected=IllegalArgumentException.class) public void testSkipTrack() throws Exception {
        ObservableList<AudioFile> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new AudioFile(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
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

    @Test
            (expected=IllegalArgumentException.class) public void testPreviousTrack() throws Exception {
        ObservableList<AudioFile> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new AudioFile(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
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

    @Test
    public void testRemoveSong() throws Exception {
        ObservableList<AudioFile> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new AudioFile(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
                .collect(Collectors.toList())
        );

        Playlist playlist = new Playlist(files);
        assertEquals("/Users/RyanMalmoe/Documents/TestSong/TestSong.mp3", playlist.getCurrentSong().getPath());
        playlist.removeSong(playlist.getCurrentSong());
        assertEquals("/Users/RyanMalmoe/Documents/THEPRAYER.mp3", playlist.getCurrentSong().getPath());
    }

    @Test
            (expected=IllegalArgumentException.class) public void testRemoveSongByIndex() throws Exception {
        ObservableList<AudioFile> files;
        files = FXCollections.observableArrayList(FileFinder.findIn("/Users/RyanMalmoe/Documents")
                .stream()
                .map(f -> new AudioFile(f.getFilePath(), f.getArtistName(), f.getSongTitle(), f.getAlbum()))
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
