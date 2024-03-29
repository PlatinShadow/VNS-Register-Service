package de.uulm.in.vs.vns.p7.Messages;

import de.uulm.in.vs.vns.p7.Core.SeatReservations;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class MessageParser implements Runnable {

    private final Socket socket;

    private final SeatReservations service;

    private final long id;

    /**
     * Constructor of Message Handler
     * @param socket Client socker from ServerSocket.accept
     */
    public MessageParser(Socket socket, SeatReservations service, long id) {
        this.socket = socket;
        this.service = service;
        this.id = id;
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
            System.out.println(e.getMessage());
        }
    }

    /**
     * Parses and handels a message
     * @param rawMessage Message to parse
     * @return response to message
     */
    public String parse(String rawMessage) {
        var parts = rawMessage.split(" ");

        return switch (parts[0]) {
            case "LIST" -> on_list();
            case "RESERVE" -> on_reserve(parts[1]);
            case "BOOK" -> on_book(parts[1]);
            case "ABORT" -> on_abort();
            default -> "FAIL";
        };
    }

    /**
     * LIST
     * @return list of seats
     */
    private String on_list(){
        log("LIST");
        return service.toString();
    }

    /**
     * RESERVE <SEAT>
     * @param seat seat to reserve
     * @return FAILED or RESERVED
     */
    private String on_reserve(String seat) {
        log("RESERVE " + seat);

        if(service.reserveSeat(seat, id)) {
            return "RESERVED";
        } else {
            return "FAIL";
        }
    }

    /**
     * BOOK <SEAT>
     * @param seat seat to book
     * @return FAILED or BOOKED
     */
    private String on_book(String seat){
        log("BOOK" + seat);

        if(service.bookSeat(seat,id)) {
            return "BOOKED";
        } else {
            return "FAIL";
        }

    }

    /**
     * ABORT
     * @return ABORTED
     */
    private String on_abort(){
        log("ABORT");
        service.clearReservations(id);
        return "ABORTED";
    }

    private void log(String msg) {
        System.out.println("[" + socket.getInetAddress() + " | " + id + "]: " + msg);
    }

}
