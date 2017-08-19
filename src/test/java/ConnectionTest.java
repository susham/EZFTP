// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

import org.junit.Assert;
import org.junit.Test;
import edu.pdx.cs510.agile.team3.FTP.*;

/**
 * Created by henry on 7/22/17.
 *
 * Tests whether Connect and Disconnect work.
 */
public class ConnectionTest {

    @Test
    public void testCanConnect() {
        FTPCore ftpCore = new FTPCore();
        FTPServerInfo serverInfo = TestUtil.getTestServerInfo();

        // Check that a. we can connect and b. connection does not return a null FTPConnection object
        try {
            Assert.assertNotNull("FAILED - ftpCore.connect returned null", ftpCore.connect(serverInfo));
            Assert.assertTrue("FAILED - ftpCore reports isConnected is false after successful connection",
                    ftpCore.isConnected());
            ftpCore.disconnect();
        } catch (ConnectionFailedException e) {
            Assert.assertTrue("FAILED - could not connect to test server", false);
        }

        // Check that an assertion is thrown if we give bogus host, port, username, or password
        FTPServerInfo badHost = new FTPServerInfo(
                "Test server",
                "123.45.67.890",
                "agile",
                "imanagiledude",
                21
        );
        try {
            ftpCore.connect(badHost);
            Assert.assertTrue("FAILED - connecting to server with bad host IP did not thrown ConnectionFailedException",
                    false);
            ftpCore.disconnect();
        } catch (ConnectionFailedException e) {
            Assert.assertTrue("FAILED - ftpCore reports isConnected is true after failed connection",
                    !ftpCore.isConnected());
            ftpCore.disconnect();
        }
    }

    @Test
    public void testCanConnectAndDisconnect() {
        FTPCore ftpCore = new FTPCore();
        FTPServerInfo serverInfo = TestUtil.getTestServerInfo();

        Assert.assertTrue("FAILED - FTPCore should not be connected before call to connect",
                !ftpCore.isConnected());
        try {
            ftpCore.connect(serverInfo);
        } catch (ConnectionFailedException e) {
            Assert.assertTrue("FAILED - could not connect to FTP server",
                    false);
        }

        Assert.assertTrue("FAILED - FTPCore should be connected after successful connection",
                ftpCore.isConnected());
        ftpCore.disconnect();

        Assert.assertTrue("FAILED - FTPCore should not be connected after disconnecting",
                !ftpCore.isConnected());

        try {
            ftpCore.connect(serverInfo);
        } catch (ConnectionFailedException e) {
            Assert.assertTrue("FAILED - could not connect to FTP server after previously disconnecting",
                    false);
        }
        Assert.assertTrue("FAILED - FTPCore should be connected after reconnecting after disconnect",
                ftpCore.isConnected());
    }
}
