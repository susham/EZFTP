package edu.pdx.cs510.agile.team3.FTP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushamkumar on 7/21/17.
 */

/*
* Local File class has the method getrootdirectoryList to retrieve the list of root directories of the local machine.
*
*
*
*
*
*
* */
public class LocalFile {

    //returns a list of root directories of the local machine along with its path and whether the file is a directory or not.
    public List<LocalFileInfo> getRootList() {

        ArrayList<LocalFileInfo> root_directorylist= new ArrayList();


        File[] listroots = File.listRoots(); //listRoots method lists all the root directories of the local machine.

        if (listroots.length > 0) {
            for (File root : listroots) {
                File[] root_directories = root.listFiles();
                if (root_directories.length > 0) {
                    for (File root_directory : root_directories) {

                        LocalFileInfo localFileInfo= new LocalFileInfo();
                        localFileInfo.setFileName(root_directory.getName());
                        localFileInfo.setFilePath(root_directory.getPath());
                        localFileInfo.setDirectory(root_directory.isDirectory());
                        root_directorylist.add(localFileInfo);
                        //System.out.println(root_directory);

                    }
                }

            }

        }

        return root_directorylist;
    }

    //returns the list of file info, which are present under the path provided.
    public List<LocalFileInfo> getFileListByPath(String filePath){

        List<LocalFileInfo> fileInfoList = new ArrayList<>();
        if(!filePath.isEmpty() && filePath != null)
        {
            File userfile= new File(filePath);
            if(userfile.exists() && userfile.isDirectory()) //check if the path provided exists and whether it's a directory or not
            {
               //if the path provided is a directory, then get the list of all files under the directory

              File[] fileList= userfile.listFiles();
                if(fileList.length >0) //check if the directory has any files under it.
                {

                    for (File file:fileList) {
                        LocalFileInfo localFileInfo= new LocalFileInfo();
                        localFileInfo.setFileName(file.getName());
                        localFileInfo.setDirectory(file.isDirectory());
                        localFileInfo.setFilePath(file.getPath());
                      fileInfoList.add(localFileInfo);


                    }


                }




            }


        }

return fileInfoList;

    }




}
