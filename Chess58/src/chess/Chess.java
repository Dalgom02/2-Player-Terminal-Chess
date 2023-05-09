package chess;
import java.util.Scanner;

import chessObject.Piece;
import chessObject.Queen;

public class Chess {

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        boolean isWhiteTurn = true;
        boolean printBoard = true;
        boolean drawOffered = false;

        while (true) {
            if (printBoard) {
                board.printBoard();
            }
            
            System.out.println();
            System.out.print(isWhiteTurn ? "White's move: " : "Black's move: ");
            String move = scanner.nextLine();
    
            if (move.equalsIgnoreCase("quit")) {
                break;
            }
    
            if (move.equalsIgnoreCase("resign")) {
                System.out.println(isWhiteTurn ? "Black wins" : "White wins");
                break;
            }
            
            if (move.equalsIgnoreCase("draw") && drawOffered) {
                System.out.println("Draw");
                break;
            }
            
            drawOffered = false;

            String[] moves = move.trim().split("\\s+");
            if (moves.length == 2 || moves.length == 3) {
                String start = moves[0];
                String end = moves[1];

                /// Used for promotion
                int destLoc[] = board.destLocation(end);


                if (moves.length == 3 && moves[2].equalsIgnoreCase("draw?")) {
                    drawOffered = true;
                }

                if (board.makeMove(start, end, isWhiteTurn)) {
                    
                    if (board.isChecking(isWhiteTurn)) {
               if (board.isChecking(!isWhiteTurn) && board.isCheckmate(!isWhiteTurn, board)) {
             System.out.println("Checkmate");
                System.out.println(isWhiteTurn ? "Black" : "White" + " wins.");
             break;
        } else {
         System.out.println("Check");
            }

                    }
                    
                
                    ////// Promotion
                    if ((moves.length == 3 || moves.length == 2) && (board.getPiece(destLoc[0], destLoc[1]).getType() == "P") ){
                        boolean color = board.getPiece(destLoc[0], destLoc[1]).isBlack();
                        if(color){
                            if(destLoc[1] == 0){
                                if (moves.length == 3){
                                    board.matchingPiece(moves[2], color, destLoc);
                                }
                                if (moves.length == 2){
                                    Piece promo = new Queen(color, destLoc[0], destLoc[1]);
                                    board.setPiece(promo, destLoc[0], destLoc[1]);
                                }
                            }
                        }else{
                            if(destLoc[1] == 7){
                                if (moves.length == 3){
                                    board.matchingPiece(moves[2], color, destLoc);
                                }
                                if (moves.length == 2){
                                    Piece promo = new Queen(color, destLoc[0], destLoc[1]);
                                    board.setPiece(promo, destLoc[0], destLoc[1]);
                                }
    
                            }
                        }
                    }

                    isWhiteTurn = !isWhiteTurn;
                    printBoard = true;
                } else {
                    System.out.println("Illegal move, try again");
                    printBoard = false;
                }
            } else {
                System.out.println("Invalid input format, try again");
                printBoard = false;
            }
        }
    
        scanner.close();
    }
}
