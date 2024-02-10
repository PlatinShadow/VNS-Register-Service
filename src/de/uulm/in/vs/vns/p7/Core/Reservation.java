package de.uulm.in.vs.vns.p7.Core;

import de.uulm.in.vs.vns.p7.Enums.Status;

public class Reservation {
    public String seat;
    public long timestamp;
    public long client_id;

    public Reservation(String seat, long timestamp, long client_id) {
        this.seat = seat;
        this.timestamp = timestamp;
        this.client_id = client_id;
    }
}
