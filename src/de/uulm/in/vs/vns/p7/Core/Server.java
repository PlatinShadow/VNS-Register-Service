package de.uulm.in.vs.vns.p7.Core;

import de.uulm.in.vs.vns.p7.Messages.MessageParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ServerSocket socket;

    private final ExecutorService thread_pool;

    private final SeatReservations reservation_service;

    /**
     * Initializes the socket server
     * @throws IOException
     */
    public Server() throws IOException {
        socket = new ServerSocket(9876);
        thread_pool = Executors.newCachedThreadPool();
        reservation_service = new SeatReservations();
    }

    /**
     * Runs the service and accepts new clients
     * This is a blocking call
     * @throws IOException
     */
    public void run() throws IOException {
        // Check timeouts
        thread_pool.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        // Listen for clients
        while(true) {
            Socket client = socket.accept();
            System.out.println("New Client: " + client.getInetAddress());

            thread_pool.execute(new MessageParser(client, reservation_service));
        }
    }

}
