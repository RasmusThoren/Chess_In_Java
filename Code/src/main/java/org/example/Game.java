package org.example;

import java.util.function.DoubleToIntFunction;

public class Game {
    private Board board;
    private Color playerTurn;
    private Player p1;
    private Player p2;

    public Board getBoard() {
        return board;
    }

    public Color getPlayerTurn() {
        return playerTurn;
    }

    public Game() {
        this.board = new Board();
        this.playerTurn = Color.WHITE;
        this.p1 = new Player();
        this.p2 = new Player();
    }

    public boolean legalMoves(Position start, Position end) {
        switch (start.getPieceStandingOn().getType()) {
            case PAWN -> {
                return checkPawnMove(start, end);
            }
            case KNIGHT -> {
                return checkKnightMove(start, end);
            }
            case BISHOP -> {
                return checkBishopMove(start, end);
            }
            case ROOK -> {
                return checkRookMove(start, end);
            }
            case QUEEN -> {
                return checkQueenMove(start, end);
            }
            case KING -> {
                return checkKingMove(start, end);
            }
            default -> {
                throw new IllegalArgumentException("Invalid piece type: " + start.getPieceStandingOn().getType());
            }
        }
    }

    Boolean checkPawnMove(Position start, Position end) {
        int startRow = start.getRow();
        int startCol = start.getColumn();
        int endRow   = end.getRow();
        int endCol   = end.getColumn();

        Piece pawn = start.getPieceStandingOn();
        int direction = 1;
        if (pawn.getColor() == Color.BLACK) {
            direction = -1;
        }

        if (startCol == endCol && end.getPieceStandingOn() == null) {
            if (startRow + direction == end.getRow()) {
                return true;
            }
            return ((startRow == 1 || startRow == 6) && (startRow + (2 * direction)) == end.getRow());
        }
        else {
            if (Math.abs(startCol - endCol) == 1 && startRow + direction == endRow ){
                return (end.getPieceStandingOn() != null);
                }
            }
        return false;
    }

    private boolean checkKnightMove(Position start, Position end) {
        int startRow = start.getRow();
        int startCol = start.getColumn();
        int endRow = end.getRow();
        int endCol = end.getColumn();

        // Checks if the move fulfills the L shape
        if (Math.abs(startRow - endRow) == 2 && Math.abs(startCol - endCol) == 1) {
            return true;
        }
        if (Math.abs(startRow - endRow) == 1 && Math.abs(startCol - endCol) == 2) {
            return true;
        }

        return false;
    }

    private boolean checkBishopMove(Position start, Position end) {
    int startRow = start.getRow();
    int startCol = start.getColumn();
    int endRow = end.getRow();
    int endCol = end.getColumn();

    // Check if the move is diagonal
    if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) {
        return false;
    }

    // Determine the direction of the move
    int rowStep = (endRow > startRow) ? 1 : -1;
    int colStep = (endCol > startCol) ? 1 : -1;

    int currentRow = startRow + rowStep;
    int currentCol = startCol + colStep;

