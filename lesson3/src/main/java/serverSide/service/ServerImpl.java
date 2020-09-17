package serverSide.service;

import serverSide.handlers.ClientHandler;
import serverSide.interfaces.AuthService;
import serverSide.interfaces.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerImpl implements Server {

    private List<ClientHandler> clients;
    private AuthService authService;
    public static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);


    public ServerImpl() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            authService = new AuthServiceImpl();
            authService.start();
            clients = new LinkedList<>();
            while (true) {
                LOGGER.info("WAITING FOR CLIENTS");
                Socket socket = serverSocket.accept();
                LOGGER.info("A CLIENT HAS JOINED THE SERVER");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            LOGGER.error("THERE IS A PROBLEM WITH THE SERVER! ", e);
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    @Override
    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getNick() != null &&
                    clientHandler.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized void broadcastMsg(String msg) {
        String author = msg.split(":", 2)[0];
        for (ClientHandler c : clients) {
            if (!c.getNick().equals(author)) {
                c.sendMsg(msg);
            }
        }
    }

    @Override
    public synchronized void unicastMsg(String msg, String nick) {
        for (ClientHandler c : clients) {
            if (c.getNick().equals(nick)) {
                c.sendMsg(msg);
                break;
            }
        }
    }

    @Override
    public synchronized void broadcastClientList() {
        StringBuilder stringBuilder = new StringBuilder("/clients ");
        for (ClientHandler clientHandler : clients) {
            stringBuilder.append(clientHandler.getNick());
            stringBuilder.append(" ");
        }
        broadcastMsg(stringBuilder.toString());

    }

    @Override
    public synchronized void updateClientNick(String currentNick, String newNick) {
        for (ClientHandler c : clients) {
            if (c.getNick().equals(currentNick)) {
                c.setNick(newNick);
            }
        }
    }

    @Override
    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    @Override
    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    @Override
    public AuthService getAuthService() {
        return authService;
    }
}
