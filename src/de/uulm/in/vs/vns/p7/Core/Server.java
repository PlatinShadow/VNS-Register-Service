package de.uulm.in.vs.vns.p7.Core;

import de.uulm.in.vs.vns.p7.Messages.MessageParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket socket;

    private ExecutorService thread_pool;


    public Server() throws IOException {
        socket = new ServerSocket(9876);
        thread_pool = Executors.newCachedThreadPool();
    }

    public void run() throws IOException {
        while(true) {
            Socket client = socket.accept();
            thread_pool.execute(new MessageParser(client));
        }
    }

}
