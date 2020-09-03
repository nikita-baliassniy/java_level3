package dataBaseController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class DBConn {

    private static DBConn instance;
    private Connection conn;

    private DBConn() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        String host = resourceBundle.getString("host");
        String port = resourceBundle.getString("port");
        String db = resourceBundle.getString("db");
        String user = resourceBundle.getString("user");
        String password = resourceBundle.getString("password");

        String jdbcURL = MessageFormat.format(
                "jdbc:mysql://{0}:{1}/{2}", host, port, db);
        conn = DriverManager.getConnection(jdbcURL, user, password);
    }

    public static DBConn getInstance() {
        if (instance == null) {
            try {
                instance = new DBConn();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return instance;
    }

    public Connection connection() {
        return conn;
    }

}
