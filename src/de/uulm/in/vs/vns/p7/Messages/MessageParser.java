package de.uulm.in.vs.vns.p7.Messages;

public class MessageParser {
    public Object parse(String rawMessage) {
        var parts = rawMessage.split(" ");

        switch (parts[0]) {
            // case "RESERVE": return new ReserveMessage(rawMessage);
            // ...
        }

        return null;
    }


}
