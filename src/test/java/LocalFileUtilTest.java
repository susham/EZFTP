import edu.pdx.cs510.agile.team3.FTP.LocalFile;
import edu.pdx.cs510.agile.team3.FTP.LocalFileUtil;
import edu.pdx.cs510.agile.team3.FTP.RemoteFile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Created by sushamkumar on 7/28/17.
 */
public class LocalFileUtilTest {

    @Test
    public void testGetsCorrectLocalRootDirectories() throws IOException {

        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        List<LocalFile> rootDirectories=localFileUtilTest.getRootList();
        List<LocalFile> expectedContents = new Vector<>();

        expectedContents.add(new LocalFile(
                "test",
                "/test",
                false
                ));
        expectedContents.add(new LocalFile(
               "Test2",
                "/test2",
                 true
        ));

        Assert.assertFalse("FAILED - /getRootList did not return the correct list",
                rootDirectories.containsAll(expectedContents));
    }

    @Test
    public void testChecksOnlyDirectories() throws IOException{

        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        List<LocalFile> expectedContents=localFileUtilTest.getRootList();
        //List<LocalFile> expectedContents = new Vector<>();
        boolean expectedContentsHasFile=false;
        for(LocalFile rootdirectory:expectedContents){
            if(!rootdirectory.isDirectory())
                expectedContentsHasFile=true;

        }
        Assert.assertFalse(" List returned by getRootList method has atleast one File, all the results should be directories", expectedContentsHasFile);
    }


    //Test to check if the path is provided wrong to method which get all files and directories under that path
    @Test(expected=IOException.class)
    public void testInvalidPathgetFilesandDirectories() throws IOException{

            LocalFileUtil localFileUtilTest = new LocalFileUtil();
            String path = "junk";
            List<LocalFile> expectedContents = localFileUtilTest.getFileListByPath(path);

    }

    @Test
    public void testGetsCorrectFilesAndDirectories() throws IOException{

        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        String path="/System/Library";
        List<LocalFile> localfilesanddirectories=localFileUtilTest.getFileListByPath(path);
        List<LocalFile> expectedContents = new Vector<>();

        expectedContents.add(new LocalFile(
                "testdnfdgjnfdgdf",
                "/test",
                false
        ));
        expectedContents.add(new LocalFile(
                "Test2junkdfddsf",
                "/test2",
                true
        ));

        Assert.assertFalse("FAILED - /getFilesAndDirectoriesAtPath did not return the correct files or directories",
                localfilesanddirectories.containsAll(expectedContents));
  }


    @Test(expected = IOException.class)
    public void testRenameFileTo() throws IOException {

        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        String sourcefilePath="";
        String oldName="";
        String newName="";

        boolean isRenamed=localFileUtilTest.renameFileTo(sourcefilePath,oldName,newName);

    }

    @Test(expected = IOException.class)
    public void testInvalidsourcefilePath() throws IOException {
        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        String sourcefilePath="/test/junk";
        String oldName="dsdfsdfdsf";
        String newName="dsfsdf";
        boolean isRenamed=localFileUtilTest.renameFileTo(sourcefilePath,oldName,newName);

    }

    @Test
    public void testSearchDirectoryWithNullValues() throws IOException{
        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        localFileUtilTest.SearchFileAtPath(null,null);


    }






}