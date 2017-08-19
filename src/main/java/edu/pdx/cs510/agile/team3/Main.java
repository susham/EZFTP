// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

package edu.pdx.cs510.agile.team3;



import edu.pdx.cs510.agile.team3.FTP.*;
import org.apache.commons.cli.*;


public class Main {

    public static void main(String[] args) {

        Options options = new Options();
        CommandLineParser parser = new ExtendedGnuParser(true);

        Option guiCommand = new Option("gui",
                "graphical",
                false,
                "run in GUI mode");
        options.addOption(guiCommand);

        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args, false);

            if (commandLine.hasOption("gui")) {
                EZFTPGUIClient ezftpguiClient= new EZFTPGUIClient();
                ezftpguiClient.setVisible(true);
            } else {
                CLIClient cliClient = new CLIClient();
                cliClient.start(args);
            }

        } catch (ParseException e) {
            System.out.println("Could not parse command-line input: " + e.getMessage());
        }
    }
}
