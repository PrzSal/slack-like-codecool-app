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
            Message controlMessage = new Message("control", this.client.getUserName());
            objectOutputStream.writeObject(controlMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userInput;
        do {
            try {
                userInput = this.client.getStdIn().readLine();
                Message newMessage = new Message(userInput, this.client.getUserName());
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