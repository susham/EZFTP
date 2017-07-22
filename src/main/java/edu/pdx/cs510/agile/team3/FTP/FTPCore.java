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
        isConnected = false;
        currentConnection = null;
        ftpClient = new FTPClient();
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
        isConnected = false;
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
        isConnected = true;
        currentConnection = ftpConnection;
        return ftpConnection;
    }

    // returns directory contents at specified path.
    public List<RemoteFile> getDirectoryContentsAtPath(String path) throws FTPConnectionClosedException, IOException {
        if (!isConnected) {
            throw new FTPConnectionClosedException("Not connected to an FTP server");
        }

        FTPFile[] ftpContents = ftpClient.listFiles(path);

        // Find the parent directory
        String oldWorkingDirectory = ftpClient.printWorkingDirectory();
        ftpClient.changeWorkingDirectory(path);
        ftpClient.changeToParentDirectory();
        String parentDirectory = ftpClient.printWorkingDirectory();
        ftpClient.changeWorkingDirectory(oldWorkingDirectory);

        // Convert FTPFile contents into RemoteFiles
        List<RemoteFile> contents = new Vector<RemoteFile>(ftpContents.length);
        for (FTPFile file : ftpContents) {
            contents.add(new RemoteFile(file.getName(),
                    parentDirectory,
                    file.isDirectory()));
        }

        return contents;
    }


    private boolean isConnected;
    private FTPConnection currentConnection;
    private FTPClient ftpClient;
}
