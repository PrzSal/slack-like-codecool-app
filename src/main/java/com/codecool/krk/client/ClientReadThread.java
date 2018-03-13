package com.codecool.krk.client;

import java.io.ObjectInputStream;

public class ClientReadThread extends Thread {
    private ObjectInputStream objectInputStream;

    public ClientReadThread(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }
}