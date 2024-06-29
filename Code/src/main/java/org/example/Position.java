package org.example;

public class Position {
    private int index;
    private Piece pieceStandingOn;
    private String name;
    private Color color;

    public Position(int index, Piece pieceStandingOn, String name, Color color) {
        this.index = index;
        this.pieceStandingOn = pieceStandingOn;
        this.name = name;
        this.color = color;
    }

    public void PlacePieceOnPos(Piece pieceStandingOn) {
        this.pieceStandingOn = pieceStandingOn;
    }

    public int getIndex() {
        return index;
    }

    public Piece getPieceStandingOn() {
        return pieceStandingOn;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getColumn(){
        return this.index % 8;
    }

    public int getRow(){
        return this.index / 8;
    }

    @Override
    public String toString() {
        if(this.pieceStandingOn == null){
            return ("  -  " );
        }
        else {
            return this.pieceStandingOn.toString();
        }
    }

//    @Override
//    public String toString() {
//        return this.getName();
//    }

}
