package edu.pdx.cs510.agile.team3.FTP;

import java.io.File;

/**
 * Created by sushamkumar on 7/21/17.
 */
public class LocalFileInfo extends FileBase {
    public LocalFileInfo(String fileName, String filePath, boolean directoryFlag) {
        super(fileName, filePath, directoryFlag, false);
    }
}
