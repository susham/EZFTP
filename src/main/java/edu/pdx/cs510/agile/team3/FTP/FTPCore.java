package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */

import org.apache.commons.net.ftp.*;

import java.io.*;
import java.nio.file.Files;
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

        ftpClient.setConnectTimeout(5000);
        ftpClient.setDefaultTimeout(5000);

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

        if (!isConnected()) {
            try {
                connect(serverInfo);
            } catch (ConnectionFailedException ex) {
                System.out.println("Could not connect to ftp server -- " + ex.getMessage());
                return false;
            }
        }

        try {
            return ftpClient.makeDirectory(path);
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println(ex.getStackTrace());
            return false;
        }
    }

    //Deletes file at the path provided.  The path can be relative or absolute.
    //Returns true if the file was deleted, otherwise returns false.
    // To delete file "ex.txt" in the root directory
    // -d /ex.txt
    // To delete file "ex.txt" at /uploads/ex.txt
    // -d /uploads/ex.txt
    // *note that the "uploads" directory must exist or this will fail and return false
    public Boolean deleteFile(FTPServerInfo serverInfo, String path) {
        if (!isConnected()) {
            try {
                connect(serverInfo);
            } catch (ConnectionFailedException ex) {
                System.out.println("Could not connect to ftp server -- " + ex.getMessage());
                return false;
            }
        }

        try {
            return ftpClient.deleteFile(path);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
            return false;
        }
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

    //Renames file at first path provided to second path provided.
    //The path can be relative or absolute.
    //Returns true if the file was renamed, otherwise returns false.
    // Usage: -rn file1 file2
    // To rename file "ex.txt" to "ex2.txt" in the root directory
    // -rn /ex.txt /ex2.txt
    // To delete file "ex.txt" to "ex2.txt" at /uploads
    // -rn /uploads/ex.txt /uploads/ex2.txt
    // *note that the "uploads" directory must exist or this will fail and return false
    // *also, new name can be in a different (existing) directory
    public Boolean renameFile(FTPServerInfo serverInfo, String[] list) {

        if (!isConnected()) {
            try {
                connect(serverInfo);
            } catch (ConnectionFailedException ex) {
                System.out.println("Could not connect to FTP server: " + ex.getMessage());
                return false;
            }
        }

        if (!doesRemoteFileExistAtPath(list[0])) {
            System.out.println("Could not rename file at path " + list[0] + " -- file does not exist");
            return false;
        }

        try {
            return ftpClient.rename(list[0], list[1]);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
            return false;
        }
    }

    // Retrieves files from remote. All option arguments after -g are treated as remote file paths to be downloaded.
    // Usage: -g file1 file2 file3 ... savePath
    // Ex. retrieve the upload test files in the upload directory, and save them to your current working directory:
    // -g /upload/testUpload.txt /upload/upTest2.txt ./
    //
    // Returns true if all files were downloaded.  Returns false if any transfers failed. If an unexpected error is encountered,
    // subsequent downloads will be cancelled.
    //
    // The ftpClient must be connected before calling this method.
    public Boolean getFiles(FTPServerInfo serverInfo, String[] list) {
        if (!isConnected())
        {
            try {
                connect(serverInfo);
            } catch (ConnectionFailedException e) {
                System.out.println("Could not connected to FTP server");
                return false;
            }
        }

        int numFiles = list.length - 1;
        String localPath = list[list.length - 1]; //path to save files to (last arg)

        // Strip trailing /
        localPath = localPath.replaceAll("/$", "");

        // Check that localPath is a directory
        File localDirectory = new File(localPath);
        if (!localDirectory.exists() || !localDirectory.isDirectory()) {
            System.out.println("Could not download files to target local path: " + localPath + ": target local path must be a directory");
        }

        ftpClient.enterLocalPassiveMode();
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            System.out.println("Unexpected FTPClient error: " + e.getMessage());
            e.printStackTrace();
        }

        boolean success = true;

        System.out.println("");

        for (int i = 0; i < numFiles; ++i) {

            String remoteFilePath = list[i];
            File remoteFile = new File(remoteFilePath);
            String downloadedFilePath = localPath + "/" + remoteFile.getName();

            success &= downloadFile(remoteFilePath, downloadedFilePath);

            System.out.println("");

        }

        return success;
    }

    /**
     * Download a whole directory from a FTP server.
     * @param parentDir Path of the parent directory of the current directory being
     * downloaded.
     * @param currentDir Path of the current directory being downloaded.
     * @param saveDir path of directory where the whole remote directory will be
     * downloaded and saved.
     * @throws IOException if any network or IO error occurred.
     * Source: codejava.net, edited by Kenneth Martin
     */
    public void downloadDirectory(String parentDir, String currentDir, String saveDir) throws IOException {
        String dirToList = parentDir;
        if (!currentDir.equals("")) {
            dirToList += "/" + currentDir;
        }

        FTPFile[] subFiles = ftpClient.listFiles(dirToList);

        if (subFiles != null && subFiles.length > 0) {
            for (FTPFile aFile : subFiles) {
                String currentFileName = aFile.getName();
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    // skip parent directory and the directory itself
                    continue;
                }
                String filePath = parentDir + "/" + currentDir + "/"
                        + currentFileName;
                if (currentDir.equals("")) {
                    filePath = parentDir + "/" + currentFileName;
                }

                String newDirPath = saveDir + parentDir + File.separator
                        + currentDir + File.separator + currentFileName;
                if (currentDir.equals("")) {
                    newDirPath = saveDir + parentDir + File.separator
                            + currentFileName;
                }

                if (aFile.isDirectory()) {
                    // create the directory in saveDir
                    File newDir = new File(newDirPath);
                    boolean created = newDir.mkdirs();
                    if (created) {
                        System.out.println("CREATED the directory: " + newDirPath);
                    } else {
                        System.out.println("COULD NOT create the directory: " + newDirPath);
                    }

                    // download the sub directory
                    downloadDirectory(dirToList, currentFileName, saveDir);
                } else {
                    // download the file
                    boolean success = downloadFile(filePath, newDirPath);
                    if (success) {
                        System.out.println("DOWNLOADED the file: " + filePath);
                    } else {
                        System.out.println("COULD NOT download the file: "
                                + filePath);
                    }
                }
            }
        }
    }

    // Downloads file at remotePath to localPath. Returns true on success, false on failure.
    // ftpClient must be connected for this to work; otherwise this method does nothing and returns false.
    public boolean downloadFile(String remotePath, String localPath) {

        if (!isConnected()) {
            System.out.println("Cannot download file, ftp client is not connected");
            return false;
        }

        File downloadFile = new File(localPath);

        System.out.println("Downloading " + remotePath + " to local path: " + localPath);

        if (isRemotePathADirectory(remotePath)) {
            System.out.println("Cannot download directory " + remotePath + " -- directory downloading is not supported");
            return false;
        }

        if (!doesRemoteFileExistAtPath(remotePath)) {
            System.out.println("Cannot download file at " + remotePath + " -- file does not exist");
            return false;
        }

        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
        } catch (FileNotFoundException e) {
            System.out.println("Unexpected exception while creating local file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        try {
            if (!ftpClient.retrieveFile(remotePath, outputStream)) {
                System.err.println("Could not download file: " + remotePath);

                // clean up local file
                outputStream.close();
                if (downloadFile.exists()) {
                    downloadFile.delete();
                }
                return false;
            }
        } catch (IOException e) {
            System.out.println("Unexpected exception while retrieving file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Unexpected exception while closing output stream: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Uploads one or more files to the specified path.
    // Usage: -up file1 file2 file3 ... remotePath
    // Ex. Upload file1.txt from current directory to remote root: -up file1 /
    // Ex. Upload /upload/file1.txt to /upload: -up /upload/file1 /upload/
    // Note that the remote path must end in a forward slash, it must be provided
    // Returns true if all files were uploaded successfully. Returns false as soon as a single file failed
    public Boolean uploadFiles (FTPServerInfo serverInfo, String[] list) {

        System.out.println("");

        if (!isConnected()) {
            try {
                connect(serverInfo);
            } catch (ConnectionFailedException e) {
                System.out.println("Could not connect to FTP server");
                return false;
            }
        }

        ftpClient.enterLocalPassiveMode();
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            System.out.println("Unexpected FTPClient error: " + e.getMessage());
            e.printStackTrace();
        }

        int numFiles = list.length - 1;
        String remoteDirectoryPath = list[list.length - 1];

        // Strip trailing /
        remoteDirectoryPath = remoteDirectoryPath.replaceAll("/$", "");

        // target path must be a directory
        if (!isRemotePathADirectory(remoteDirectoryPath)) {
            System.out.println("Cannot upload files to remote path: " + remoteDirectoryPath + " -- remote path must be a directory");
            return false;
        }

        boolean success = true;

        for (int i = 0; i < numFiles; ++i) {


            File localFile = new File(list[i]);

            String localPath = list[i];
            String remotePath = remoteDirectoryPath + "/" + localFile.getName();

            success &= uploadFile(remotePath, localPath);

            System.out.println("");
        }
        return success;
    }


    // Uploads a single file from localPath to remotePath. ftpClient must be connected, or
    // this method will fail.
    public boolean uploadFile(String remotePath, String localPath) {

        System.out.println("Uploading file " + localPath + " to remote path: " + remotePath);

        File localFile = new File(localPath);

        if (!localFile.exists()) {
            System.out.println("Cannot upload file " + localPath + " -- file does not exist");
            return false;
        }

        if (localFile.isDirectory()) {
            System.out.println("Cannot upload directory " + localPath + " -- uploading directories is not supported");
            return false;
        }

        try {

            OutputStream outputStream = ftpClient.storeFileStream(remotePath);
            InputStream inputStream = new FileInputStream(localFile);

            byte[] bytesIn = new byte[4096];
            int read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }

            inputStream.close();
            outputStream.close();

            boolean completed = ftpClient.completePendingCommand();

            return completed;

        } catch (Exception e) {
            System.out.println("Could not upload file " + localPath);
            System.out.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Uploads the contents of a directory. Does not copy the topmost folder itself, just its contents. Be sure to
     * create a new directory, or choose a directory for which all of the contents will be copied to if you want the
     * contents to reside in a single folder.
     * Ex. you want to upload a directory called newDir that has a subfolder newSubDir
     * -ud / newDir
     * This will upload the contents of newDir (only the subfolder, newSubDir) to /newSubDir
     * In order to create newDir at root and have its contents copied you must first create newDir at root
     * -nd /newDir
     * then: -ud /newDir newDir
     *
     * @param remoteDirPath
     *            Path of the destination directory on the server.
     * @param localParentDir
     *            Path of the local directory being uploaded.
     * @param remoteParentDir
     *            Path of the parent directory of the current directory on the
     *            server (used by recursive calls).
     * @throws IOException
     *             if any network or IO error occurred.
     */
    //Source: codejava.net, edited by Kenneth Martin
    public void uploadDirectory(String remoteDirPath, String localParentDir, String remoteParentDir)
            throws IOException {

        System.out.println("LISTING directory: " + localParentDir);

        File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();
        if (subFiles != null && subFiles.length > 0) {
            for (File item : subFiles) {
                String remoteFilePath = remoteDirPath + "/" + remoteParentDir
                        + "/" + item.getName();
                if (remoteParentDir.equals("")) {
                    remoteFilePath = remoteDirPath + "/" + item.getName();
                }

                if (item.isFile()) {
                    // upload the file
                    String localFilePath = item.getAbsolutePath();
                    System.out.println("About to upload the file: " + localFilePath);
                    boolean uploaded = uploadFile(remoteFilePath, localFilePath);
                    if (uploaded) {
                        System.out.println("UPLOADED a file to: "
                                + remoteFilePath);
                    } else {
                        System.out.println("COULD NOT upload the file: "
                                + localFilePath);
                    }
                } else {
                    // create directory on the server
                    boolean created = ftpClient.makeDirectory(remoteFilePath);
                    if (created) {
                        System.out.println("CREATED the directory: "
                                + remoteFilePath);
                    } else {
                        System.out.println("COULD NOT create the directory: "
                                + remoteFilePath);
                    }

                    // upload the sub directory
                    String parent = remoteParentDir + "/" + item.getName();
                    if (remoteParentDir.equals("")) {
                        parent = item.getName();
                    }

                    localParentDir = item.getAbsolutePath();
                    uploadDirectory(remoteDirPath, localParentDir, parent);
                }
            }
        }
    }

    // Returns true if the remote path is a directory, otherwise returns false.
    //
    // ftpClient must be connected before calling this method (otherwise, it always returns false).
    private boolean isRemotePathADirectory(String path) {
        try {
            // Check by attempting to CWD to the target path. Yes, this is how it's done.
            String oldWD = ftpClient.printWorkingDirectory();
            boolean dirExists = ftpClient.changeWorkingDirectory(path);
            ftpClient.changeWorkingDirectory(oldWD);
            return dirExists;
        } catch (IOException e) {
            System.out.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a file exists at the remote path path.
    //
    // ftpClient must be connected before calling this method, otherwise it always returns false.
    private boolean doesRemoteFileExistAtPath(String path) {
        try {
            return (ftpClient.listFiles(path).length > 0) ? true : false;
        } catch (IOException e) {
            System.out.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private FTPConnection currentConnection;
    private FTPClient ftpClient;
}
