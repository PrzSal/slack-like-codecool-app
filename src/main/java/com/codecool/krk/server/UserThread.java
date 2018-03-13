package com.codecool.krk.server;

import java.net.Socket;

public class UserThread extends Thread {
    private Server server;
    private Socket socket;

    public UserThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
