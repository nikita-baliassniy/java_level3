package serverSide.service;

import dataBaseController.User;
import dataBaseController.dao.UsersDAO;
import serverSide.interfaces.AuthService;

import java.sql.SQLException;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    private List<User> userList;
    private static UsersDAO usersController;

    public AuthServiceImpl() {
        usersController = new UsersDAO();
        fullUsersList();
    }

    @Override
    public void fullUsersList() {
        try {
            this.userList = usersController.getAllUsers();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
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
    public boolean changeNick(String login, String password, String newNick) {
        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                if (usersController.changeNick(user, newNick)) {
                    fullUsersList();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }

}
