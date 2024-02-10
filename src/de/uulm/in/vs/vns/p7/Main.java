package de.uulm.in.vs.vns.p7;

import de.uulm.in.vs.vns.p7.Core.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}