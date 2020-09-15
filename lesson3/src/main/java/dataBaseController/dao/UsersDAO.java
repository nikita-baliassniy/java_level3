package dataBaseController.dao;

import dataBaseController.DBConn;
import dataBaseController.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsersDAO {

    private PreparedStatement ps = null;

    public void addUser(User user) throws SQLException {
        ps = DBConn
                .getInstance()
                .connection()
                .prepareStatement("INSERT INTO test.users " +
                        "(login, pass, nick) VALUES (?, ?, ?)"
                );
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getNick());
        ps.executeUpdate();
    }

    public User getUser(String login, String password) throws SQLException {
        ps = DBConn
                .getInstance()
                .connection()
                .prepareStatement("SELECT * FROM test.users WHERE " +
                        "LOGIN = ?" +
                        "AND PASS = ?"
                );
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet set = ps.executeQuery();
        User user = new User();
        if (set.next()) {
            user = new User();
            user.setLogin(set.getString("LOGIN"));
            user.setPassword(set.getString("PASS"));
            user.setNick(set.getString("NICK"));
        }
        return user;
    }

    public boolean changeNick(User user, String nick) {
        try {
            ps = DBConn
                    .getInstance()
                    .connection()
                    .prepareStatement("UPDATE test.users " +
                            "SET NICK = ? " +
                            "WHERE LOGIN = ? " +
                            "AND PASS = ?"
                    );
            ps.setString(1, nick);
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
