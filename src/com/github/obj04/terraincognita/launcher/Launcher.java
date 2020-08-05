package com.github.obj04.terraincognita.launcher;

import com.github.obj04.terraincognita.client.Client;
import com.github.obj04.terraincognita.server.Server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Launcher {
    List<Server> servers = new ArrayList<>();

    public Launcher() {

    }

    public void createServer(String name) {

    }

    public void startServer(String name) {
        Server server = new Server(new File("servers/" + name));
        this.servers.add(server);
        server.start();
    }

    public void joinServer(String name) {

    }

    public void joinServer(String address, int port) {
        new Client(address, port).start();
    }
}
