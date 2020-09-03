package clientSide;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Controller {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private volatile String myNick;
    private volatile boolean isActive;
    private volatile boolean authorized;
    @FXML
    public volatile TextArea mainTextArea;
    public TextField messageTextField;

    public void btnOneClickAction(ActionEvent actionEvent) {
        if (!isActive) {
            showTips();
            isActive = true;
        }
        sendMessage();
    }

    public void keyPressedAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (!isActive) {
                showTips();
                isActive = true;
            }
            sendMessage();
        }
    }

    public Controller() {

        myNick = "";
        try {
            socket = new Socket("localhost", 8189);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);

            //Поток, отвечающий за обмен информацией между клиентом, сервером и другими клиентами
            Thread infoExchangeThread = new Thread(() -> {
                try {
                    while (true) {
                        String strMsg = dis.readUTF();
                        if (isActive && strMsg.startsWith("/authOk")) {
                            setAuthorized(true);
                            myNick = strMsg.split("\\s", 2)[1];
                            mainTextArea.clear();
                            mainTextArea.appendText("You were authorized as " + myNick + "\n");
                            break;
                        }
                        mainTextArea.appendText(strMsg + "\n");
                    }
                    while (!myNick.equals("")) {
                        String strMsg = dis.readUTF();
                        if (strMsg.equals("/exit")) {
                            break;
                        }
                        else if (strMsg.startsWith("Your nick is ")) {
                            myNick = strMsg.split("\\s", 4)[3];
                        }
                        mainTextArea.appendText(strMsg + "\n");
                    }
                } catch (IOException e) {
                } finally {
                    try {
                        setAuthorized(false);
                        socket.close();
                        myNick = "";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            infoExchangeThread.setDaemon(true);
            infoExchangeThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTips() {
        mainTextArea.appendText("\nUse /auth <login> <password> to authorize \n");
    }

    private void sendMessage() {
        String messageText = messageTextField.getText().trim();
        if (messageText.length() > 0) {
            mainTextArea.appendText("You: ");
            mainTextArea.appendText(messageText);
            mainTextArea.appendText("\n");
            try {
                dos.writeUTF(messageText);
            } catch (IOException e) {
            }
        }
        messageTextField.clear();
        messageTextField.requestFocus();
    }

    private void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }


}