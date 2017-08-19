// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

import edu.pdx.cs510.agile.team3.FTP.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.nio.file.Files;

/**
 * Created by KTM on 8/5/2017.
 */
public class GetFileTest {
  @Test
  public void retrieveFileFromRemoteAndCheckItExistsOnLocal() {
    FTPCore ftpCore = new FTPCore();
    FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
    FTPConnection connection = TestUtil.connect(serverInfo, ftpCore);

    String fileToGet = "/GetTest/aFile"; //we will save this file from remote to our current directory
    String saveDir = "./"; //save to current directory
    File downloadFile = new File(System.getProperty("user.dir") + File.separator + "aFile"); // ./aFile
    String[] list = new String[2];
    list[0] = fileToGet;
    list[1] = saveDir;
    ftpCore.getFiles(serverInfo, list);
    Boolean exists = downloadFile.exists();
    //Delete test file as we have saved the result in "exists"
    downloadFile.delete();
    Assert.assertTrue(exists); //check if the file "aFile" exists in your working directory
  }

  @Test
  public void retrieveSeveralFilesAndCheckExistenceOnLocal() {
    FTPCore ftpCore = new FTPCore();
    FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
    FTPConnection connection = TestUtil.connect(serverInfo, ftpCore);

    String file1 = "/GetTest/file1";
    String file2 = "/GetTest/New directory/file2";
    String file3 = "/GetTest/file3";
    String saveDir = "./"; //save to current directory

    File downloadFile1 = new File(System.getProperty("user.dir") + File.separator + "file1"); // ./file1
    File downloadFile2 = new File(System.getProperty("user.dir") + File.separator + "file2"); // ./file2
    File downloadFile3 = new File(System.getProperty("user.dir") + File.separator + "file3"); // ./file3

    String[] list = new String[4];
    list[0] = file1;
    list[1] = file2;
    list[2] = file3;
    list[3] = saveDir;
    ftpCore.getFiles(serverInfo, list);
    //Check that all three files downloaded correctly
    Boolean verify = downloadFile1.exists() && downloadFile2.exists() && downloadFile3.exists();
    //Delete the test files as we already have the results
    downloadFile1.delete();
    downloadFile2.delete();
    downloadFile3.delete();
    Assert.assertTrue(verify);
  }
}
