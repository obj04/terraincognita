package com.github.obj04.terraincognita.client;

import com.github.obj04.terraincognita.game.Block;
import com.github.obj04.terraincognita.game.BlockCoordinates;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection {
    public Socket server;
    public final String ip;
    public final int port;
    DataOutputStream out;
    DataInputStream in;
    boolean connectionLost = false;
    Map<BlockCoordinates, Block> blockBuffer = new HashMap<>();

    public ServerConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            try {
            this.server = new Socket(ip, port);
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "Unknown host", "Error", 1);
            }
            this.out = new DataOutputStream(server.getOutputStream());
            this.in = new DataInputStream(server.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block getBlock(BlockCoordinates pos) {
        Block bufferedBlock = this.blockBuffer.get(pos);
        if(bufferedBlock != null)
            return bufferedBlock;
        if(this.connectionLost) {
            return new Block(0);
        }
        try {
            out.writeByte(getReqCode(Request.GET_BLOCK));
            out.writeLong(pos.x);
            out.writeInt(pos.y);
            Block block = new Block(in.read());
            this.blockBuffer.put(pos, block);
            return block;
        } catch (SocketException e) {
            this.connectionLost = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Block(0);
    }

    byte getReqCode(Request r) {
        Request[] requestTypes = Request.values();
        for(byte i = 0; i < requestTypes.length; i++) {
            if(requestTypes[i] == r)
                return i;
        }
        System.out.println("Invalid enum element");
        return -1;
    }
}
