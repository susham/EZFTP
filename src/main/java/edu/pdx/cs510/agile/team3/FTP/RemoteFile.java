// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/22/17.
 */
public class RemoteFile extends FileBase {
    public RemoteFile(String fileName, String filePath, boolean directoryFlag) {
        super(fileName, filePath, directoryFlag, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof RemoteFile)) {
            return false;
        }

        RemoteFile casted = (RemoteFile) o;

        return casted.fileName.equals(fileName)
                && casted.filePath.equals(filePath)
                && casted.remoteFlag == remoteFlag
                && casted.directoryFlag == directoryFlag;
    }
}
