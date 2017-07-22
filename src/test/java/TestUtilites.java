import edu.pdx.cs510.agile.team3.FTP.*;

/**
 * Created by henry on 7/22/17.
 *
 * Utility functions for testing
 */
public class TestUtilites {

    // Return info for the test server
    public static FTPServerInfo getTestServerInfo() {
        FTPServerInfo serverInfo = new FTPServerInfo(
                "Test server",
                "138.68.11.232",
                "agile",
                "imanagiledude",
                21
        );
        return serverInfo;
    }

}
