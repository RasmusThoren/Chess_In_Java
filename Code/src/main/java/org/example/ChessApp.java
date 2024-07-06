package org.example;

import java.util.Scanner;

public class ChessApp {

    public static void main(String[] args) {
        Game game = new Game();
        Board board = game.getBoard();
        Scanner scanner = new Scanner(System.in);
        while (!game.isCheckmate(game.getPlayerTurn())) {
            System.out.println(board.printBoard(game.getPlayerTurn()));
            System.out.println(game.getPlayerTurn().toString() + ", To Move");
            Move move = inputToMove(scanner.nextLine());
            while (!game.makeMove(move)) {
                System.out.println("Invalid move, please insert new move");
                move = inputToMove(scanner.nextLine());
            }
        }
        game.switchPlayerTurn();
        System.out.println(board.printBoard(game.getPlayerTurn()));
        System.out.println("CHECKMATE");
        System.out.println(game.getPlayerTurn() + " WINS");
    }

    private static Move inputToMove(String input) {
        // Split the input string by ','
        String[] parts = input.split(",");

        // Ensure there are two parts after splitting
        if (parts.length != 2) {
            throw new IllegalArgumentException("Input string is not in the correct format.");
        }

        // Return a new Move object with the parsed start and end
        return new Move(parts[0], parts[1]);
    }
}
