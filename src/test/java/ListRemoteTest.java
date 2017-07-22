
import org.junit.Test;
import edu.pdx.cs510.agile.team3.FTP.*;


/**
 * Created by henry on 7/22/17.
 *
 * Tests listing remote files
 */
public class ListRemoteTest {
    @Test
    public void testGetsCorrectFilesAtHomeDirectory() {
        FTPCore ftpCore = new FTPCore();
        FTPServerInfo serverInfo = TestUtil.getTestServerInfo();
        FTPConnection connection = TestUtil.connect(serverInfo, ftpCore);




    }


}
