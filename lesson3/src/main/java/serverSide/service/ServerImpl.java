package serverSide.service;

import dataBaseController.dao.UsersDAO;
import serverSide.handlers.ClientHandler;
import serverSide.interfaces.AuthService;
import serverSide.interfaces.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerImpl implements Server {

    private List<ClientHandler> clients;
    private AuthService authService;

    public ServerImpl() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            authService = new AuthServiceImpl();
            authService.start();
            clients = new LinkedList<>();
            while (true) {
                System.out.println("Waiting for clients");
                Socket socket = serverSocket.accept();
                System.out.println("Client joined");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Problem with server");
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
