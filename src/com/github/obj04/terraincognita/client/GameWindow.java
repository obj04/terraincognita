package com.github.obj04.terraincognita.client;

import com.github.obj04.terraincognita.client.graphics.FOV;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {
    Client client;
    ServerConnection connection;
    FOV fov;

    public GameWindow(Client client) {
        super("Terra Incognita");
        this.client = client;
        this.connection = this.client.connection;
        this.fov = new FOV(this.connection);

        this.add(this.fov);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                switch(keyEvent.getKeyChar()) {
                    case 'w':
                        fov.move(0f, 0.1f);
                        break;
                    case 's':
                        fov.move(0f, -0.1f);
                        break;
                    case 'a':
                        fov.move(0.1f, 0f);
                        break;
                    case 'd':
                        fov.move(-0.1f, 0f);
                        break;
                }
                fov.repaint();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
