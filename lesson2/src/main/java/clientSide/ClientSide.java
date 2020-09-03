package clientSide;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientSide extends Application {

    public ClientSide() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Network Chat");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        launch();
    }

}
