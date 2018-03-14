package com.codecool.krk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public void execute() {
        try (Socket serverSocket = new Socket(this.hostName, this.portNumber);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverSocket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(serverSocket.getInputStream())
        ){
            this.output = new ClientWriteThread(objectOutputStream, this);
            this.input = new ClientReadThread(objectInputStream, this);

            output.start();
            input.start();

            while (!this.input.isInterrupted());

        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void interruptThreads() {
        this.output.interrupt();
        this.input.interrupt();
    }


}