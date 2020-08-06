package com.github.obj04.terraincognita.client.graphics;

import com.github.obj04.terraincognita.client.ServerConnection;
import com.github.obj04.terraincognita.game.Block;
import com.github.obj04.terraincognita.game.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

public class FOV extends JPanel {
    ServerConnection connection;
    public long xPos;
    public int yPos;
    public float xPawn;
    public float yPawn;
    float deltaX = 0;
    float deltaY = 0;
    PawnDirection lastPawnDirection;
    ArrayList<Coordinates> renderedBlocks = new ArrayList<>();
    Dimension blockSize;

    public FOV(ServerConnection connection) {
        this.connection = connection;
        this.xPos = 0L;
        this.yPos = 53;
        this.renderedBlocks.clear();
        this.blockSize = new Dimension(128, 128);
    }

    public void paint(Graphics g) {
        g.copyArea(0, 0, this.getWidth(), this.getHeight(), (int) (this.blockSize.width * this.deltaX), (int) (this.blockSize.height * this.deltaY));
        this.adjustPosition();
        int maxY = this.getHeight() / this.blockSize.height + 2;
        int maxX = this.getWidth() / this.blockSize.width + 2;
        ArrayList<Coordinates> visibleBlocks = new ArrayList<>();
        visibleBlocks.clear();
        for(int y = -1; y < maxY - 1; y++) {
            for(int x = -1; x < maxX - 1; x++) {
                Coordinates blockPos = new Coordinates((long) (this.xPos - x), (int) (this.yPos - y));
                visibleBlocks.add(blockPos);
                if(this.needToRender(blockPos)) {
                    Block block = this.connection.getBlock(blockPos);
                    block.getTexture().getImage(this.blockSize).paintIcon(this, g, Math.round(this.blockSize.width * (x + xPawn)), Math.round(this.blockSize.height * (y + yPawn)));
                }
            }
        }
        this.renderedBlocks = visibleBlocks;
    }

    boolean needToRender(Coordinates pos) {
        boolean result = true;
        for(Coordinates renderedBlock : this.renderedBlocks) {
            boolean condX = (renderedBlock.x == pos.x);
            boolean condY = (renderedBlock.y == pos.y);
            switch(this.lastPawnDirection) {
                case UP:
                    condY = (renderedBlock.y - 1 == pos.y);
                    break;
                case DOWN:
                    condY = (renderedBlock.y + 2 == pos.y);
                    break;
                case LEFT:
                    condX = (renderedBlock.x + 2 == pos.x);
                    break;
                case RIGHT:
                    condX = (renderedBlock.x - 1 == pos.x);
                    break;            }
            if(condX && condY) {
                result = false;
            }
        }
        return result;
    }

    public void move(float deltaX, float deltaY) {
        this.deltaX += deltaX;
        this.deltaY += deltaY;
        if(deltaX < 0)
            this.lastPawnDirection = PawnDirection.LEFT;
        if(deltaX > 0)
            this.lastPawnDirection = PawnDirection.RIGHT;
        if(deltaY < 0)
            this.lastPawnDirection = PawnDirection.DOWN;
        if(deltaY > 0)
            this.lastPawnDirection = PawnDirection.UP;
    }

    void adjustPosition() {
        this.xPawn += this.deltaX;
        this.yPawn += this.deltaY;
        this.deltaX = 0;
        this.deltaY = 0;
        while(this.xPawn <= 0) {
            this.xPos--;
            this.xPawn++;
        }
        while(this.xPawn >= 1) {
            this.xPos++;
            this.xPawn--;
        }
        while(this.yPawn <= 0) {
            this.yPos--;
            this.yPawn++;
        }
        while(this.yPawn >= 1) {
            this.yPos++;
            this.yPawn--;
        }
    }

    enum PawnDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }
}
