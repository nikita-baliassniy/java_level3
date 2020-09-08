package serverSide.interfaces;

public interface AuthService {

    void start();

    void fullUsersList();

    String getNick(String login, String password);

    boolean changeNick(String login, String password, String newNick);

    void stop();

}
