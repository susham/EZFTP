// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

import org.junit.Assert;
import org.junit.Test;

import edu.pdx.cs510.agile.team3.FTP.*;
/**
 * Created by henry on 7/22/17.
 *
 * A example use of JUnit
 */
public class JUnitTest {
    @Test
    public void jUnitTest() {
        Assert.assertEquals("FAIL - this test should never fail!", 0, 0);
        //Assert.assertEquals("OK - this test should always FAIL!", 0, 1);
        System.out.println("I'm a test output. If JUnit is working, you should see me.");
    }
}
