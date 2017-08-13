package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */

import edu.pdx.cs510.agile.team3.FTP.*;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

//    Core class for command-line interface. Should parse user command, interact with FTPCore, and
//    display result appropriately.
//
//  Should contain USER-INTERFACE code only - that is, processing user input and nicely displaying
//  results. All actual FTP logic should be in FTPCore.

// Uses Apache Commons CLI for command-line parsing. See https://commons.apache.org/proper/commons-cli/usage.html
public class CLIClient {

    public CLIClient() {
        this.ftpCore = new FTPCore();
        this.options = null;
        this.parser  = null;
        this.configured = false;
        configure();
    }

    // configure command-line options. must be called before attempting to parse
    // CLI input
    private void configure() {

        if(configured == true) {
            return;
        }

        options = new Options();
        parser  = new DefaultParser();

        // Command options - these specify the command to run,
        // generally corresponding to one of our features.
        // The user might specify more than one command, but only one will
        // actually run.

        Option guiCommand = new Option("gui",
                "graphical",
                false,
                "run in GUI mode");
        options.addOption(guiCommand);

        Option pingCommand = new Option("p",
                "ping",
                false,
                "connects to server and disconnects immediately");
        options.addOption(pingCommand);

        Option getCommand = new Option("g",
                "get",
                true,
                "downloads files from server to the specified path");
        getCommand.setArgs(Option.UNLIMITED_VALUES); //you can retrieve as many files as you want
        options.addOption(getCommand);

        Option putCommand = new Option("up",
                "upload",
                true,
                "uploads files from local to remote to the specified path");
        putCommand.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(putCommand);

        // List contents of directory at path
        Option listCommand = new Option("ls",
                "list",
                true,
                "print directory contents at remote path");
        listCommand.setArgs(1);
        listCommand.setArgName("PATH");
        options.addOption(listCommand);

        // End of command options

        // Options for setting up the connection. Some are required
        Option hostOption = new Option("h",
                "host",
                true,
                "FTP server hostname. This is a required option.");
        hostOption.setArgs(1);
        hostOption.setArgName("HOST");
        hostOption.setRequired(true);
        options.addOption(hostOption);

        Option usernameOption = new Option("un",
                "uname",
                true,
                "username to connect with");
        usernameOption.setArgs(1);
        usernameOption.setArgName("STRING");
        usernameOption.setRequired(true);
        options.addOption(usernameOption);

        Option passwordOption = new Option("pw",
                "pword",
                true,
                "password to connect with");
        passwordOption.setArgs(1);
        passwordOption.setArgName("STRING");
        passwordOption.setRequired(true);
        options.addOption(passwordOption);

        // The port -- will default to 21
        Option portOption = new Option("p",
                "port",
                true,
                "port to connect on - defaults to 21");
        portOption.setArgs(1);
        portOption.setArgName("PORT");
        portOption.setRequired(false);
        options.addOption(portOption);

        // Connection name - if not specified, defaults to "unnamed"
        Option nameOption = new Option("pn",
                "name",
                true,
                "connection name - defaults to 'unnamed'");
        nameOption.setArgs(1);
        nameOption.setArgName("STRING");
        nameOption.setRequired(false);
        options.addOption(nameOption);

        Option rootDirectoriesoption= new Option("ll",
                "listlocal",
                false,
                "list all the root directories of the local machine");
        rootDirectoriesoption.setRequired(false);
        options.addOption(rootDirectoriesoption);


        Option renameLocalFileOption= new Option("rlf","RenameLocal",true,"Rename Local File");
        renameLocalFileOption.setArgs(2);
        renameLocalFileOption.setArgName("STRING");
        renameLocalFileOption.setRequired(false);
        options.addOption(renameLocalFileOption);


        //Creates a new directory from the path given. Takes in one argument which is the path.
        Option newDirectoryOption = new Option("nd",
                "newDir",
                true,
                "create a new directory at the specified path");
        newDirectoryOption.setArgs(1);
        newDirectoryOption.setArgName("STRING");
        newDirectoryOption.setRequired(false);
        options.addOption(newDirectoryOption);

        //Deletes a file at the path given. Takes in one argument which is the path.
        Option deleteFileOption = new Option("d",
                "del",
                true,
                "Delete a file at the specified path.");
        deleteFileOption.setArgs(1);
        deleteFileOption.setArgName("STRING");
        deleteFileOption.setRequired(false);
        options.addOption(deleteFileOption);

        Option deleteDirectoryOption = new Option( "dd",
                "delDir",
                true,
                "Delete a directory at the specified path.");
        deleteDirectoryOption.setArgs(1);
        deleteDirectoryOption.setRequired(false);
        options.addOption(deleteDirectoryOption);


        //Renames a file at the path given. Takes in two arguments.
        //First argument is the from path, second is the to path.
        Option renameOption = new Option("rn",
                "rename",
                true,
                "rename a file from first specified path to second specified path");
        renameOption.setArgs(2);
        renameOption.setArgName("STRING");
        renameOption.setRequired(false);
        options.addOption(renameOption);



        configured = true;
    }

    // Parses CLI input into FTPServerInfo object
    private FTPServerInfo createServerInfo(CommandLine line) {
        String name = "unnamed";
        if (line.hasOption("name")) {
            name = line.getOptionValue("name");
        }

        int port = 21;
        if (line.hasOption("port")) {
            String portstring = line.getOptionValue("port");
            try {
                port = Integer.parseInt(portstring);
            }
            catch (java.lang.NumberFormatException e) {
                port = 21;
            }
        }

        FTPServerInfo serverInfo = new FTPServerInfo(
                name,
                line.getOptionValue("host"),
                line.getOptionValue("uname"),
                line.getOptionValue("pword"),
                port
        );

        return serverInfo;
    }

