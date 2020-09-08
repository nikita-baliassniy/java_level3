package dataBaseController.dao;

import dataBaseController.DBConn;
import dataBaseController.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public User getUserByNick(String nick) throws SQLException {
        ps = DBConn
                .getInstance()
                .connection()
                .prepareStatement("SELECT * FROM test.users WHERE " +
                        "nick = ?"
                );
        ps.setString(1, nick);
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

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        ps = DBConn
                .getInstance()
                .connection()
                .prepareStatement("SELECT * from test.users");
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            User user = new User();
            user.setLogin(set.getString("LOGIN"));
            user.setPassword(set.getString("PASS"));
            user.setNick(set.getString("NICK"));
            list.add(user);
        }
        return list;
    }

    public boolean changeNick(User user, String nick) {
        try {
            ps = DBConn
                    .getInstance()
                    .connection()
                    .prepareStatement("UPDATE test.users " +
                            "SET NICK = ? " +
                            "WHERE LOGIN = '" + user.getLogin() + "' " +
                            "AND PASS = '" + user.getPassword() + "';"
                    );
            // Не очень красиво с конкатенацией, но '?' не видит знак вопроса, \'?\' - не помогло
            ps.setString(1, nick);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
