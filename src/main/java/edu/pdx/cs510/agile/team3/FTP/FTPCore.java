package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */

import org.apache.commons.net.ftp.*;

import java.io.*;
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
    // -nd /newDirectory
    // To create a new directory called "newDirectory" in the working directory
    // -nd newDirectory
    // To create a new directory called "newDirectory" at /uploads/newDirectory
    // -nd /uploads/newDirectory
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

    //Deletes file at the path provided.  The path can be relative or absolute.
    //Returns true if the file was deleted, otherwise returns false.
    // To delete file "ex.txt" in the root directory
    // -d /ex.txt
    // To delete file "ex.txt" at /uploads/ex.txt
    // -d /uploads/ex.txt
    // *note that the "uploads" directory must exist or this will fail and return false
    public Boolean deleteFile(FTPServerInfo serverInfo, String path) {
        try {
            connect(serverInfo);
        } catch (ConnectionFailedException ex) {
            System.out.println(ex);
            System.exit(1);
        }
        try {
            return ftpClient.deleteFile(path);
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        }

        //shouldn't ever reach here, just for making compiler happy.
        System.out.println("File deletion encountered an unexpected error.");
        return false;
    }

    public void deleteDirectory(FTPServerInfo serverInfo, String path) {
        try {
            connect(serverInfo);
        } catch (ConnectionFailedException ex) {
            System.out.println(ex);
            System.exit(1);
        }
        try {
            FTPUtil.removeDirectory(ftpClient, path, "");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Retrieves files from remote. All option arguments after -g are treated as remote file paths to be downloaded.
    // Usage: -g file1 file2 file3 ... savePath
    // Ex. retrieve the upload test files in the upload directory, and save them to your current working directory:
    // -g /upload/testUpload.txt /upload/upTest2.txt ./
    // Returns true if all files were downloaded.  Returns false as soon as a single file failed.
    public Boolean getFiles(FTPServerInfo serverInfo, String[] list) { //list is the list of args after the -g option
        FTPClient ftpClient = new FTPClient();
        try {
            //Log in
            ftpClient.connect(serverInfo.host, serverInfo.port);
            ftpClient.login(serverInfo.username, serverInfo.password);

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            int files = list.length - 1; //number of files to download

            String localPath = list[list.length - 1]; //path to save files to (last arg)
            String remoteFileName;
            File remoteFile; //This is just needed to extracting the file name from the path, otherwise not necessary
            File downloadFile;
            OutputStream outputStream;
            for (int i = 0; i < files; ++i) {
                remoteFileName = list[i];
                remoteFile = new File(remoteFileName);
                //Old method: save files to current working directory
                //Save the file to "./remoteFileName"
                //downloadFile = new File(System.getProperty("user.dir") + File.separator + remoteFile.getName());
                //Use this instead:
                downloadFile = new File(localPath + remoteFile.getName());
                System.out.println("Retrieving " + downloadFile); //Prints the full path of the downloaded file
                outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                boolean success = ftpClient.retrieveFile(remoteFileName, outputStream);
                if (!success) { //if a file failed to transfer, error out
                    System.err.println("Retrieval of a file failed.");
                    return false;
                }
                outputStream.close();
            }
            return true;
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    // Uploads one or more files to the specified path.
    // Usage: -up file1 file2 file3 ... remotePath
    // Ex. Upload file1.txt from current directory to remote root: -up file1 /
    // Ex. Upload /upload/file1.txt to /upload: -up /upload/file1 /upload/
    // Note that the remote path must end in a forward slash, it must be provided
    // Returns true if all files were uploaded successfully. Returns false as soon as a single file failed
    public Boolean uploadFiles (FTPServerInfo serverInfo, String[] list) {
        FTPClient ftpClient = new FTPClient();
        try {
            //Log in
            ftpClient.connect(serverInfo.host, serverInfo.port);
            ftpClient.login(serverInfo.username, serverInfo.password);

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            int files = list.length - 1; //number of files to download
            File localFile = null; //the file to be uploaded
            String remotePath = list[list.length - 1]; //the path for the uploaded file(s) on the server (last arg)
            InputStream inputStream = null;

            for (int i = 0; i < files; ++i) {
                localFile = new File(list[i]);
                inputStream = new FileInputStream(localFile);

                System.out.println("Start uploading file");
                OutputStream outputStream = ftpClient.storeFileStream(remotePath + localFile.getName());
                byte[] bytesIn = new byte[4096];
                int read = 0;

                while ((read = inputStream.read(bytesIn)) != -1) {
                    outputStream.write(bytesIn, 0, read);
                }
                inputStream.close();
                outputStream.close();

                boolean completed = ftpClient.completePendingCommand();
                if (completed) {
                    System.out.println("File, " + localFile.getName() + ", uploaded successfully.");
                } else {
                    System.out.println("File, " + localFile.getName() + "failed to upload.");
                    return false;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    private FTPConnection currentConnection;
    private FTPClient ftpClient;
}
