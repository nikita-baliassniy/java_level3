package dataBaseController;

public class User {

    private String login;
    private String password;
    private String nick;

    public User(String login, String password, String nick) {
        this.login = login;
        this.password = password;
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }

    public User() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNick() {
        return nick;
    }


}
