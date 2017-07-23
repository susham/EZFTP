package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */

import edu.pdx.cs510.agile.team3.FTP.*;
import org.apache.commons.cli.*;

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
        Option pingCommand = new Option("p",
                "ping",
                false,
                "connects to server and disconnects immediately");
        options.addOption(pingCommand);

        // Another example -- not implemented yet!!
        Option getCommand = new Option("g",
                "get",
                true,
                "downloads files from server to the specified path");
        getCommand.setArgs(1);
        getCommand.setArgName("PATH");
        options.addOption(getCommand);
        // End of command options

        // Options for setting up the connection. Some are required
        Option hostOption = new Option("h",
                "host",
                true,
                "ftp server host");
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
            port = Integer.parseInt(portstring);
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
                System.out.println("ERROR -- GET NOT IMPLEMENTED YET!!");
            }
            else {
                System.out.println("No command specified \n");
                printUsage();
            }

        } catch (ParseException e) {
            System.out.println("Invalid input -- " + e.getMessage() + "\n");
            printUsage();
        }

        ftpCore.disconnect();
    }

    // print the usage statement
    public void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ftpa", options);
    }


    private FTPCore ftpCore;
    private Options options;
    private CommandLineParser parser;
    boolean configured;
}
