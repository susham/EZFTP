import edu.pdx.cs510.agile.team3.FTP.FTPCore;
import edu.pdx.cs510.agile.team3.FTP.FTPServerInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Hawkins on 8/6/2017.
 */
public class RenameFileTest {
    @Test
    public void renameFileAndBack() {
        FTPCore ftpCore = new FTPCore();
        FTPClient ftpClient = new FTPClient();
        FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
        //Log in
        try {
            ftpClient.connect(serverInfo.host, serverInfo.port);
            ftpClient.login(serverInfo.username, serverInfo.password);

            String directory = "/RenameTest";
            String before = directory + "/Before.txt";
            String after = directory + "/After.txt";
            String[] list = {before, after}; // File name: from, to

            //rename remote file
            ftpCore.renameFile(serverInfo, list);

            //check if renamed file exists on remote
            String[] nameList = ftpClient.listNames(directory);
            if (!Arrays.asList(nameList).contains(after))
                Assert.fail();

            //change file back for easy retesting
            String[] listReverse = {after, before};
            ftpCore.renameFile(serverInfo, listReverse);

            //might as well check it again
            nameList = ftpClient.listNames(directory);
            if (!Arrays.asList(nameList).contains(before))
                Assert.fail();
        } catch (IOException ex) {
            System.out.println(ex);
            Assert.fail();
        }
    }
}
