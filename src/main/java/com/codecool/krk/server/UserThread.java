package com.codecool.krk.server;

import com.codecool.krk.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    ObjectInputStream in;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            this.out = out;
            this.in = in;

            Message controlMessage = (Message) in.readObject();

            if (controlMessage.getContent().equalsIgnoreCase("control")) {
                server.addUserThread(controlMessage.getAuthor(), this);

                Message clientMessage;
                do {
                    clientMessage = (Message) in.readObject();
                    server.broadcastMessage(clientMessage, this);
                } while (clientMessage.getContent().equalsIgnoreCase(".quit!"));

                server.removeUser(clientMessage.getAuthor());
            }
            // give else with throw some error and message about lack of control message
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
