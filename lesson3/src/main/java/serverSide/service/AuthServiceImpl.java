package serverSide.service;

import dataBaseController.User;
import dataBaseController.dao.UsersDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serverSide.interfaces.AuthService;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    private List<User> userList;
    private static UsersDAO usersController;
    public static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);

    public AuthServiceImpl() {
        userList = new LinkedList<>();
        usersController = new UsersDAO();
    }

    @Override
    public void start() {
        LOGGER.info("AUTHENTICATION SERVICE IS UP NOW");
    }

    @Override
    public String getNick(String login, String password) {
        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user.getNick();
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        userList.add(user);
    }

    @Override
    public User getUser(String login, String password) {
        try {
            return usersController.getUser(login, password);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean changeNick(String login, String password, String newNick) {
        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                if (usersController.changeNick(user, newNick)) {
                    user.setNick(newNick);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void stop() {
        LOGGER.info("AUTHENTICATION SERVICE IS OFF NOW");
    }

}
