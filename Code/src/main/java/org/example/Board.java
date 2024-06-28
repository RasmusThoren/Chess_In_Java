package org.example;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> pieces;
    private List<Position> positions;

    public Board() {
        this.pieces = new ArrayList<>();
        this.positions = new ArrayList<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Create positions
        for (int i = 0; i < 64; i++) {
            positions.add(new Position(i, null, getPositionName(i), (i / 8 + i % 8) % 2 == 0 ? Color.WHITE : Color.BLACK));
        }

        // Place pieces
        placePieces();
    }

    private void placePieces() {
        // Place pawns
        for (int i = 8; i < 16; i++) {
            positions.get(i).PlacePieceOnPos(new Piece(PieceType.PAWN, Color.WHITE));
            positions.get(63 - i).PlacePieceOnPos(new Piece(PieceType.PAWN, Color.BLACK));
        }

        // Place other pieces
        PieceType[] pieceOrder = {PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK};

        for (int i = 0; i < 8; i++) {
            positions.get(i).PlacePieceOnPos(new Piece(pieceOrder[i], Color.WHITE));
            positions.get(56 + i).PlacePieceOnPos(new Piece(pieceOrder[i], Color.BLACK));
        }
    }

    private String getPositionName(int index) {
        char file = (char) ('a' + index % 8);
        int rank = 8 - index / 8;
        return "" + file + rank;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("      A     B     C     D     E     F     G     H \n");
        for (int i = 0; i < 8; i++) {
            sb.append(i + "  ");
            for (int j = 0; j < 8; j++) {
                int offset = i * 8;
                sb.append('|');
                sb.append(positions.get(offset+j).toString());
            }
            sb.append('|');
            sb.append("\n   --------------------------------------------------\n");
        }
        return sb.toString();
    }
}
