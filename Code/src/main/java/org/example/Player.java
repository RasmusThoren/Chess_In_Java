package org.example;

public class Player {
    private boolean QueenCastlingRight;
    private boolean KingCastlingRight;

    public Player() {
        QueenCastlingRight = true;
        KingCastlingRight = true;
    }

    public boolean isQueenCastlingRight() {
        return QueenCastlingRight;
    }

    public boolean isKingCastlingRight() {
        return KingCastlingRight;
    }

    public void UnenableQueenCastlingRight() {
        QueenCastlingRight = false;
    }

    public void UnenabelKingCastlingRight() {
        KingCastlingRight = false;
    }
}
