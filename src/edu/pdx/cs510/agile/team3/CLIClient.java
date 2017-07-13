package edu.pdx.cs510.agile.team3;

/**
 * Created by henry on 7/12/17.
 */

import edu.pdx.cs510.agile.team3.FTP.*;

//    Core class for command-line interface. Should parse user command, interact with FTPCore, and
//    display result appropriately.
//
//  Should contain USER-INTERFACE code only - that is, processing user input and nicely displaying
//  results. All actual FTP logic should be in FTPCore.
public class CLIClient {

    public CLIClient() { }

    // Start the CLI client; parse user input and execute the requested command
    public void start() {
        /*
        ftp server info: use me for tests!
         */
        FTPServerInfo serverInfo = new FTPServerInfo(
                "Test server",
                "138.68.11.232",
                "agile",
                "imanagiledude",
                21
        );

        System.out.println("Connecting to test server:");
        System.out.println(serverInfo.toString());

        try {
            FTPConnection connection = ftpCore.connect(serverInfo);
        } catch (ConnectionFailedException e) {
            e.printStackTrace();
        }
    }

    private FTPCore ftpCore = new FTPCore();
}
