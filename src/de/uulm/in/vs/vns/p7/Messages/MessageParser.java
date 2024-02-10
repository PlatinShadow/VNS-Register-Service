package de.uulm.in.vs.vns.p7.Messages;

import java.util.List;

public class MessageParser {
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
