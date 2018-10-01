package com.codecool.krk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Client {

    private BufferedReader stdIn;
    private String hostName;
    private int portNumber;
    private String userName;
    private Thread output;
    private Thread input;
    private Set<String> rooms;
    private String actualRoom;

    public Client(String userInput, String hostName, int portNumber, String userName) {
        this.stdIn = stdIn;
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.userName = userName;
        joinToDefaultRoom();

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

    public String getActualRoom() {
        return actualRoom;
    }

    public void setActualRoom(String actualRoom) {
        this.actualRoom = actualRoom;
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

            while (!this.input.isInterrupted() && !this.output.isInterrupted());

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

    private void joinToDefaultRoom() {
        this.rooms = new HashSet<>();
        String room = "main_room";
        this.rooms.add(room);
        this.actualRoom = room;
    }

    public void addRoom(String newRoom) {
        this.rooms.add(newRoom);
        this.actualRoom = newRoom;
        System.out.printf("You connect to %s\n", this.actualRoom);
    }

    public void removeRoom(String roomToRemove) {
        if (this.rooms.contains(roomToRemove)) {
            this.rooms.remove(roomToRemove);
            this.actualRoom = "main_room";
        } else {
            System.out.println("Room to remove don't exist");
        }
    }
}