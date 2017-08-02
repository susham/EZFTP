import edu.pdx.cs510.agile.team3.FTP.LocalFile;
import edu.pdx.cs510.agile.team3.FTP.LocalFileUtil;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
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
    public void testRenameFileToWithBlankValues() throws IOException {

        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        String sourceFilePath="";
        String oldName="";
        String newName="";

        boolean isRenamed=localFileUtilTest.renameFileTo(sourceFilePath,oldName,newName);


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
    public void testRenameToWithValidValues() throws IOException {
        File testRenameDirectory= new File("testDirectory");
        File testRenameFile;
        try {
            if (!testRenameDirectory.exists()) {
                testRenameDirectory.mkdir();
            }
            testRenameFile = new File(testRenameDirectory.getAbsolutePath()+"/newtext.txt");
            testRenameFile.createNewFile();
            LocalFileUtil localFileUtilTest= new LocalFileUtil();
            boolean isRenamed=localFileUtilTest.renameFileTo(testRenameDirectory.getPath(), testRenameFile.getName(),"Test3.txt");
            if(isRenamed)
                System.out.println("Renamed Successfully");
            else
                System.out.println("Rename was not successful");
            testRenameFile.delete();
            testRenameDirectory.delete();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


    }
    @Test(expected = IOException.class)
    public void testSearchDirectoryWithNullValues() throws IOException{
        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        localFileUtilTest.SearchFileAtPath(null,null);
    }

    @Test(expected = IOException.class)
    public void testSearchDirectoryWithIncorrectPath() throws IOException{
        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        localFileUtilTest.SearchFileAtPath("/test/junk",null);
    }


    @Test(expected = IOException.class)
    public void testSearchDirectoryWithIncorrectParameters() throws IOException{
        LocalFileUtil localFileUtilTest= new LocalFileUtil();
        localFileUtilTest.SearchFileAtPath("/test/junk","doesntexist");
    }

    @Test
    public void testSearchDirectoryWithNewDirectory() throws IOException{
        File testDirectory= new File("testDirectory");
        File testFile,testFile2;
        try {
            if (!testDirectory.exists()) {
                testDirectory.mkdir();

            }
            testFile = new File(testDirectory.getAbsolutePath()+"/newtext.txt");
            testFile.createNewFile();
            testFile2 = new File(testDirectory.getAbsolutePath()+"/newtext2.txt");
            testFile2.createNewFile();
            LocalFileUtil localFileUtilTest= new LocalFileUtil();
            List<LocalFile> searchResults=localFileUtilTest.SearchFileAtPath(testDirectory.getPath(), testFile.getName());
            System.out.println("Searched Results");
            for (LocalFile searchedFile:searchResults) {
                System.out.println(searchedFile.getFileName());
            }
            testFile.delete();
            testFile2.delete();
            testDirectory.delete();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void testSearchDirectoryWithoutFileName() throws IOException{
        File test1Directory= new File("testDirectory");
        File test1File,test1File2;
        try {
            if (!test1Directory.exists()) {
                test1Directory.mkdir();
            }
            test1File = new File(test1Directory.getAbsolutePath()+"/newtext.txt");
            test1File.createNewFile();
            test1File2 = new File(test1Directory.getAbsolutePath()+"/newtext2.txt");
            test1File2.createNewFile();
            LocalFileUtil localFileUtilTest= new LocalFileUtil();
            List<LocalFile> searchResults=localFileUtilTest.SearchFileAtPath(test1Directory.getPath(),"");
            System.out.println("Searched Results");
            for (LocalFile searchedFile:searchResults) {
                System.out.println(searchedFile.getFileName());
            }
            test1File2.delete();
            test1File2.delete();
            test1Directory.delete();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}