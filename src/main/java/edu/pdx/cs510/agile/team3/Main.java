package edu.pdx.cs510.agile.team3;



import edu.pdx.cs510.agile.team3.FTP.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import edu.pdx.cs510.agile.team3.FTP.CLIClient;


public class Main {

    public static void main(String[] args) {


         FTPCore ftpCore= new FTPCore();

        try {
            FTPConnection connection=ftpCore.connect(new FTPServerInfo("test","138.68.11.232","agile","imanagiledude",21));
            FTPServerInfo serverInfo=connection.getServerInfo();
            FileListViewer fileListViewer= new FileListViewer(ftpCore);
            fileListViewer.setVisible(true);

        } catch (ConnectionFailedException e) {
            e.printStackTrace();
        }



//         CLIClient cliClient = new CLIClient();
//         cliClient.start(args);



    }
}
