
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
        FTPServerInfo serverInfo = TestUtilites.getTestServerInfo();

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




        /*
        // Check that an assertion is thrown if we give bogus host, port, username, or password
        FTPServerInfo unparseableHost = new FTPServerInfo(
                "Test server",
                "asdlajkj$%^&",
                "agile",
                "imanagiledude",
                21
        );
        try {
            ftpCore.connect(unparseableHost);
            Assert.assertTrue("FAILED - connecting to host with invalid host format did not throw ConnectionFailedException", false);
        } catch (ConnectionFailedException e) {
            // OK -- an exception should be thrown
        }

        // These tests commented out because they are slow to run (and very unlikely to fail)

        FTPServerInfo nullHost = new FTPServerInfo(
                "Test server",
                "",
                "agile",
                "imanagiledude",
                21
        );
        try {
            ftpCore.connect(nullHost);
            Assert.assertTrue("FAILED - connecting to host with null hostname did not throw ConnectionFailedException", false);
        } catch (ConnectionFailedException e) {
            // OK -- an exception should be thrown
        }

        FTPServerInfo badUsername = new FTPServerInfo(
                "Test server",
                "138.68.11.232",
                "BAD",
                "imanagiledude",
                21
        );
        try {
            ftpCore.connect(badUsername);
            Assert.assertTrue("FAILED - connecting to server with bad username did not throw ConnectionFailedException", false);
        } catch (ConnectionFailedException e) {
            // OK -- an exception should be thrown
        }

        FTPServerInfo badPassword = new FTPServerInfo(
                "Test server",
                "138.68.11.232",
                "agile",
                "BAD",
                21
        );
        try {
            ftpCore.connect(badPassword);
            Assert.assertTrue("FAILED - connecting to server with bad password did not throw ConnectionFailedException", false);
        } catch (ConnectionFailedException e) {
            // OK -- an exception should be thrown
        }

        FTPServerInfo badPort = new FTPServerInfo(
                "Test server",
                "138.68.11.232",
                "agile",
                "imanagiledude",
                22
        );
        try {
            ftpCore.connect(badPort);
            Assert.assertTrue("FAILED - connecting to server with bad port did not throw ConnectionFailedException", false);
        } catch (ConnectionFailedException e) {
            // OK -- an exception should be thrown
        }
        */
    }

    @Test
    public void testCanConnectAndDisconnect() {
        FTPCore ftpCore = new FTPCore();
        FTPServerInfo serverInfo = TestUtilites.getTestServerInfo();

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
