import edu.pdx.cs510.agile.team3.FTP.*;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.io.*;

/**
 * Created by KTM on 8/5/2017.
 */
public class PutFileTest {

  @Test
  public void uploadFileToRemoteAndCheckIfItExists() {
    FTPCore ftpCore = new FTPCore();
    FTPClient ftpClient = new FTPClient();
    FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
    //Log in
    try {
      ftpClient.connect(serverInfo.host, serverInfo.port);
      ftpClient.login(serverInfo.username, serverInfo.password);

      //First, create a file in current directory to upload to the server
      File upTest = new File(System.getProperty("user.dir") + File.separator + "up1.txt");
      upTest.createNewFile();

      String[] list = new String[2];
      list[0] = upTest.getPath();
      list[1] = "/upload/";
      ftpCore.uploadFiles(serverInfo, list);
      //check if file exists on remote
      InputStream inputStream = ftpClient.retrieveFileStream("/upload/" + upTest.getName());
      int returnCode = ftpClient.getReplyCode();
      if (returnCode == 550) // return code 550: file/directory is unavailable
      Assert.fail();
      //Delete local test file and remote file
      ftpClient.deleteFile("/upload/" + upTest.getName());
      upTest.delete();
    } catch (IOException ex) {
      System.out.println(ex);
      Assert.fail();
    }
  }

  /*The Apache Commons FTPClient seems to have an issue with hanging indefinitely when attempting to do several
  consecutive ftp commands.  I have looked into this and tried some suggestions on StackOverflow
  (https://stackoverflow.com/questions/9706968/apache-commons-ftpclient-hanging)to no avail.
  Thus, if you run this test but it never seems to finish, you'll just have to run the test manually by running
  the CLIClient with the args: -h 138.68.11.232 -un agile -pw imanagiledude -up ./up1.txt ./up2.txt ./up3.txt /upload/
  and verifying with FileZilla that the files were uploaded successfully.
  */
  @Test
  public void uploadSeveralFilesAndCheckExistenceOnRemote() {
    FTPCore ftpCore = new FTPCore();
    FTPClient ftpClient = new FTPClient();
    FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
    //Log in
    try {
      ftpClient.connect(serverInfo.host, serverInfo.port);
      ftpClient.login(serverInfo.username, serverInfo.password);

      //First, create a file in current directory to upload to the server
      File upTest = new File(System.getProperty("user.dir") + File.separator + "up1.txt");
      File upTest2 = new File(System.getProperty("user.dir") + File.separator + "up2.txt");
      File upTest3 = new File(System.getProperty("user.dir") + File.separator + "up3.txt");
      upTest.createNewFile();
      upTest2.createNewFile();
      upTest3.createNewFile();

      String[] list = new String[4];
      list[0] = upTest.getPath();
      list[1] = upTest2.getPath();
      list[2] = upTest3.getPath();
      list[3] = "/upload/";
      ftpCore.uploadFiles(serverInfo, list);
      //check if files exist on remote
      String name;
      int returnCode = 0;
      InputStream inputStream;
      for (int i = 0; i < 3; ++i) {
        switch (i) {
          case 0: name = upTest.getName();
          break;
          case 1: name = upTest2.getName();
          break;
          case 2: name = upTest3.getName();
          break;
          default: name = "fail";
          Assert.fail();
        }
        inputStream = ftpClient.retrieveFileStream("/upload/" + name);
        returnCode = ftpClient.getReplyCode();
        if (returnCode == 550) // return code 550: file/directory is unavailable
          Assert.fail();
      }
      //Delete local files and remote files
      ftpClient.deleteFile("/upload/" + upTest.getName());
      ftpClient.deleteFile("/upload/" + upTest2.getName());
      ftpClient.deleteFile("/upload/" + upTest3.getName());
      upTest.delete();
      upTest2.delete();
      upTest3.delete();
    } catch (IOException ex) {
      System.out.println(ex);
      Assert.fail();
    }
  }
}
