// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

import edu.pdx.cs510.agile.team3.FTP.FTPCore;
import edu.pdx.cs510.agile.team3.FTP.FTPServerInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by KTM on 8/5/2017.
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
      ftpClient.changeWorkingDirectory("/NewDir");
      returnCode = ftpClient.getReplyCode();
      if (returnCode != 550)
        Assert.fail();
    } catch (IOException ex) {
      System.out.println(ex);
      Assert.fail();
    }
  }
}
