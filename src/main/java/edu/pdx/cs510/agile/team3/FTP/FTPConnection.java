package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */

// Represents an FTP connection in-progress. This is a stateful object;
// it contains static information (e.g., server name, password) as well
// as connection state information (e.g., is a download in progress, etc).

// Currently this class doesn't do anything; it just wraps FTPServer. We may need it for
// resuming transfers and such later.
public class FTPConnection {

    public FTPConnection(FTPServerInfo serverInfo) {
        this.serverInfo = new FTPServerInfo(serverInfo);
    }

    public FTPConnection(FTPConnection other) {
        this.serverInfo = other.serverInfo;
    }

    public FTPServerInfo getServerInfo() { return serverInfo; }
    private FTPServerInfo serverInfo;
}
