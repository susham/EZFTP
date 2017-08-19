// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

package edu.pdx.cs510.agile.team3.FTP;
import org.apache.commons.cli.*;

import java.util.ListIterator;

/**
 * Created by henry on 8/13/17.
 */

/*
  Extended parser that does not fail if unrecognized options are found.

  See: https://stackoverflow.com/questions/6049470/can-apache-commons-cli-options-parser-ignore-unknown-command-line-options
 */
public class ExtendedGnuParser extends GnuParser {

    private boolean ignoreUnrecognizedOption;

    public ExtendedGnuParser(final boolean ignoreUnrecognizedOption) {
        this.ignoreUnrecognizedOption = ignoreUnrecognizedOption;
    }

    @Override
    protected void processOption(final String arg, final ListIterator iter) throws     ParseException {
        boolean hasOption = getOptions().hasOption(arg);

        if (hasOption || !ignoreUnrecognizedOption) {
            super.processOption(arg, iter);
        }
    }

}
