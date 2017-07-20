package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */
public class ConnectionFailedException extends Exception {
    public ConnectionFailedException() { super("Could not connect to FTP server"); }
    public ConnectionFailedException(String message) { super(message); }
}
