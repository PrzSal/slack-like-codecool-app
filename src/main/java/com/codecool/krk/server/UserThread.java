package com.codecool.krk.server;

import com.codecool.krk.message.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            setupStreams();

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
                throw new NoControlMessageException("No control message from Client");
            }
        } catch (EOFException e) {
            System.err.println("Client disconnected from server");
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoControlMessageException e) {
            e.printStackTrace();
        } finally {
            closeResources();
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

    private void setupStreams() throws IOException {
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.in = new ObjectInputStream(this.socket.getInputStream());
    }

    private void closeResources() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
