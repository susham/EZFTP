// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by KTM on 8/4/2017.
 */
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * This utility class implements a method that removes a non-empty directory
 * on a FTP server.  Modified to remove empty directories
 * @author www.codejava.net, modified by Kenneth Martin
 */
public class FTPUtil {

  /**
   * Removes a non-empty directory by delete all its sub files and
   * sub directories recursively. And finally remove the directory.
   */
  public static void removeDirectory(FTPClient ftpClient, String parentDir,
                                     String currentDir) throws IOException {
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

        if (aFile.isDirectory()) {
          // remove the sub directory
          removeDirectory(ftpClient, dirToList, currentFileName);
        } else {
          // delete the file
          boolean deleted = ftpClient.deleteFile(filePath);
          if (deleted) {
            System.out.println("DELETED the file: " + filePath);
          } else {
            System.out.println("CANNOT delete the file: "
                    + filePath);
          }
        }
      }

      // finally, remove the directory itself
      boolean removed = ftpClient.removeDirectory(dirToList);
      if (removed) {
        System.out.println("REMOVED the directory: " + dirToList);
      } else {
        System.out.println("CANNOT remove the directory: " + dirToList);
      }
    } else if (subFiles.length == 0) { //Needed to delete empty directory
      boolean removed = ftpClient.removeDirectory(dirToList);
      if (removed) {
        System.out.println("REMOVED the directory: " + dirToList);
      } else {
        System.out.println("CANNOT remove the directory: " + dirToList);
      }
    }
  }
}
