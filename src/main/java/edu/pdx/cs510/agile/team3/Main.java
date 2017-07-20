package edu.pdx.cs510.agile.team3;


import edu.pdx.cs510.agile.team3.FTP.CLIClient;
import edu.pdx.cs510.agile.team3.FTP.ServerConnect;

public class Main {

    public static void main(String[] args) {


        //ServerConnect serverConnect= new ServerConnect();
        //serverConnect.setVisible(true);
        CLIClient cliClient= new CLIClient();
        cliClient.start(args);
    }
}
