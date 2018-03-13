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

    public BufferedReader getStdIn() {
        return stdIn;
    }

    public String getUserName() {
        return userName;
    }

    public Thread getOutput() {
        return output;
    }

    public void setOutput(Thread output) {
        this.output = output;
    }

    public Thread getInput() {
        return input;
    }

    public void setInput(Thread input) {
        this.input = input;
    }

    public void interruptThreads() {
        this.output.interrupt();
        this.input.interrupt();
    }


}