    // Check if all squares between the start and end positions are empty
    while (currentRow != endRow && currentCol != endCol) {
        Position currentPosition = board.getPositionfromRowColumn(currentRow, currentCol);
        if (currentPosition.getPieceStandingOn() != null) {
            return false;
        }
        currentRow += rowStep;
        currentCol += colStep;
    }
    return true;
}


    private boolean checkRookMove(Position start, Position end) {
        int startRow = start.getRow();
        int startCol = start.getColumn();
        int endRow = end.getRow();
        int endCol = end.getColumn();

        int startIndex = start.getIndex();
        int endIndex = end.getIndex();
        if (startIndex > endIndex) {
            int temp = startIndex;
            startIndex = endIndex;
            endIndex = temp;
        }
        // Check no pieces between
        if (startRow == endRow){
            for (int i = startIndex + 1 ; i < endIndex; i++){
                if(board.getPositions().get(i).getPieceStandingOn() != null){
                    return false;
                }
            }
            return true;
        }
        if (startCol==endCol) {
            for (int i = startIndex + 8; i < endIndex; i+=8){
                if(board.getPositions().get(i).getPieceStandingOn() != null){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean checkQueenMove(Position start, Position end) {
        return (checkBishopMove(start, end) || checkRookMove(start, end));
    }

    private boolean checkKingMove(Position start, Position end) {
        int startRow = start.getRow();
        int startCol = start.getColumn();
        int endRow = end.getRow();
        int endCol = end.getColumn();

        // Check if the move is one square in any direction
        if (Math.abs(startRow - endRow) <= 1 && Math.abs(startCol - endCol) <= 1) {
            // Ensure the destination square is either empty or contains an opponent's piece
            if (end.getPieceStandingOn() == null || end.getPieceStandingOn().getColor() != start.getPieceStandingOn().getColor()) {
                return true;
            }
        }
        if
        return false;
    }

    public boolean makeMove(Move move) {
        Position start = board.getPositionFromName(move.getStart());
        Position end = board.getPositionFromName(move.getEnd());

        if(start.getPieceStandingOn() == null || start.getPieceStandingOn().getColor() != this.playerTurn){
            return false;
        }
        if (end.getPieceStandingOn() != null) {
            if (start.getPieceStandingOn().getColor() == end.getPieceStandingOn().getColor()) {
                return false;
            }
        }
        if(!legalMoves(start, end)){
            return false;
        }
        // place piece on new position
        Piece startPiece = start.getPieceStandingOn();
        Piece endPiece = end.getPieceStandingOn();
        end.PlacePieceOnPos(start.getPieceStandingOn());
        start.PlacePieceOnPos(null);

        // If player is in check after move revert and return false
        Position kingPos = board.getKingPosition(playerTurn);
        if (isColorChecked(playerTurn,kingPos)){
            end.PlacePieceOnPos(endPiece);
            start.PlacePieceOnPos(startPiece);
            return false;
        }
        if (start.getPieceStandingOn().getType() == PieceType.KING) {
            if (playerTurn == Color.WHITE){
                p1.UnenabelKingCastlingRight();
                p1.UnenableQueenCastlingRight();
            } else{
                p2.UnenabelKingCastlingRight();
                p2.UnenableQueenCastlingRight();
            }
        }
        if (start.getPieceStandingOn().getType() == PieceType.ROOK) {
            if (playerTurn == Color.WHITE){
                if (start.getColumn() == 0){
                    p1.UnenableQueenCastlingRight();
                } else {
                    p1.UnenabelKingCastlingRight();
                }
            } else {
                if (start.getColumn() == 0){
                    p1.UnenableQueenCastlingRight();
                } else {
                    p1.UnenabelKingCastlingRight();
                }
            }
        }
        switchPlayerTurn();
        return true;
    }

    public boolean isColorChecked(Color color,Position kingPos) {
        for (Position pos : board.getPositions()) {
            Piece piece = pos.getPieceStandingOn();
            if (piece != null) {
                if(piece.getColor() != color){
                    if (legalMoves(pos,kingPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(Color color) {
    // If the color is not in check, it's not checkmate
    Position kingPos = board.getKingPosition(color);
    if (!isColorChecked(color,kingPos)) {
        return false;
    }

    for (Position start : board.getPositions()) {
        Piece piece = start.getPieceStandingOn();

        if (piece != null) {
            if (piece.getColor() == color) {
                // Generate all possible moves for the piece
                for (Position end : board.getPositions()) {
                    // Check if the move is legal
                    if (legalMoves(start, end)) {
                        // Temporarily make the move
                        Piece capturedPiece = end.getPieceStandingOn();
                        end.PlacePieceOnPos(piece);
                        start.PlacePieceOnPos(null);
                        boolean stillInCheck;
                        if (piece.getType() == PieceType.KING){
                            stillInCheck = isColorChecked(color, end);
                        } else {
                            stillInCheck = isColorChecked(color, kingPos);
                        }
                        // Undo the move
                        start.PlacePieceOnPos(piece);
                        end.PlacePieceOnPos(capturedPiece);

                        if (!stillInCheck) {
                            return false;
                            }
                        }
                    }
                }
            }
        }
    return true;
    }

    public void switchPlayerTurn(){
            if (this.playerTurn.equals(Color.BLACK)) {
                playerTurn = Color.WHITE;
            }else {
                playerTurn = Color.BLACK;
            }
    }
}

