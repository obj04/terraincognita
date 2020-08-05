package com.github.obj04.terraincognita.client.graphics;

import com.sun.imageio.plugins.common.ImageUtil;

import javax.swing.*;
import java.awt.*;

public class Texture {
    ImageIcon image;

    public Texture(String name) {
        this.image = new ImageIcon("resources/textures/" + name + ".gif");
    }

    public ImageIcon getImage(Dimension size) {
        return new ImageIcon(this.image.getImage().getScaledInstance(size.width, size.height, 0));
    }
}
