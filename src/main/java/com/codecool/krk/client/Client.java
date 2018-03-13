package com.codecool.krk.client;

import java.io.BufferedReader;

public class Client {

    private BufferedReader stdIn;
    private String hostName;
    private int portNumber;
    private String userName;
    private Thread output;
    private Thread input;

    public Client(BufferedReader stdIn, String hostName, int portNumber, String userName) {
        this.stdIn = stdIn;
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.userName = userName;
    }
}