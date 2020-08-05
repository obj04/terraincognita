package com.github.obj04.terraincognita.client;

import com.github.obj04.terraincognita.game.Coordinates;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Client extends Thread {
    ServerConnection connection;
    GameWindow window;

    public Client(String ip, int port) {
        this.connection = new ServerConnection(ip, port);
        this.window = new GameWindow(this);
    }


    @Override
    public void run() {
        System.out.println(connection.getBlock(new Coordinates(-8171, 1000)).id);
        System.out.println(connection.getBlock(new Coordinates(-8171, 50)).id);
        //connection.close();
    }
}
