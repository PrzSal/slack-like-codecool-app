package com.codecool.krk;

import com.codecool.krk.appcontroller.SlackController;

public class SlackLikeApp {
    public static void main( String[] args ) {
        SlackController slackController = new SlackController(args);
        slackController.startApp();
    }
}
