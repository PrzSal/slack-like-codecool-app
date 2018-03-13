package com.codecool.krk.appcontroller;

import com.codecool.krk.client.Client;
import com.codecool.krk.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SlackController {

    String[] args;

    public SlackController(String[] args) {
        this.args = args;
    }

    private  Mode getUserChoice() {
        Mode userChoice;

        try {
            userChoice = Mode.valueOf(this.args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            userChoice = Mode.NOT_VALID;
        }

        return userChoice;
    }

    private int getPortNumber() {
        int portNumber = -1;

        try {
            if (this.args.length == 2) {
                portNumber = Integer.parseInt(this.args[1]);
            } else if (args.length == 3) {
                portNumber = Integer.parseInt(this.args[2]);
            } else {
                throw new IllegalArgumentException("Wrong arguments number");
            }
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
            if (this.args.length == 3) {
                hostName = this.args[1];
            } else {
                throw new IllegalArgumentException("Wrong arguments number");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } finally {
            return hostName;
        }
    }

    private String getUserName(BufferedReader stdIn) {
        String userName = null;

        try {
            System.out.printf("What's your name: ");
            userName = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return userName;
        }
    }

    private void startClient(BufferedReader stdIn, String hostName, int portNumber) {
        if (hostName != null) {
            String userName = getUserName(stdIn);

            if (userName != null) {
                Client client = new Client(stdIn, hostName, portNumber, userName);
                client.execute();
            }
        }
    }

    private void startServer(BufferedReader stdIn, int portNumber) {
        Server server = new Server(stdIn, portNumber);
        server.execute();
    }

    private void startNetChat() {
        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))){
            int portNumber = getPortNumber();
            Mode userChoice = getUserChoice();

            if (portNumber != -1) {
                switch (userChoice) {
                    case SERVER:
                        startServer(stdIn, portNumber);
                        break;
                    case CLIENT:
                        String hostName = getHostName();
                        startClient(stdIn, hostName, portNumber);
                        break;
                    default:
                        System.err.println("No such mode");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
