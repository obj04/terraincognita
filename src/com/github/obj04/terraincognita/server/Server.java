package com.github.obj04.terraincognita.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
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
        while(!interrupted())
        {

        }

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
        } catch(IOException exc) {
            state = ServerThreadState.CRASHED;
            exc.printStackTrace();
            return;
        }
    }


    void pollRequests()
    {
        List<Socket> currentClients = new ArrayList<>(clients);
        for(Socket client : currentClients)
        {
            try {
                String input = new DataInputStream(client.getInputStream()).readUTF();
                String reqType = input.split(" ", 2)[0];
                String args = input.split(" ", 2)[1];
                System.out.println(reqType + "->" + args);

                if(reqType.compareTo("chat") == 0) {
                    System.out.println("" + client.getRemoteSocketAddress() + " > " + args);
                }
                if(reqType.compareTo("disconnect") == 0) {
                    removeClient(client);
                }
            } catch(IOException exc) {
                System.out.println("Server-side IOException");
                exc.printStackTrace();
            } catch(NullPointerException exc) {
                System.out.println("Server-side NullPointerException");
            }
        }
    }



    private synchronized void removeClient(Socket client)
    {
        try
        {
            clients.remove(client);
            System.out.println("" + client.getRemoteSocketAddress() + " disconnected");
        } catch(Exception exc) {}
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
        subThreads.add(new Thread() {
            @Override
            public void run()
            {
                while(!interrupted())
                    checkForClients();
            }
        });
        subThreads.add(new Thread() {
            @Override
            public void run()
            {
                while(!interrupted())
                    pollRequests();
            }
        });

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
