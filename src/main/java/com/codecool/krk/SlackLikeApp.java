package com.codecool.krk;

import com.codecool.krk.appcontroller.SlackController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SlackLikeApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        FXMLLoader userWindowLoader = new FXMLLoader(getClass().getResource("/userWindow.fxml"));
        root.setTop(userWindowLoader.load());
        primaryStage.setTitle("Slack like App");
        Scene scene = new Scene(root, 550, 400);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/style.css");
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
    }

    public static void main( String[] args ) {
        launch(args);
    }
}
