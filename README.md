## Project Description

Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim

This is a simple FTP client written in Java. Currently, it provides a command line interface for common FTP tasks. A rudimentary GUI is also provided.

This program is licensed under the "MIT License". Please see the file COPYING in the source distribution of this software for license terms.

## Contributor Information
(CS561) Kenneth Martin, Github: glass2/risingAsh, email: kenneth6@pdx.edu

(CS561) Susham Yerabolu, Github: susham, email: yerabolu@pdx.edu

Henry Cooney, Github: hacoo, email: hcooney@pdx.edu

Dakota Sanchez, Github: dakotasanchez, email: daks2@pdx.edu

Chris Kim, Github: sayunkim, email: kimchris@pdx.edu

Matthew Hawkins, Github: hawkins00, email: mhawkins@pdx.edu


## Status

   Most command line options are currently working and tested. The GUI is mostly implemented, but there are some options that have not been implemented yet.  Note that the GUI has not been thorougly tested and may be buggy.

## Requirements

   This project has been tested under Ubuntu Linux only. To run it, you will need Java 7 or later. To install Java, use the following steps:

   > sudo apt-get update
   > sudo apt-get install default-jre
   > sudo apt-get install openjdk-8-jdk

   Building this project requires Gradle, available at https://gradle.org


## Pre-built Install

   A pre-built executable is provided with this repository at /build/distributions/EZFTP.jar. To run it, use the following steps:

   > cd build/distributions/
   > tar -xvf cs510-agile-team3.tar
   > cd cs510-agile-team3/bin
   > ./cs510-agile-team3 [options]

   So, the pre-build executable is located in the /bin directory of cs510-agile-team3.tar.

## Building this project

   This project may be built using Gradle. Simply cd to the root of the repo and use:

   > ./gradlew build

   This will build the project and execute all tests. Note that some tests will fail, as this project is under development.

## Using the command-line interface

   The command-line interface accepts two types of options, 'connection parameters' and 'commands'. Connection parameters include things like the server hostname, username, password, and the port; to run the client, you must provide at least the hostname, username, and password. The 'command' is the action to execute (e.g., 'get' a file from a path). If multiple commands are given, only one will be executed. For example, the following command would connect to the host at IP 123.45.67.800, on port 23, and download a file to the current directory:

   > ./cs510-agile-team3 -h 123.45.67.800 -un user -pw pword -p 23 -g /path/to/file .

   The following connection parameters are mandatory:
   
   - -h -- the host name or IP (e.g., -h 123.45.56.800)
   - -un -- the user name to connect with
   - -pw -- the password to connect with

   The following connection parameters are optional:
   - -p -- the port to connect on (defaults to 21)

   The following commands are supported:
   
   - -d -- deletes a file at a path
   - -g -- downloads one or more files to the specified path. The last argument is the target path, preceeding arguments are the files to download
   - -ls -- lists files at the remote path
   - -nd -- creates a new directory on the FTP server at path
   - -rn -- rename (move) file on the FTP server from the first argument to the second one
   - -up -- upload file(s) to the specified remote path. The last argument is the remote path to upload to, preceeding arguments are the files to upload.

## CLI Examples

   The following command downloads two files from the remote path working/ to the current directory:

   > ./cs510-agile-team3 -h 123.45.67.800 -un user -pw pword -g /working/f1.txt /working/f2.txt .

   This command uploads two files from the current directory to working/:

   > ./cs510-agile-team3 -h 123.45.67.800 -un user -pw pword -up f1.txt f2.txt working/

   This command moves the remote file f1.txt to the remote directory working/f2.txt:

   > ./cs510-agile-team3 -h 123.45.67.800 -un user -pw pword -rn f1.txt working/f2.txt


## Using the GUI

   To run in gui mode use:

   > ./EZFTP.jar -gui

   You do not need to provide connection parameters to run in gui mode.
   
   After entering this command, a new window should pop up with textboxes for the Username, Password, Server Address, Port, and Name. To the right of these textboxes is the "Quick Connect" button, and the "Save Connection" button.  To start the ftp client, enter in the relevant information of the server into the textboxes and click Quick Connect (Name field is not necessary).  Note that the Save Connection button was meant to save this login information and map it to the name given in the Name textbox for quick validation on subsequent uses, but the functionality has not yet been implemented so this button currently does nothing.
   
   Upon successful connection, you should see a success message, followed by the folder tree of your server in the right-hand pane.  You should also see the folder tree of your local machine on the left-hand pane.  Note that if the left-hand pane shows directory structure starting at "JTree", that means there was an error retrieving your folder hierarchy (this happens when using Windows, which is not supported yet).

   You can view what ftp options are availabe by right-clicking a file or folder in either of the panes.  Right-clicking in the remote pane (right) will show you the options to Create Directory, Create File, Delete, Download, and Refresh.  These options are all implemented and can be used, and are straightforward to use.  Right-clicking in the left pane shows you the options of Upload, Rename, and Delete.  Currently, only the Upload option has been implemented.

   Note that after making any changes to the server, you need to use the Refresh option to view the changes.

