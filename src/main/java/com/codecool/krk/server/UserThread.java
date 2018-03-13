package com.codecool.krk.server;

import com.codecool.krk.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;

public class UserThread extends Thread {
    private Server server;
    private ObjectOutputStream out;
    ObjectInputStream in;

    public UserThread(Server server, ObjectOutputStream out, ObjectInputStream in) {
        this.server = server;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            Message controlMessage = (Message) in.readObject();

            if (controlMessage.getContent().equalsIgnoreCase("control")) {
                sendUsersList();

                server.addUserThread(controlMessage.getAuthor(), this);
                sendMessageUserConnected(controlMessage);

                Message clientMessage;
                do {
                    clientMessage = (Message) in.readObject();
                    if (!clientMessage.getContent().equalsIgnoreCase(".quit!")) {
                        server.broadcastMessage(clientMessage, this);
                    }
                } while (!clientMessage.getContent().equalsIgnoreCase(".quit!"));

                sendMessageUserQuit(controlMessage);
                server.removeUser(clientMessage.getAuthor());
            } else {
                // maybe custom exception
                throw new NoSuchElementException("No control message from Client");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) throws IOException{
        out.writeObject(message);
    }

    private void sendMessageUserQuit(Message controlMessage) {
        Message userQuit = new Message(String.format("%s has quit the server",
                controlMessage.getAuthor()), this.server.getName());
        server.broadcastMessage(userQuit, this);
    }

    private void sendMessageUserConnected(Message controlMessage) {
        Message userConnected = new Message(String.format("%s has connect the server",
                controlMessage.getAuthor()), this.server.getName());
        server.broadcastMessage(userConnected, this);
    }

    private void sendUsersList() throws IOException{
        if (this.server.hasUsers()) {
            Message usersList = new Message(this.server.getFormattedUsersList(), this.server.getName());
            this.sendMessage(usersList);
        }
    }
}
