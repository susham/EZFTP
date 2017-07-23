package edu.pdx.cs510.agile.team3.FTP;

import java.io.File;

/**
 * Created by sushamkumar on 7/21/17.
 */
public class LocalFile extends FileBase {
    public LocalFile(String fileName, String filePath, boolean directoryFlag) {
        super(fileName, filePath, directoryFlag, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof LocalFile)) {
            return false;
        }

        LocalFile casted = (LocalFile) o;

        return casted.fileName.equals(fileName)
                && casted.filePath.equals(filePath)
                && casted.remoteFlag == remoteFlag
                && casted.directoryFlag == directoryFlag;
    }
}
