package com.github.obj04.terraincognita.server.world.generator;

import com.github.obj04.terraincognita.game.Block;
import com.github.obj04.terraincognita.game.Coordinates;
import com.github.obj04.terraincognita.server.world.World;

public class Generator {
    World world;
    Simplex simplex;

    public Generator(World world, long seed) {
        this.world = world;
        this.simplex = new Simplex(seed);
    }

    public void generateAt(long xPos) {
        world.setBlock(new Coordinates(xPos, 50), new Block(2));
        if(xPos == 0) {
            for(int i = 0; i < 100; i++) {
                world.setBlock(new Coordinates(0L, i), new Block(2));
            }
        }
    }
}
