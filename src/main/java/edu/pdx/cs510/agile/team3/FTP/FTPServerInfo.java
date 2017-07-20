package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */

// Record class containing info needed to connect to an FTP server.
// This class should contain only static data; i.e., it does not track
// state of the connection / server
public class FTPServerInfo {

    public FTPServerInfo(String connectionName,
                  String host,
                  String username,
                  String password,
                  int port) {
        this.connectionName = connectionName;
        this.host = host;
        this.password = password;
        this.username = username;
        this.port = port;
    }

    public FTPServerInfo(FTPServerInfo other) {
        this.connectionName = other.connectionName;
        this.host = other.host;
        this.password = other.password;
        this.username = other.username;
        this.port = other.port;
    }

    public String toString() {
        return    "FTP Connection: \n"
                + " connectionName: " + connectionName + "\n"
                + " host: " + host + "\n"
                + " password: " + "******" + "\n"
                + " password: " + password + "\n"
                + " username: " + username + "\n"
                + " port: " + port + "\n";
    }

    // Unique name for the connection
    public String connectionName;

    // hostname or IP
    public String host;

    // Username to login with
    public String username;

    // Password to login with. So secure.
    public String password;

    // The port to connect on (FTP default is 21)
    public int port;
}
