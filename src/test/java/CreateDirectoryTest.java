// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

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
public class CreateDirectoryTest {
  @Test
  public void createDirectoryAndCheckIfItExists() {
      FTPCore ftpCore = new FTPCore();
      FTPClient ftpClient = new FTPClient();
      FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
      //Log in
      try {
        ftpClient.connect(serverInfo.host, serverInfo.port);
        ftpClient.login(serverInfo.username, serverInfo.password);

        //Create a new directory
        ftpCore.createNewDirectory(serverInfo, "/NewDir");

        //check if directory exists on remote
        ftpClient.changeWorkingDirectory("/NewDir");
        int returnCode = ftpClient.getReplyCode();
        if (returnCode == 550) // return code 550: file/directory is unavailable
          Assert.fail();
        //Delete directory
        ftpClient.removeDirectory("/NewDir");
      } catch (IOException ex) {
        System.out.println(ex);
        Assert.fail();
      }
    }
}
