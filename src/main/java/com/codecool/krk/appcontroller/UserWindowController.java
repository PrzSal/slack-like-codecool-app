package com.codecool.krk.appcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class UserWindowController implements Initializable {

    @FXML
    public TextField login;
    @FXML
    public TextField portNumber;
    @FXML
    GridPane gridPane;
    @FXML
    GridPane gP;
    @FXML
    public Button server;
    public static UserWindowController userWindowController;

    @FXML
    public void changeStage() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/userLoginWindow.fxml"));
            Stage stage = (Stage) server.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (IOException io){
            io.printStackTrace();
        }
//        FXMLLoader userWindowLoader = new FXMLLoader(getClass().getResource("/userLoginWindow.fxml"));
//        root.setLeft(userWindowLoader.load())
    }

    @FXML
    public void startApp(){
        SlackController slackController= new SlackController();
        slackController.startNetChat();

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userWindowController = this;
    }
}
