package de.uulm.in.vs.vns.p7.Messages;

public class MessageParser {
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
