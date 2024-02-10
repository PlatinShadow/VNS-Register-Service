package de.uulm.in.vs.vns.p7.Messages;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
                String response = (String)parse(request);

                writer.write(response);
                writer.write("\r\n");
                writer.flush();
            }
        } catch (Exception e) {

        }
    }

    public Object parse(String rawMessage) {
        var parts = rawMessage.split(" ");

        switch (parts[0]) {
            case "LIST": on_list();
            case "RESERVE": on_reserve(parts[1]);
            case "BOOK": on_book(parts[1]);
            case "ABORT": on_abort();
        }

        return null;
    }

    private List<String> on_list(){
        return null;
    }

    private void on_reserve(String seat) {

    }


    private void on_book(String seat){

    }

    private void on_abort(){

    }

}
