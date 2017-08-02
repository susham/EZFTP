package edu.pdx.cs510.agile.team3;



import edu.pdx.cs510.agile.team3.FTP.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import edu.pdx.cs510.agile.team3.FTP.CLIClient;


public class Main {

    public static void main(String[] args) {
  /*  FileListViewer fileListViewer= new FileListViewer();
    fileListViewer.setVisible(true);*/

        CLIClient cliClient = new CLIClient();
        cliClient.start(args);



    }
}
