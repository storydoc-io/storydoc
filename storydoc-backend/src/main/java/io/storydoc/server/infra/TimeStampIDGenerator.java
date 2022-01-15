package io.storydoc.server.infra;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStampIDGenerator implements IDGenerator {

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public String generateID(String category) {
        String timeStamp =  format.format(LocalDateTime.now());
        int counter = incrementCounter(timeStamp);
        return category + "-" + timeStamp + (counter>0 ? "-" + counter: "");
    }

    String currentTimeStamp;
    int counter;

    private synchronized int incrementCounter(String timeStamp) {
        if (currentTimeStamp!=timeStamp) {
            currentTimeStamp = timeStamp;
            counter = 0;
        } else {
            counter++;
        }
        return counter;

    }
}
