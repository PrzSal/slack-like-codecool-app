package com.codecool.krk.server;

import com.codecool.krk.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class Server {
    private BufferedReader stdIn;
    private int portNumber;
    private HashMap<String, Thread> userThreads;

    public Server(BufferedReader stdIn, int portNumber) {
        this.stdIn = stdIn;
        this.portNumber = portNumber;

        this.userThreads = new LinkedHashMap<>();
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(this.portNumber)) {
            System.out.printf("Chat Server is listening on port %d\n", this.portNumber);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                Thread newUserThread = new UserThread(socket, this);
                newUserThread.start();
            }

        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void broadcastMessage(Message message, Thread excludeUser) {

    }

    public void addUserThread(String userName, Thread userThread) {
        this.userThreads.put(userName, userThread);
    }

    public void removeUser(String userName) {
        this.userThreads.remove(userName);
    }

    public Set<String> getUserNames() {
        return this.userThreads.keySet();
    }
}
