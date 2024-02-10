package de.uulm.in.vs.vns.p7.Messages;

import java.io.*;
import java.net.Socket;

public class MessageParser implements Runnable {

    private Socket socket;

    public MessageParser(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            var reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            var writer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );


            while(true) {
                String request = reader.readLine();
                parse(request);
            }

        } catch (Exception e) {

        }
    }

    public Object parse(String rawMessage) {
        var parts = rawMessage.split(" ");

        switch (parts[0]) {
            case "RESERVE": on_reserve(parts[1]);
        }

        return null;
    }


    private void on_reserve(String seat) {

    }
}
