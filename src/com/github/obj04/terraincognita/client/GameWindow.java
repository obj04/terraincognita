package com.github.obj04.terraincognita.client;

import com.github.obj04.terraincognita.client.graphics.FOV;
import com.github.obj04.terraincognita.client.graphics.InventoryBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {
    Client client;
    ServerConnection connection;
    FOV fov;
    JPanel bottomPanel;
    InventoryBar invBar;

    public GameWindow(Client client) {
        super("Terra Incognita");
        this.setLayout(new BorderLayout());
        this.client = client;
        this.connection = this.client.connection;

        this.fov = new FOV(this.connection);

        this.invBar = new InventoryBar();
        JPanel invBarWrapper = new JPanel();
        invBarWrapper.setLayout(new BorderLayout());
        invBarWrapper.add(invBar, BorderLayout.WEST);
        this.bottomPanel = new JPanel();
        this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, BoxLayout.X_AXIS));
        this.bottomPanel.add(new JLabel("Inventory:"));
        this.bottomPanel.add(invBarWrapper);

        this.add(this.fov, BorderLayout.CENTER);
        this.add(this.bottomPanel, BorderLayout.SOUTH);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                switch(keyEvent.getKeyChar()) {
                    case 'w':
                        fov.move(0f, 0.125f);
                        break;
                    case 's':
                        fov.move(0f, -0.125f);
                        break;
                    case 'a':
                        fov.move(0.125f, 0f);
                        break;
                    case 'd':
                        fov.move(-0.125f, 0f);
                        break;
                }
                fov.repaint();
                invBar.repaint();
                if(connection.connectionLost) {
                    JOptionPane.showMessageDialog(null, "Connection lost", "Error", 1);
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
