import edu.pdx.cs510.agile.team3.FTP.FTPCore;
import edu.pdx.cs510.agile.team3.FTP.FTPServerInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by KTM on 8/5/2017.
 */
/*The Apache Commons FTPClient seems to have an issue with hanging indefinitely when attempting to do several
  consecutive ftp commands.  I have looked into this and tried some suggestions on StackOverflow
  (https://stackoverflow.com/questions/9706968/apache-commons-ftpclient-hanging) to no avail.
  Thus, if you run this test but it never seems to finish, you'll just have to run the test manually by running
  the CLIClient with the args: -h 138.68.11.232 -un agile -pw imanagiledude -dd "/NewDir"
  and verifying with FileZilla that NewDir was actually deleted.
 */
public class DeleteDirectoryTest {
  @Test
  public void deleteEmptyRemoteDirectory() {
    FTPCore ftpCore = new FTPCore();
    FTPClient ftpClient = new FTPClient();
    FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
    ftpClient.enterLocalPassiveMode();
    ftpClient.setBufferSize(1024 * 1024);
    //Log in
    try {
      ftpClient.connect(serverInfo.host, serverInfo.port);
      ftpClient.login(serverInfo.username, serverInfo.password);

      //Create a new directory on the server
      ftpCore.createNewDirectory(serverInfo, "/NewDir");

      //check if directory exists on remote
      ftpClient.changeWorkingDirectory("/NewDir");
      int returnCode = ftpClient.getReplyCode();
      if (returnCode == 550) // return code 550: file/directory is unavailable
        Assert.fail();

      //Now, delete the directory and check if it still exists
      ftpCore.deleteDirectory(serverInfo, "/NewDir");
      // ftpClient.completePendingCommand();
      ftpClient.changeWorkingDirectory("/NewDir");
      // ftpClient.completePendingCommand();
      returnCode = ftpClient.getReplyCode();
      if (returnCode != 550)
        Assert.fail();
    } catch (IOException ex) {
      System.out.println(ex);
      Assert.fail();
    }
  }
}
