import java.io.File;
import java.util.Iterator;
import FileWalker.FileWalker;
import MediaParser.MediaParser;
import MediaFile.MediaFile;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList <MediaFile> mediaFileArrayList = loadMediaMap();

    }


    /**
     *
     * @param
     * @return New ArrayList of Media files stored by their filenames
     */
    public static ArrayList<MediaFile> loadMediaMap() {
        ArrayList <MediaFile> mediaFileArrayList = new ArrayList<MediaFile>();
        FileWalker fw = new FileWalker();
        Iterator myFiles = fw.listFiles();
        String fileLocation;
        while(myFiles.hasNext()) {
            File file = (File) myFiles.next();
            fileLocation = file.getAbsolutePath();
            MediaParser mp = new MediaParser();
            MediaFile mf = mp.getMediaFileObjectWithFileLocation(fileLocation, file.getName());
            mf.printFileName();
            mf.printArtistName();
            mediaFileArrayList.add(mf);
        }
        return mediaFileArrayList;
    }


}
