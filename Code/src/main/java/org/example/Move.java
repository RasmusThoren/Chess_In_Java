package org.example;

public class Move {
    private String start;
    private String end;

    public Move(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
