package com.github.obj04.terraincognita.client.graphics;

import com.github.obj04.terraincognita.client.ServerConnection;
import com.github.obj04.terraincognita.game.Block;
import com.github.obj04.terraincognita.game.BlockCoordinates;
import com.github.obj04.terraincognita.game.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FOV extends JPanel {
    ServerConnection connection;
    public Coordinates ulCorner;
    public Coordinates playerPosition;
    ArrayList<BlockCoordinates> renderedBlocks = new ArrayList<>();
    Dimension blockSize;

    public FOV(ServerConnection connection) {
        this.connection = connection;
        this.ulCorner = new Coordinates(0L, 100);
        this.renderedBlocks.clear();
        this.blockSize = new Dimension(128, 128);
    }

    public void paint(Graphics g) {
        g.copyArea(0, 0, this.getWidth(), this.getHeight(), (int) (this.blockSize.width * ulCorner.deltaX), (int) (this.blockSize.height * ulCorner.deltaY));
        ulCorner.adjustPosition();
        int maxY = this.getHeight() / this.blockSize.height + 2;
        int maxX = this.getWidth() / this.blockSize.width + 2;
        ArrayList<BlockCoordinates> visibleBlocks = new ArrayList<>();
        visibleBlocks.clear();
        for(int y = -1; y < maxY - 1; y++) {
            for(int x = -1; x < maxX - 1; x++) {
                BlockCoordinates blockPos = ulCorner.getBlockCoordinates(-x, -y);
                visibleBlocks.add(blockPos);
                if(this.needToRender(blockPos)) {
                    Block block = this.connection.getBlock(blockPos);
                    block.getTexture().getImage(this.blockSize).paintIcon(this, g, Math.round(this.blockSize.width * (x + ulCorner.xPawn)), Math.round(this.blockSize.height * (y + ulCorner.yPawn)));
                }
            }
        }
        this.renderedBlocks = visibleBlocks;
    }

    boolean needToRender(BlockCoordinates pos) {
        if(ulCorner.lastPawnDirection == null)
            return true;
        boolean result = true;
        for(BlockCoordinates renderedBlock : this.renderedBlocks) {
            boolean condX = (renderedBlock.x == pos.x);
            boolean condY = (renderedBlock.y == pos.y);
            switch(ulCorner.lastPawnDirection) {
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
                    break;
            }
            if(condX && condY) {
                result = false;
            }
        }
        return result;
    }

    public void move(float deltaX, float deltaY) {
        ulCorner.move(deltaX, deltaY);
    }
}
