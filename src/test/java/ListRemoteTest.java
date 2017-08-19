// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

import org.junit.Test;
import org.junit.Assert;
import edu.pdx.cs510.agile.team3.FTP.*;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Created by henry on 7/22/17.
 *
 * Tests listing remote files
 */
public class ListRemoteTest {

    @Test
    public void testGetsCorrectFilesAtDirectory() throws IOException {
        FTPCore ftpCore = new FTPCore();
        FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
        FTPConnection connection = TestUtil.connect(serverInfo, ftpCore);

        String path = "ListRemoteTest/";

        List<RemoteFile> contents = ftpCore.getDirectoryContentsAtPath(path);
        List<RemoteFile> expectedContents = new Vector<RemoteFile>();

        expectedContents.add(new RemoteFile(
                "another",
                "/ListRemoteTest",
                true
        ));

        expectedContents.add(new RemoteFile(
                "dir",
                "/ListRemoteTest",
                true
        ));

        expectedContents.add(new RemoteFile(
                "file1.txt",
                "/ListRemoteTest",
                false
        ));

        Assert.assertTrue("FAILED - /ListRemoteTest did not appear to contain the correct contents",
                contents.containsAll(expectedContents));
    }

    @Test
    public void testGetsCorrectFilesAtNestedDirectory() throws IOException {
        FTPCore ftpCore = new FTPCore();
        FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
        FTPConnection connection = TestUtil.connect(serverInfo, ftpCore);

        String path = "ListRemoteTest/dir/nested";

        List<RemoteFile> contents = ftpCore.getDirectoryContentsAtPath(path);
        List<RemoteFile> expectedContents = new Vector<RemoteFile>();

        expectedContents.add(new RemoteFile(
                "nestedfile.txt",
                "/ListRemoteTest/dir/nested",
                false
        ));

        Assert.assertTrue("FAILED - /ListRemoteTest/dir/nested did not appear to contain the correct contents",
                contents.containsAll(expectedContents));
    }

    @Test
    public void testGetsCorrectFilesAtEmptyDirectory() throws IOException {
        FTPCore ftpCore = new FTPCore();
        FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
        FTPConnection connection = TestUtil.connect(serverInfo, ftpCore);

        String path = "ListRemoteTest/empty";

        List<RemoteFile> contents = ftpCore.getDirectoryContentsAtPath(path);

        Assert.assertTrue("FAILED - /ListRemoteTest/empty did not appear to be empty",
                contents.size() == 0);
    }
}
