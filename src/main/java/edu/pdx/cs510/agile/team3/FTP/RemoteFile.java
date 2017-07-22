package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/22/17.
 */
public class RemoteFile extends FileBase {
    public RemoteFile(String fileName, String filePath, boolean directoryFlag) {
        super(fileName, filePath, directoryFlag, true);
    }

}
