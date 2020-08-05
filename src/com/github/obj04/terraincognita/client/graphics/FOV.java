package com.github.obj04.terraincognita.client.graphics;

import com.github.obj04.terraincognita.client.ServerConnection;
import com.github.obj04.terraincognita.game.Block;
import com.github.obj04.terraincognita.game.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class FOV extends JPanel {
    ServerConnection connection;
    public long xPos;
    public int yPos;
    public float xPawn;
    public float yPawn;
    Dimension blockSize;

    public FOV(ServerConnection connection) {
        this.connection = connection;
        this.xPos = 0L;
        this.yPos = 53;
        this.blockSize = new Dimension(128, 128);
    }

    public void paint(Graphics g) {
        this.adjustPosition();
        for(int y = -1; y < this.getHeight() / this.blockSize.height + 2; y++) {
            for(int x = -1; x < this.getWidth() / this.blockSize.width + 2; x++) {
                Block block = this.connection.getBlock(new Coordinates((long) (this.xPos + x), (int) (this.yPos - y)));
                block.getTexture().getImage(this.blockSize).paintIcon(this, g, Math.round(this.blockSize.width * (x + xPawn)), Math.round(this.blockSize.height * (y - yPawn)));
            }
        }
    }

    void adjustPosition() {
        while(this.xPawn <= -1) {
            this.xPos--;
            this.xPawn++;
        }
        while(this.xPawn >= 1) {
            this.xPos++;
            this.xPawn--;
        }
        while(this.yPawn <= -1) {
            this.yPos--;
            this.yPawn++;
        }
        while(this.yPawn >= 1) {
            this.yPos++;
            this.yPawn--;
        }
    }
}
