package org.example;

public class Piece {
    private PieceType type;
    private Color color;


    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        String abr = Character.toString(type.toString().charAt(0));
        if (type == PieceType.KNIGHT) {
            abr = "n";
        }
        if (color == Color.BLACK) {
            return "  " + abr.toLowerCase() + "  ";
        } else {
            return "  " + abr.toUpperCase() + "  ";
        }
    }
}

