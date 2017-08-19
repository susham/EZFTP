// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

import edu.pdx.cs510.agile.team3.db.DBManager;
import org.junit.Test;
import org.junit.Assert;

import java.sql.SQLException;

/**
 * Created by dsanchez on 8/1/17.
 */
public class DBTest {

    @Test
    public void canConnect() {
        try {
            DBManager.connect();
            DBManager.close();
        } catch (SQLException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void canSave() {
        try {
            DBManager.connect();
            DBManager.saveConnection("testuser", "testpass", "testhost");
            DBManager.close();
        } catch (SQLException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void canLogin() {
        try {
            DBManager.connect();
            DBManager.saveConnection("testuser", "testpass", "testhost");
            boolean loggedIn = DBManager.login("testuser", "testpass");
            DBManager.close();
            Assert.assertTrue(loggedIn);
        } catch (SQLException e) {
            Assert.assertTrue(e.getMessage(), false);
        }
    }
}
