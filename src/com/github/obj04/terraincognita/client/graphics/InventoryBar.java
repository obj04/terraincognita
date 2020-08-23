package com.github.obj04.terraincognita.client.graphics;

import javax.swing.*;
import java.awt.*;

public class InventoryBar extends JPanel {
    public int height = 50;
    public int width = 8 * height;

    public InventoryBar() {
        super();
        setLayout(new GridLayout(1, 8));
        for(int i = 0; i < 8; i++) {
            JPanel button = new JPanel();
            button.add(new JLabel(new Texture("undefined").getImage(new Dimension(50, 50))));

            JLabel label = new JLabel("0", JLabel.CENTER);

            JPanel slot = new JPanel();
            slot.setLayout(new BorderLayout());
            slot.add(button, BorderLayout.CENTER);
            slot.add(label, BorderLayout.SOUTH);
            add(slot);
        }
    }
}
