package serverSide.handlers;

import dataBaseController.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serverSide.interfaces.Server;
import serverSide.service.ServerImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {

    private Server server;
    private Socket socket;
    private volatile DataInputStream dis;
    private volatile DataOutputStream dos;
    private volatile boolean isIdle = false;
    private String nick;
    public static final Logger LOGGER = LogManager.getLogger(ServerImpl.class);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public String getNick() {
        return nick;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.nick = "";
            new Thread(() -> {
                try {
                    authentication();
                    readMessage();
                } catch (IOException e) {
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            LOGGER.error("ERROR WHILE CREATING CLIENT HANDLER");
            throw new RuntimeException();
        }
    }

    private void authentication() {
        // Поток контроля, что пользователь авторизуется за заданное время
        Thread authorityControlThread = new Thread(() -> {
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.nick.equals("")) {
                sendMsg("You were disconnected due the idleness\n");
                isIdle = true;
            }

        });
        authorityControlThread.setDaemon(true);
        executorService.execute(authorityControlThread);

        while (true) {
            try {
                String string = dis.readUTF();
                dos.flush();

                if (isIdle) {
                    return;
                }
                if (string.startsWith("/auth")) {
                    String[] dataArray = string.split("\\s");
                    User user = server.getAuthService().getUser(dataArray[1], dataArray[2]);
                    String nick = user.getNick();
                    if (nick != null) {
                        if (!server.isNickBusy(nick)) {
                            sendMsg("/authOk " + nick);
                            this.nick = nick;
                            server.broadcastMsg(this.nick + " Joined the chat");
                            server.getAuthService().addUser(user);
                            server.subscribe(this);
                            return;
                        } else {
                            sendMsg("You are logged in");
                        }
                    } else {
                        sendMsg("Incorrect password or login");
                    }
                }
            } catch (Exception e) {
            }
        }

    }

    public void sendMsg(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            LOGGER.error("ERROR WHILE SENDING MESSAGE", e);
        }
    }

    public void readMessage() throws IOException {
        while (true) {
            String clientStr = dis.readUTF();
            LOGGER.info("from " + this.nick + ":" + clientStr);
            if (clientStr.contains("/exit")) { // Ветка выхода
                return;
            }
            if (clientStr.startsWith("/w")) { // Ветка отправки персонального сообщения
                String[] dataArray = clientStr.split("\\s", 3);
                if (dataArray.length > 2) {
                    server.unicastMsg(this.nick + ": " + dataArray[2], dataArray[1]);
                }
            } else if (clientStr.startsWith("/clients")) { // Ветка получения списка клиентов
                server.broadcastClientList();
            } else if (clientStr.startsWith("/change")) {  // Ветка изменения ника
                String[] dataArray = clientStr.split("\\s", 4);
                String newNick = dataArray[3];
                String currentNick = server.getAuthService().getNick(dataArray[1], dataArray[2]);
                if (currentNick != null) {
                    if (!server.isNickBusy(newNick)) {
                        boolean result = server.getAuthService().changeNick(dataArray[1], dataArray[2], newNick);
                        if (result) {
                            sendMsg("Your nick is " + currentNick);
                            server.broadcastMsg(currentNick + " now is known as " + newNick);
                            LOGGER.info(currentNick + " now is known as " + newNick);
                            server.updateClientNick(currentNick, newNick);
                        } else {
                            sendMsg("There was an error while changing your nick");
                        }
                    } else {
                        sendMsg("This nick is already in use, try another");
                    }
                } else {
                    sendMsg("Incorrect password or login");
                }
            } else {
                server.broadcastMsg(this.nick + ": " + clientStr);
            }
        }
    }

    private void closeConnection() {
        server.unsubscribe(this);
        server.broadcastMsg(this.nick + " left the chat");
        try {
            dis.close();
        } catch (IOException e) {
            LOGGER.error("ERROR WHILE CLOSING INPUT STREAM", e);
        }
        try {
            dos.close();
        } catch (IOException e) {
            LOGGER.error("ERROR WHILE CLOSING OUTPUT STREAM", e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.error("ERROR WHILE CLOSING SOCKET", e);
        }
        executorService.shutdown();
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
