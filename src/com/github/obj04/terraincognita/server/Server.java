package com.github.obj04.terraincognita.server;

import com.github.obj04.terraincognita.client.Request;
import com.github.obj04.terraincognita.game.BlockCoordinates;
import com.github.obj04.terraincognita.server.world.World;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    ServerSocket socket;
    ServerThreadState state;
    List<Thread> subThreads = new ArrayList<>();
    List<Socket> clients = new ArrayList<>();
    World world = new World();

    public Server(File baseDirectory) {

    }

    @Override
    public void run()
    {
        state = ServerThreadState.STARTING;
        try {
            boot();
        } catch(Exception exc) {
            state = ServerThreadState.CRASHED;
            exc.printStackTrace();
            return;
        }

        state = ServerThreadState.RUNNING;
        while(!interrupted());

        state = ServerThreadState.STOPPING;
        try {
            shutdown();
        } catch(Exception exc) {
            state = ServerThreadState.CRASHED;
        }
    }


    void boot() throws Exception
    {
        socket = new ServerSocket(38642);
        socket.setSoTimeout(1000);
        initSubThreads();
    }


    void shutdown() throws Exception
    {
        for(Thread subThread : subThreads)
        {
            subThread.interrupt();
        }
        socket.close();
    }


    void checkForClients()
    {
        try
        {
            Socket client = socket.accept();
            addClient(client);
        } catch(SocketTimeoutException exc) {
            return;
        } catch(IOException exc) {
            state = ServerThreadState.CRASHED;
            exc.printStackTrace();
            System.out.println("Server crashed.");
        }
    }


    void processRequests()
    {
        List<Socket> currentClients = new ArrayList<>(clients);
        for(Socket client : currentClients)
        {
            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());
                byte input = in.readByte();
                if(input == 0)
                    continue;
                Request request = getRequest(input);
                switch(request) {
                    case GET_BLOCK:
                        BlockCoordinates pos = new BlockCoordinates(in.readLong(), in.readInt());
                        try {
                            out.write(world.getBlock(pos).id);
                        } catch(ArrayIndexOutOfBoundsException e) {
                            this.state = ServerThreadState.CRASHED;
                            this.removeClient(client);
                        }
                }
            } catch(EOFException e) {
                removeClient(client);
            } catch(IOException e) {
                System.out.println("Server-side IOException");
                e.printStackTrace();
            } catch(NullPointerException e) {
                e.printStackTrace();
                System.out.println("Server-side NullPointerException");
            }
        }
    }

    Request getRequest(int code) {
        try {
            return Request.values()[code];
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("outOfBounds");
            return Request.HEARTBEAT;
        }
    }



    private synchronized void removeClient(Socket client) {
        clients.remove(client);
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("" + client.getRemoteSocketAddress() + " disconnected");
    }
    private synchronized void addClient(Socket client)
    {
        //try
        //{
            //long UUID = new DataInputStream(client.getInputStream()).readLong();
            clients.add(client);

            //System.out.println("New client with UUID " + UUID + " connected from " + client.getRemoteSocketAddress());
        //} catch(IOException exc) {}
    }


    void initSubThreads()
    {
        subThreads.add(new Thread(() -> {
                while(!interrupted())
                    checkForClients();
        }));
        subThreads.add(new Thread(() -> {
            while(!interrupted())
                processRequests();
        }));

        for(Thread subThread : subThreads)
        {
            subThread.start();
        }
    }


    public ServerThreadState getServerState() {
        return state;
    }
    public int clientsCount() {
        return clients.size();
    }
    public String getIP() {
        return socket.getInetAddress().getHostAddress();
    }
    public int getPort() {
        return socket.getLocalPort();
    }


    public enum ServerThreadState
    {
        INACTIVE,
        STARTING,
        RUNNING,
        STOPPING,
        CRASHED
    }
}
