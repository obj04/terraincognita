package com.github.obj04.terraincognita.server.world;

import com.github.obj04.terraincognita.game.Block;
import com.github.obj04.terraincognita.game.Coordinates;
import com.github.obj04.terraincognita.server.world.generator.Generator;

import java.util.HashMap;
import java.util.Map;

public class World {
    Generator generator;
    Map<Long, Block[]> verticalSectors = new HashMap<>();
    final int height;

    public World() {
        this.generator = new Generator(this, 34);
        this.height = 1024;
    }

    public Block getBlock(Coordinates pos) {
        Block[] sector = this.verticalSectors.get(pos.x);
        if(sector == null) {
            this.generator.generateAt(pos.x);
            sector = this.verticalSectors.get(pos.x);
        }
        return sector[pos.y];
    }

    public void setBlock(Coordinates pos, Block block) {
        Block[] sector = this.verticalSectors.get(pos.x);
        if(sector == null) {
            sector = new Block[this.height];
            for(int i = 0; i < this.height; i++) {
                sector[i] = new Block(0);
            }
            this.verticalSectors.put(pos.x, sector);
            this.generator.generateAt(pos.x);
            sector = this.verticalSectors.get(pos.x);
        }
        sector[pos.y] = block;
    }
}
