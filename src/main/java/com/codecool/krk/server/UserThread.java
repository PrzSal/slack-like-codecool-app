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
                server.addUserThread(controlMessage.getAuthor(), this);

                Message clientMessage;
                do {
                    clientMessage = (Message) in.readObject();
                    server.broadcastMessage(clientMessage, this);
                } while (clientMessage.getContent().equalsIgnoreCase(".quit!"));

                server.removeUser(clientMessage.getAuthor());

                sendMessageUserQuit(controlMessage);
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
        Message userQuit = new Message(String.format("%s has quit server",
                controlMessage.getAuthor()), this.server.getName());
        server.broadcastMessage(userQuit, this);
    }
}
