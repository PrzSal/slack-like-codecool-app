package com.codecool.krk.client;

import com.codecool.krk.message.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientWriteThread extends Thread {

    private ObjectOutputStream objectOutputStream;
    private Client client;

    public ClientWriteThread(ObjectOutputStream objectOutputStream, Client client) {
        this.objectOutputStream = objectOutputStream;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Message controlMessage = new Message("control", this.client.getUserName(), client.getActualRoom());
            objectOutputStream.writeObject(controlMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userInput;
        do {
            try {
                userInput = this.client.getStdIn().readLine();
                Message newMessage = new Message(userInput, this.client.getUserName(), client.getActualRoom());
                if (newMessage.getContent().startsWith(".connect!")) {
                    String[] parseMessage = newMessage.getContent().split("\\s+", 2);
                    if (parseMessage.length == 2) {
                        client.addRoom(parseMessage[1]);
                    }
                } else if (newMessage.getContent().startsWith(".disconnect!")) {
                    String[] parseMessage = newMessage.getContent().split("\\s+", 2);
                    if (parseMessage.length == 2) {
                        client.removeRoom(parseMessage[1]);
                    }
                } else if (newMessage.getContent().startsWith(".change_room!")) {
                    String[] parseMessage = newMessage.getContent().split("\\s+", 2);
                    if (parseMessage.length == 2) {
                        client.addRoom(parseMessage[1]);
                    }
                }
                objectOutputStream.writeObject(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        } while (!userInput.equalsIgnoreCase(".quit!"));
        System.out.println("Bye bye!");

        this.client.interruptThreads();
    }
}