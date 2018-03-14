package com.codecool.krk.client;

import com.codecool.krk.message.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;

public class ClientReadThread extends Thread {
    private ObjectInputStream objectInputStream;
    private Client client;

    public ClientReadThread(ObjectInputStream objectInputStream, Client client) {
        this.objectInputStream = objectInputStream;
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message serverMessage = (Message) this.objectInputStream.readObject();
                System.out.printf("room: %s - by: %s> %s\n", serverMessage.getRoom(), serverMessage.getAuthor(), serverMessage.getContent());
            } catch (SocketException e) {
                break;
            } catch (EOFException e) {
                this.client.interruptThreads();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}