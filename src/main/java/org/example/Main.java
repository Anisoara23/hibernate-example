package org.example;

import org.example.ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        console();
    }

    private static void console() {
        UserInterface userInterface = new UserInterface();
        userInterface.displayUserInterface();
    }
}