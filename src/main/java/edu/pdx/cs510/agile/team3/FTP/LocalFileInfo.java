package edu.pdx.cs510.agile.team3.FTP;

import java.io.File;

/**
 * Created by sushamkumar on 7/21/17.
 */
public class LocalFileInfo {

    public LocalFileInfo() {
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
        return IsDirectory;
    }

    public void setDirectory(boolean directory) {
        IsDirectory = directory;
    }

    private String fileName;
    private String filePath;
    private boolean IsDirectory;



}
