package clientSide;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.net.Socket;

public class Controller {

    // Использовал 20 вместо 100, чтобы легче было пересчитать :)
    private static final long LINE_NUMBER_FROM_END = -20;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private volatile String myNick;
    private volatile boolean isActive;
    private volatile boolean authorized;
    @FXML
    public volatile TextArea mainTextArea;
    public TextField messageTextField;
    private volatile RandomAccessFile logFile;

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
                            try {
                                logFile = new RandomAccessFile(myNick + ".txt", "rw");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            long startPosition = setFocusOnLine(LINE_NUMBER_FROM_END);
                            logFile.seek(startPosition);
                            String s;
                            while ((s = logFile.readLine()) != null) {
                                String currentString = s;
                                javafx.application.Platform.runLater( ()
                                        -> mainTextArea.appendText(currentString + "\n") );
                            }
                            break;
                        }
                        mainTextArea.appendText(strMsg + "\n");
                    }
                    while (!myNick.equals("")) {
                        String strMsg = dis.readUTF();
                        if (strMsg.equals("/exit")) {
                            mainTextArea.appendText("You logged off\n");
                            break;
                        } else if (strMsg.startsWith("Your nick is ")) {
                            myNick = strMsg.split("\\s", 4)[3];
                        }
                        mainTextArea.appendText(strMsg + "\n");
                        logFile.seek(logFile.length());
                        logFile.writeUTF(strMsg + "\n");
                    }
                } catch (IOException e) {
                } finally {
                    try {
                        setAuthorized(false);
                        socket.close();
                        myNick = "";
                        logFile.close();
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

    private long setFocusOnLine(long lineNumber) {
        try {
            long lineCount = 0;
            logFile.seek(0);
            if (lineNumber < 0) {
                while (logFile.readLine() != null) {
                    lineCount++;
                }
                logFile.seek(0);
                lineNumber = lineCount + lineNumber;
                if (lineNumber < 0) {
                    throw new IOException();
                }
            }
            for (int i = 0; i < lineNumber; i++) {
                logFile.readLine();
            }
            return logFile.getFilePointer();
        } catch (IOException e) {
            return 0;
        }
    }

    private void showTips() {
        mainTextArea.appendText("\nUse /auth <login> <password> to authorize \n");
        mainTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mainTextArea.setScrollTop(Double.MAX_VALUE);
            }
        });
    }

    private void sendMessage() {
        String messageText = messageTextField.getText().trim();
        if (messageText.length() > 0) {
            String messageTextForSelf = "You: " + messageText + "\n";
            mainTextArea.appendText(messageTextForSelf);
            try {
                dos.writeUTF(messageText);
                if (!myNick.equals("")) {
                    logFile.seek(logFile.length());
                    logFile.writeUTF(messageTextForSelf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        messageTextField.clear();
        messageTextField.requestFocus();
    }

    private void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

}