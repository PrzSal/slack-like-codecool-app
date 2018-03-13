package com.codecool.krk.client;

import com.codecool.krk.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;

public class ClientReadThread extends Thread {
    private ObjectInputStream objectInputStream;

    public ClientReadThread(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message serverMessage = (Message) this.objectInputStream.readObject();
                System.out.printf("%s> %s\n", serverMessage.getAuthor(), serverMessage.getContent());
            } catch (SocketException e) {
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