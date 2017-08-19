// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */
public class ConnectionFailedException extends Exception {
    public ConnectionFailedException() { super("Could not connect to FTP server"); }
    public ConnectionFailedException(String message) { super(message); }
}
