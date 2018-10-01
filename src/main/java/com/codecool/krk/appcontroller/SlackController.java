package com.codecool.krk.appcontroller;

import com.codecool.krk.client.Client;
import com.codecool.krk.server.Server;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SlackController {

    private Mode getUserChoice() {
        Mode userChoice;

        try {
            userChoice = Mode.valueOf(UserWindowController.userWindowController.server.getText());
        } catch (IllegalArgumentException e) {
            userChoice = Mode.NOT_VALID;
        }

        return userChoice;
    }

    private int getPortNumber() {
        int portNumber = -1;

        try {

            portNumber = Integer.parseInt(UserWindowController.userWindowController.portNumber.getText());
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } finally {
            return portNumber;
        }
    }

    private String getHostName() {
        String hostName = null;

        try {
            if (UserWindowController.userWindowController.server.getText() == null) {
                hostName = "localhost";
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } finally {
            return hostName;
        }
    }

    private String getUserName() {
        String userName = UserWindowController.userWindowController.login.getText();
        return userName;
    }

    private void startClient(String hostName, int portNumber) {
        if (hostName != null) {
            String userName = getUserName();

            if (userName != null) {
                Client client = new Client(UserWindowController.userWindowController.login.getText(), hostName, portNumber, userName);
                client.execute();
            }
        }
    }

    private void startServer(int portNumber) {
        Server server = new Server(portNumber);
        server.execute();
    }

    public void startNetChat() {

        int portNumber = getPortNumber();
        Mode userChoice = getUserChoice();

        if (portNumber != -1) {
            switch (userChoice) {
                case SERVER:
                    startServer(portNumber);
                    break;
                case CLIENT:
                    String hostName = getHostName();
                    startClient(hostName, portNumber);
                    break;
                default:
                    System.err.println("No such mode");
                    break;
            }
        }

    }

}
