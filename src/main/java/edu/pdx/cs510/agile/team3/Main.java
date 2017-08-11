package edu.pdx.cs510.agile.team3;



import edu.pdx.cs510.agile.team3.FTP.*;


public class Main {

    public static void main(String[] args) {


         FTPCore ftpCore= new FTPCore();

        try {
           FTPConnection connection=ftpCore.connect(new FTPServerInfo("test","138.68.11.232","agile","imanagiledude",21));
          FTPServerInfo serverInfo=connection.getServerInfo();
            EZFTPGUIClient ezftpguiClient= new EZFTPGUIClient();
            ezftpguiClient.setVisible(true);

        } catch (ConnectionFailedException e) {
            e.printStackTrace();
        }



//         CLIClient cliClient = new CLIClient();
//         cliClient.start(args);



    }
}
