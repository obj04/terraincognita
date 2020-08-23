package com.github.obj04.terraincognita.game;

import com.github.obj04.terraincognita.client.Request;
import com.github.obj04.terraincognita.client.graphics.Texture;

public class Block {
    public int id;

    public Block(int id) {
        this.id = id;
    }

    public String getType() {
        try {
            return BlockID.values()[this.id].name().toLowerCase();
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Block.getType: outOfBounds (" + this.id + ")");
            return BlockID.UNDEFINED.name().toLowerCase();
        }
    }

    public Texture getTexture() {
        return new Texture(this.getType());
    }
}
