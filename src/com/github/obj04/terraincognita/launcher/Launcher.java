package com.github.obj04.terraincognita.launcher;

import com.github.obj04.terraincognita.client.Client;
import com.github.obj04.terraincognita.server.Server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Launcher {
    List<Server> servers = new ArrayList<Server>();

    public Launcher() {

    }

    public void startServer(File baseDirectory) {
        Server server = new Server(baseDirectory);
        this.servers.add(server);
        server.start();
        new Client("localhost", 38642).start();
    }
}
