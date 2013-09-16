/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * We need a custom Object Relational Mapping system here. Each entity manager
 * would request a factory to create its own custom database connection which
 * allows predefined requests such as SELECT, UPDATE, DELETE, INSERT wrapped
 * in safe methods.
 */

/**
 *
 * @author chin
 */
public class ConnectionFactory {
    
    String connectionUri;

    public ConnectionFactory(String address,
                               int port,
                               String user,
                               String password,
                               String databaseName)
            throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        connectionUri = String.format("jdbc:sqlserver://%s:%d;"
                + "user=%s;password=%s;databaseName=%s",
                address,
                port,
                user,
                password,
                databaseName);
    }

    public Connection getConnection() throws SQLException {
        // TODO Support some kind of connection pooling here
        return DriverManager.getConnection(connectionUri);
    }
}
