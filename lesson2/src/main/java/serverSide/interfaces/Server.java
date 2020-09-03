package serverSide.interfaces;

import serverSide.handlers.ClientHandler;

public interface Server {

    int PORT = 8189;

    boolean isNickBusy(String nick);

    void broadcastMsg(String msg);

    void unicastMsg(String msg, String nick);

    void subscribe(ClientHandler clientHandler);

    void unsubscribe(ClientHandler clientHandler);

    void broadcastClientList();

    AuthService getAuthService();

    void updateClientNick(String currentNick, String newNick);
}
