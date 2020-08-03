package com.github.obj04.terraincognita.client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Client extends Thread {
    public Socket server;
    public final String ip;
    public final int port;
    DataOutputStream out;
    DataInputStream in;


    public Client(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }


    @Override
    public void run()
    {
        try
        {
            server = new Socket(ip, port);
            out = new DataOutputStream(server.getOutputStream());
            in = new DataInputStream(server.getInputStream());

            String input = new String();

            while(!input.equalsIgnoreCase("disconnect"))
            {
                input = JOptionPane.showInputDialog(null, "Send request to server", "");
                out.writeUTF(input);
            }
            server.close();
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Can´t connect!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can´t connect!");
        }
    }
}
