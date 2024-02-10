package de.uulm.in.vs.vns.p7.Core;

import de.uulm.in.vs.vns.p7.Enums.Status;

public class SeatReservations {

    // Indexing erfolgt nach folgendem System: seats[x][y] => x beschreibt die Reihe und y den Platz innerhalb der Reihe x
    private Status[][] seats;

    public SeatReservations(){
        populateSeats();
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
        StringBuilder listofSeats = new StringBuilder("(");
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                listofSeats.append(indexToString(i,j));
                if(seats[i][j] == Status.RESERVED || seats[i][j] == Status.TAKEN){
                    listofSeats.append(Status.TAKEN);
                }else {
                    listofSeats.append(Status.TAKEN + ",");
                }
            }
        }
        return listofSeats.toString() + "\r\n";
    }

    public String indexToString(int x, int y){
        return (char)(x+65) + Integer.toString(y);
    }

    public int[] seatStringToIndex(String seat){
        int x = (int)seat.charAt(0) - 65;
        int y = Integer.parseInt(seat.substring(1,2));
        return new int[]{x,y};
    }

    public synchronized Status getStatus(String seat){
        int[] index = seatStringToIndex(seat);
        if(index[0] >= 10 || index[1] >= 10)throw new RuntimeException("Index out of Bounds");
        return seats[index[0]][index[1]];
    }

    public synchronized boolean reserveSeat(String seat){
        int[] index = seatStringToIndex(seat);
        if(index[0] >= 10 || index[1] >= 10)return false;
        if(seats[index[0]][index[1]] != Status.FREE)return false;
        seats[index[0]][index[1]] = Status.RESERVED;
        return true;
    }

    public synchronized  boolean bookSeat(String seat){
        int[] index = seatStringToIndex(seat);
        if(index[0] >= 10 || index[1] >= 10)return false;
        if(seats[index[0]][index[1]] != Status.RESERVED)return false;
        seats[index[0]][index[1]] = Status.TAKEN;
        return true;
    }

    public synchronized boolean freeSeat(String seat){
        int[] index = seatStringToIndex(seat);
        if(index[0] >= 10 || index[1] >= 10)return false;
        if(seats[index[0]][index[1]] != Status.RESERVED)return false;
        seats[index[0]][index[1]] = Status.TAKEN;
        return true;
    }

}
