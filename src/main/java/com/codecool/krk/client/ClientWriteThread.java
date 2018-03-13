package com.codecool.krk.client;

import java.io.ObjectOutputStream;

public class ClientWriteThread extends Thread {

    private ObjectOutputStream objectOutputStream;
    private Client client;

    public ClientWriteThread(ObjectOutputStream objectOutputStream, Client client) {
        this.objectOutputStream = objectOutputStream;
        this.client = client;
    }
}