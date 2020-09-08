package serverSide.interfaces;

import dataBaseController.User;

public interface AuthService {

    void start();

    String getNick(String login, String password);

    boolean changeNick(String login, String password, String newNick);

    void stop();

    User getUser(String login, String password);

    void addUser(User user);
}
