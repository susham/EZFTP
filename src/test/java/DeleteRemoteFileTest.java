import edu.pdx.cs510.agile.team3.FTP.FTPCore;
import edu.pdx.cs510.agile.team3.FTP.FTPServerInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KTM on 8/5/2017.
 */
public class DeleteRemoteFileTest {
  @Test
  public void deleteRemoteFileAndCheckThatItDoesNotExistAnymore() {
    FTPCore ftpCore = new FTPCore();
    FTPClient ftpClient = new FTPClient();
    FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
    //Log in
    try {
      ftpClient.connect(serverInfo.host, serverInfo.port);
      ftpClient.login(serverInfo.username, serverInfo.password);

      //First, create a file to be deleted in the server
      File upTest = new File(System.getProperty("user.dir") + File.separator + "up1.txt");
      upTest.createNewFile();

      String[] list = new String[2];
      list[0] = upTest.getPath();
      list[1] = "/upload/";
      ftpCore.uploadFiles(serverInfo, list);
      //check if file exists on remote
      String remotePath = "/upload/" + upTest.getName();
      InputStream inputStream = ftpClient.retrieveFileStream("/upload/" + upTest.getName());
      int returnCode = ftpClient.getReplyCode();
      if (returnCode == 550) // return code 550: file/directory is unavailable
        Assert.fail();
      //It exists, so now let's delete it
      ftpCore.deleteFile(serverInfo, remotePath);
      //Check if it still exists
      inputStream = ftpClient.retrieveFileStream(remotePath);
      ftpClient.completePendingCommand();
      returnCode = ftpClient.getReplyCode();
      if (returnCode != 550) // return code 550: file/directory is unavailable
        Assert.fail();
      //Delete local test file
      upTest.delete();
    } catch (IOException ex) {
      System.out.println(ex);
      Assert.fail();
    }
  }
}
