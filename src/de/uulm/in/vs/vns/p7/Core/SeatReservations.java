package de.uulm.in.vs.vns.p7.Core;

import de.uulm.in.vs.vns.p7.Enums.Status;

import java.util.ArrayList;
import java.util.List;

public class SeatReservations {

    // Indexing erfolgt nach folgendem System: seats[x][y] => x beschreibt die Reihe und y den Platz innerhalb der Reihe x
    private Status[][] seats;

    public SeatReservations(){
        populateSeats();
    }


    private List<Reservation> reservations = new ArrayList<>();

    private void check_timestamps() {
        List<Reservation> timedout = new ArrayList<>();

        for (var res : reservations) {
            if(System.currentTimeMillis() - res.timestamp > 60 * 1000) {
                freeSeat(res.seat);
                timedout.add(res);
            }
        }

        reservations.removeAll(timedout);
    }

    private void populateSeats(){
        seats = new Status[10][10];
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                seats[i][j] = Status.FREE;
            }
        }
    }

    @Override
    public String toString(){
        check_timestamps();

        StringBuilder listofSeats = new StringBuilder("");
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                listofSeats.append(indexToString(i,j));
                listofSeats.append(" ");

                if(seats[i][j] == Status.RESERVED || seats[i][j] == Status.TAKEN){
                    listofSeats.append(Status.TAKEN);
                    listofSeats.append(", ");
                }else {
                    listofSeats.append(Status.FREE);
                    listofSeats.append(", ");
                }
            }
        }

        listofSeats.deleteCharAt(listofSeats.length() - 1);
        listofSeats.deleteCharAt(listofSeats.length() - 1);

        //listofSeats.append(")");

        return listofSeats.toString();
    }

    private String indexToString(int x, int y){
        return (char)(x+65) + Integer.toString(y+1);
    }

    private int[] seatStringToIndex(String seat){
        int x = (int)seat.charAt(0) - 65;
        int y = Integer.parseInt(seat.substring(1)) - 1;
        return new int[]{x,y};
    }

    public synchronized Status getStatus(String seat){
        int[] index = seatStringToIndex(seat);
        if(index[0] >= 10 || index[1] >= 10)throw new RuntimeException("Index out of Bounds");
        return seats[index[0]][index[1]];
    }

    public synchronized boolean reserveSeat(String seat, long client_id){
        check_timestamps();

        if(reservations.stream().anyMatch(s -> s.client_id == client_id)) {
            return false;
        }

        int[] index = seatStringToIndex(seat);
        if(index[0] >= 10 || index[1] >= 10)return false;
        if(seats[index[0]][index[1]] != Status.FREE)return false;
        seats[index[0]][index[1]] = Status.RESERVED;

        reservations.add(new Reservation(seat, System.currentTimeMillis(), client_id));

        return true;
    }

    public synchronized  boolean bookSeat(String seat, long client_id){
        check_timestamps();


        var reservation = reservations.stream().filter(s -> s.client_id == client_id && s.seat.equals(seat)).findFirst();
        if(reservation.isEmpty()) {
            return false;
        }

        int[] index = seatStringToIndex(seat);
        if(index[0] >= 10 || index[1] >= 10)return false;
        if(seats[index[0]][index[1]] != Status.RESERVED)return false;
        seats[index[0]][index[1]] = Status.TAKEN;

        reservations.remove(reservation.get());

        return true;
    }

    public synchronized void clearReservations(long client_id) {
        var reservation = reservations.stream().filter(s -> s.client_id == client_id).findFirst();

        if(reservation.isEmpty()) {
            return;
        }

        reservations.remove(reservation.get());
        freeSeat(reservation.get().seat);
    }

    private synchronized boolean freeSeat(String seat){
        int[] index = seatStringToIndex(seat);
        if(index[0] >= 10 || index[1] >= 10)return false;
        if(seats[index[0]][index[1]] != Status.RESERVED)return false;
        seats[index[0]][index[1]] = Status.FREE;
        return true;
    }

}
