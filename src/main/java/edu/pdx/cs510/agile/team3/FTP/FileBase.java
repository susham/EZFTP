package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/22/17.
 */

/*
* Base class for remote or local files
*/
public abstract class FileBase {

    private FileBase() {
    }

    public FileBase(String fileName,
                    String filePath,
                    boolean directoryFlag,
                    boolean remoteFlag) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.directoryFlag = directoryFlag;
        this.remoteFlag = remoteFlag;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isDirectory() {
        return directoryFlag;
    }

    public void setDirectory(boolean directoryFlag) {
        this.directoryFlag = directoryFlag;
    }

    public boolean isRemote() {
        return remoteFlag;
    }

    // Get a pretty string representation
    public String toString() {
        String displayStr;
        if (directoryFlag) {
            displayStr = "D " + fileName;
        } else {
            displayStr = "F " + fileName;
        }
        return displayStr;
    }

    @Override
    public boolean equals(Object o) {
        // Can't compare abstract base class
        assert(false);
        return false;
    }

    protected String fileName;
    protected String filePath;
    protected boolean directoryFlag;
    protected boolean remoteFlag;
}
