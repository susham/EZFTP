// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

import edu.pdx.cs510.agile.team3.FTP.*;
import org.junit.Assert;


/**
 * Created by henry on 7/22/17.
 *
 * Utility functions for testing
 */
public class TestUtil {

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

    // Connect to the FTP server; fail test if connection fails
    public static FTPConnection connect(FTPServerInfo serverInfo, FTPCore ftpCore) {
        try {
            return ftpCore.connect(serverInfo);
        } catch (ConnectionFailedException e) {
            Assert.assertTrue("FAILED - could not connect to FTP server",
                    false);
        }
        return null;
    }
}
