package com.github.obj04.terraincognita.game;

import com.github.obj04.terraincognita.client.graphics.FOV;

public class Coordinates {
    public long xPos;
    public int yPos;
    public float xPawn = .0f;
    public float yPawn = .0f;
    public float deltaX = 0;
    public float deltaY = 0;
    public PawnDirection lastPawnDirection;

    public Coordinates(long xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
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

    public void adjustPosition() {
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

    public BlockCoordinates getBlockCoordinates() {
        adjustPosition();
        return new BlockCoordinates(xPos, yPos);
    }

    public BlockCoordinates getBlockCoordinates(float deltaX, float deltaY) {
        Coordinates pawnedCoordinates = new Coordinates(this.xPos, this.yPos);
        pawnedCoordinates.move(xPawn, yPawn);
        pawnedCoordinates.move(this.deltaX, this.deltaY);
        pawnedCoordinates.move(deltaX, deltaY);
        return pawnedCoordinates.getBlockCoordinates();
    }

    public enum PawnDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }
}
