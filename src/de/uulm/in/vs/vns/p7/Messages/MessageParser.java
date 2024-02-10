package de.uulm.in.vs.vns.p7.Messages;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class MessageParser implements Runnable {

    private final Socket socket;

    /**
     * Constructor of Message Handler
     * @param socket Client socker from ServerSocket.accept
     */
    public MessageParser(Socket socket) {
        this.socket = socket;
    }

    /**
     * Listen for incoming messages of the client
     */
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
                String response = (String)parse(request);

                writer.write(response);
                writer.write("\r\n");
                writer.flush();
            }
        } catch (Exception e) {

        }
    }

    /**
     * P
     * @param rawMessage
     * @return
     */
    public String parse(String rawMessage) {
        var parts = rawMessage.split(" ");

        return switch (parts[0]) {
            case "LIST" -> on_list();
            case "RESERVE" -> on_reserve(parts[1]);
            case "BOOK" -> on_book(parts[1]);
            case "ABORT" -> on_abort();
            default -> "INVALID_REQUEST";
        };
    }

    private String on_list(){
        log("LIST");
        return null;
    }

    private String on_reserve(String seat) {
        log("RESERVE " + seat);
        return null;
    }

    private String on_book(String seat){
        log("BOOK" + seat);
        return null;
    }

    private String on_abort(){
        log("ABORT");
        return null;
    }

    private void log(String msg) {
        System.out.println("[" + socket.getInetAddress() + "]: " + msg);
    }

}
