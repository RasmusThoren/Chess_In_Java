package org.example;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> pieces;
    private List<Position> positions;

    public List<Piece> getPieces() {
        return pieces;
    }

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
        System.out.println(this.getPositions());
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
        PieceType[] pieceOrder = {PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.KING, PieceType.QUEEN, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK};

        for (int i = 0; i < 8; i++) {
            positions.get(i).PlacePieceOnPos(new Piece(pieceOrder[i], Color.WHITE));
            positions.get(56 + i).PlacePieceOnPos(new Piece(pieceOrder[i], Color.BLACK));
        }
    }

    public Position getPositionFromName(String name) {
        for (Position position : positions) {
            if (position.getName().equals(name)) {
                return position;
            }
        }
        return null;
    }

    private String getPositionName(int index) {
        char file = (char) ('h' - (index % 8));
        int rank = (index / 8) + 1;
        return "" + file + rank;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public String printBoard(Color PieceColor) {
        List<Position> pos = getPositions();
        String colMarker = "         H     G     F     E     D     C     B     A \n";
        if (PieceColor == Color.WHITE) {
            pos= pos.reversed();
            colMarker = "         A     B     C     D     E     F     G     H \n \n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(colMarker);
        for (int i = 0; i < 8; i++) {
            if (PieceColor == Color.BLACK) {
                sb.append(i+1).append("     ");
            }else {
                sb.append(8-i).append("     ");
            }
            for (int j = 0; j < 8; j++) {
                int offset = i * 8;
                sb.append('|');
                sb.append(pos.get(offset+j).toString());
            }
            sb.append('|');
            if (PieceColor == Color.BLACK) {
                sb.append("     ").append(i+1);
            }else {
                sb.append("     ").append(8-i);
            }
            sb.append("\n \n");
        }
        sb.append(colMarker);
        return sb.toString();
    }

    public Position getPositionfromRowColumn(int row, int col) {
        int index = row * 8 + col;
        return positions.get(index);
    }
}
