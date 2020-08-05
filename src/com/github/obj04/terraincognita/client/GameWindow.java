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
                        fov.yPawn -= 0.1;
                        break;
                    case 's':
                        fov.yPawn += 0.1;
                        break;
                    case 'a':
                        fov.xPawn += 0.1;
                        break;
                    case 'd':
                        fov.xPawn -= 0.1;
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
