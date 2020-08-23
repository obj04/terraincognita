package com.github.obj04.terraincognita.client;

import javax.swing.*;

public class Client extends Thread {
    String serverAddress;
    int serverPort;
    ServerConnection connection;
    GameWindow window;

    public Client(String host, int port) {
        this.serverAddress = host;
        this.serverPort = port;
    }


    @Override
    public void run() {
        try {
            sleep(1000);
        } catch(InterruptedException e) {}
        try {
            this.connection = new ServerConnection(this.serverAddress, this.serverPort);
        } catch(NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to the given server", "Error", 1);
            return;
        }
        this.window = new GameWindow(this);
    }
}
