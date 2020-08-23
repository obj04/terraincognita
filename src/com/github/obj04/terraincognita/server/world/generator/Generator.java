package com.github.obj04.terraincognita.server.world.generator;

import com.github.obj04.terraincognita.game.Block;
import com.github.obj04.terraincognita.game.BlockCoordinates;
import com.github.obj04.terraincognita.server.world.World;

public class Generator {
    World world;
    Simplex simplex;

    public Generator(World world, long seed) {
        this.world = world;
        this.simplex = new Simplex(seed);
    }

    public void generateAt(long xPos) {
        blankSector(xPos);
        world.setBlock(new BlockCoordinates(xPos, (int) (100 + 20 * simplex.noise(xPos / 50., 0))), new Block(2));
        if(xPos == 0) {
            for(int i = 0; i < 100; i++) {
                world.setBlock(new BlockCoordinates(0L, i), new Block(2));
            }
        }
    }

    void blankSector(long xPos) {
        for(int i = 0; i < world.height; i++) {
            world.setBlock(new BlockCoordinates(xPos, i), new Block(1));
        }
    }
}