    // Attempts to connect to the specified servers; returns whether
    // connection attempt succeeded. On failure, prints error messages.
    // ftpCore will remain connected after this call until disconnect()
    // is called or the connection times out.
    private boolean attemptToConnect(FTPServerInfo serverInfo) {

        System.out.println("Connecting to FTP server... ");

        try {
            ftpCore.connect(serverInfo);
        } catch (ConnectionFailedException e) {
            System.out.println("Connection attempt failed -- " + e.getMessage());
            return false;
        }

        System.out.println("Connected OK!");
        return true;
    }

    // Print contents of remote directory at path
    private boolean listRemoteDirectory(FTPServerInfo serverInfo, String path) {

        try {
            ftpCore.connect(serverInfo);
        } catch (ConnectionFailedException e) {
            System.out.println("Connection attempt failed -- " + e.getMessage());
            return false;
        }

        String pathRelative = "~/" + path;

        try {
            List<RemoteFile> contents = ftpCore.getDirectoryContentsAtPath(path);
            System.out.println(pathRelative + "$");
            for (RemoteFile file : contents) {
                System.out.println("  " + file.toString());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Could not print contents of directory at path " + pathRelative + ": " + e.getMessage());
            return false;
        }
    }


    private boolean getlocalRootDirectories(){

        try{
            LocalFileUtil localFileUtil= new LocalFileUtil();
            List<LocalFile> localrootDirectories= localFileUtil.getRootList();
            System.out.format("%10s%45s", "Name", "Path"+"\n");
            for(LocalFile file: localrootDirectories){
                System.out.format("%10s%45s",file.getFileName(),file.getFilePath()+"\n");
            }
            return true;
        } catch(IOException e){
            System.out.println("Could not print the local machine root directories");
            return false;
        }




    }

    // Start the CLI client; parse user input and execute the requested command
    public void start(String[] args) {

        /*
        // TEST FTP SERVER INFO
        FTPServerInfo serverInfo = new FTPServerInfo(
                "Test server",
                "138.68.11.232",
                "agile",
                "imanagiledude",
                21
        );
*/

        // print a line to make things more readable
        System.out.println("");

        try {
            CommandLine line = parser.parse(options, args);

            // build connection info from CLI input
            FTPServerInfo serverInfo = createServerInfo(line);

            if (line.hasOption("ping")) {
                System.out.println("Attempting connection to FTP server: \n ");
                System.out.println(serverInfo.toString());
                attemptToConnect(serverInfo);
                // that's all! attemptToConnect will print the error
            }
            else if (line.hasOption("get")) {
                String[] list = line.getOptionValues("get");
                if (ftpCore.getFiles(serverInfo, list)) {
                    if (list.length == 2)
                        System.out.println("File has been downloaded successfully.");
                    else
                        System.out.println("Files have been downloaded successfully.");
                }
                else { //one or more files failed
                    //log?
                }
            }
            else if (line.hasOption("upload")) {
                String[] list = line.getOptionValues("upload");
                if (ftpCore.uploadFiles(serverInfo, list)) {
                    if (list.length == 2)
                        System.out.println("File has been uploaded successfully.");
                    else
                        System.out.println("Files have been uploaded successfully.");
                }
                else {
                    //log failed transfers?
                }
            }
            else if (line.hasOption("list")) {
                String path = line.getOptionValue("list");
                listRemoteDirectory(serverInfo, path);
            }

            else if(line.hasOption("listlocal")){

                getlocalRootDirectories();
            }

            else if (line.hasOption("newDir")) {
                String path = line.getOptionValue("newDir");
                if (ftpCore.createNewDirectory(serverInfo, path)) {
                    System.out.println("Directory was successfully created.");
                } else {
                    System.out.println("Directory creation failed.");
                }
            }
            else if (line.hasOption("del")) {
                String path = line.getOptionValue("del");
                if (ftpCore.deleteFile(serverInfo, path)) {
                    System.out.println("File was successfully deleted.");
                } else {
                    System.out.println("File deletion failed.");
                }
            }
            else if (line.hasOption("delDir")) {
                String path = line.getOptionValue("delDir");
                ftpCore.deleteDirectory(serverInfo, path);
                /*if (ftpCore.deleteDirectory(serverInfo, path)) {
                    System.out.println("Directory was successfully deleted.")
                } else {
                    System.out.println("Directory deletion failed.");
                }
                */

            }
            else if (line.hasOption("rename")) {
                String[] list = line.getOptionValues("rename");
                if (ftpCore.renameFile(serverInfo, list)) {
                    System.out.println("File was successfully renamed.");
                } else {
                    System.out.println("File renaming failed.");
                }
            }
            else {
                System.out.println("No command specified \n");
                printUsage();
            }

        } catch (ParseException e) {
            System.out.println("Invalid input -- " + e.getMessage() + "\n");
            printUsage();
        }

        if (ftpCore != null) {
            ftpCore.disconnect();
        }
    }

    // print the usage statement
    public void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ftpa", options);
    }

    public static void main(String[] args) {
        CLIClient cli = new CLIClient();
        cli.start(args);
    }

    private FTPCore ftpCore;
    private Options options;
    private CommandLineParser parser;
    boolean configured;
}
