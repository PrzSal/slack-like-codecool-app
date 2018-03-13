package com.codecool.krk.server;

import com.codecool.krk.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class Server {
    private final String name = "SERVER";
    private int portNumber;
    private HashMap<String, Thread> userThreads;

    public Server(int portNumber) {
        this.portNumber = portNumber;

        this.userThreads = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(this.portNumber)) {
            System.out.printf("Chat Server is listening on port %d\n", this.portNumber);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("New user connected");

                Thread newUserThread = new UserThread(socket,this);
                newUserThread.start();
            }
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void broadcastMessage(Message message, Thread excludeUserThread) {
        try {
            for (String user: getUserNames()) {
                Thread thread = this.userThreads.get(user);
                if (thread != excludeUserThread) {
                    UserThread userThread = (UserThread) thread;
                    userThread.sendMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void addUserThread(String userName, Thread userThread) {
        this.userThreads.put(userName, userThread);
    }

    public void removeUser(String userName) {
        this.userThreads.remove(userName);
        System.out.printf("The user %s quit\n", userName);
    }

    public Set<String> getUserNames() {
        return this.userThreads.keySet();
    }

    public boolean hasUsers() {
        return !this.userThreads.isEmpty();
    }

    public String getFormattedUsersList() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Users list:\n");
        for (String userName: getUserNames()) {
            sb.append(String.format("\t%s\n", userName));
        }
        return sb.toString();
    }
}
