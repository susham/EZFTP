package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */

import org.apache.commons.net.ftp.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

// Core FTP class.
// Contains logic for interacting with java FTPClient -- all main FTP functionality lives here!
// All FTP functions should return generic results that can displayed either by the CLI app or the GUI.
//
// May contain (possibly many!) other classes -- e.g., log functionality should be in it's own Logger class, etc.
public class FTPCore {

    public FTPCore() {
        currentConnection = null;
        ftpClient = new FTPClient();
    }

    public boolean isConnected() {
        return ftpClient.isConnected();
    }

    public void disconnect() {
        try {
            ftpClient.logout();
        } catch (IOException e) {
            // Don't care about this error!
        }
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            // Don't care about this error!
        }
        currentConnection = null;
    }

    // Connects ftpClient to the server described by serverInfo.
    // If ftpClient is already connected, the current connection is disconnected.
    public FTPConnection connect(FTPServerInfo serverInfo) throws ConnectionFailedException {

        disconnect();

        try {
            ftpClient.connect(serverInfo.host, serverInfo.port);
        } catch (IOException e) {
            throw new ConnectionFailedException("Could not connect to FTP server at host: "
                    + serverInfo.host + " on port: " + serverInfo.port);
        }

        try {
            if (!ftpClient.login(serverInfo.username, serverInfo.password)) {
                throw new ConnectionFailedException("Invalid Username or Password");
            }
        } catch (IOException e) {
            throw new ConnectionFailedException(e.getMessage());
        }

        FTPConnection ftpConnection = new FTPConnection(serverInfo);
        currentConnection = ftpConnection;
        return ftpConnection;
    }

    // returns directory contents at specified path.
    public List<RemoteFile> getDirectoryContentsAtPath(String path) throws FTPConnectionClosedException, IOException {
        if (!isConnected()) {
            throw new FTPConnectionClosedException("Not connected to an FTP server");
        }

        FTPFile[] ftpContents = ftpClient.listFiles(path);

        // Find the parent directory
        String oldWorkingDirectory = ftpClient.printWorkingDirectory();
        if (!ftpClient.changeWorkingDirectory(path)) {
            throw new IOException("No such file or directory");
        }

        String currentDirectory = ftpClient.printWorkingDirectory();

        // Convert FTPFile contents into RemoteFiles
        List<RemoteFile> contents = new Vector<RemoteFile>(ftpContents.length);
        for (FTPFile file : ftpContents) {
            contents.add(new RemoteFile(file.getName(),
                    currentDirectory,
                    file.isDirectory()));
        }

        // Change back to original WD
        ftpClient.changeWorkingDirectory(oldWorkingDirectory);
        return contents;
    }

    //Creates a new directory at the path provided.  The path can be relative or absolute.
    //Returns true if the directory was created, otherwise returns false.
    // To create a new directory called "newDirectory" at the root folder
    // -nd "/newDirectory"
    // To create a new directory called "newDirectory" in the working directory
    // -nd "newDirectory"
    // To create a new directory called "newDirectory" at /uploads/newDirectory
    // -nd "/uploads/newDirectory"
    // *note that the "uploads" directory must exist or this will fail and return false
    public Boolean createNewDirectory(FTPServerInfo serverInfo, String path) {
        try {
            connect(serverInfo);
        } catch (ConnectionFailedException ex) {
            System.out.println(ex);
            System.exit(1);
        }
        try {
            return ftpClient.makeDirectory(path);
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        }

        //shouldn't ever reach here, just for making compiler happy.
        System.out.println("Directory creation encountered an unexpected error.");
        return false;
    }

    private FTPConnection currentConnection;
    private FTPClient ftpClient;
}
