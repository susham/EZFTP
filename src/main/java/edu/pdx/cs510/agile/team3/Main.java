package edu.pdx.cs510.agile.team3;


import edu.pdx.cs510.agile.team3.FTP.CLIClient;

public class Main {

    public static void main(String[] args) {


        //ServerConnect serverConnect= new ServerConnect();
        //serverConnect.setVisible(true);

/*
     LocalFileUtil localFileUtil = new LocalFileUtil();

     List<LocalFile> rootDirectoriesInfo = localFileUtil.getRootList();
        for (LocalFile rootDirectoryInfo:rootDirectoriesInfo){
            System.out.println("Root Directory Name:"+rootDirectoryInfo.getFileName());
            System.out.println("Root Directory Path:"+rootDirectoryInfo.getFilePath());
            System.out.println("Is a Directory:"+rootDirectoryInfo.isDirectory());
        }
*/

        CLIClient cliClient = new CLIClient();
        cliClient.start(args);
    }
}
