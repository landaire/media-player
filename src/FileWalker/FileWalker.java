package FileWalker;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.Iterator;


/**
 * Created by RyanMalmoe on 11/6/15.
 */


public class FileWalker {

    private String [] fileExtentions = {"mp3", "mp4"};
                                //Parameters of file start location
    public Iterator<File> listFiles() {
        Iterator files = FileUtils.iterateFiles(new File("/Users/ryanmalmoe/Documents"), fileExtentions, true);
        return files;
    }



}
