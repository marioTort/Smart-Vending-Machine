package it.unipa.ing.info.lm32.tortorici.mario.tortorici.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {

    private DataSource dataSource;

    public DBConnector() {

        try {

            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/tortorici");

        } catch (NamingException e) {

            throw new IllegalStateException("Database doesn't exist!");

        }

    }

    public Connection connessioneDB() throws SQLException {

        return dataSource.getConnection();

    }

    /*

    public void close() throws SQLException {
        this.connessioneDB().close();
    }
     */

}
