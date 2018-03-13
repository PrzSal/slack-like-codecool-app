package com.codecool.krk.appcontroller;

public class SlackController {

    String[] args;

    public SlackController(String[] args) {
        this.args = args;
    }

    private  Mode getUserChoice() {
        Mode userChoice;

        try {
            userChoice = Mode.valueOf(this.args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            userChoice = Mode.NOT_VALID;
        }

        return userChoice;
    }
}
