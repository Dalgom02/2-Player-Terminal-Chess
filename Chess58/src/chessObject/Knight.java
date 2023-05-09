package chessObject;
import chess.Board;

public class Knight extends Piece {

    public Knight(boolean isBlack, int x, int y) {
        super(isBlack, x, y);
    }

    @Override
    public boolean isValidMove(int[] src, int[] dest, Board board) {
        int srcX = src[1];
        int srcY = src[0];
        int destX = dest[1];
        int destY = dest[0];

        Piece destPiece = board.getPiece(destY, destX);
        if (destPiece == null) {
            if (Math.abs(srcX - destX) == 1 && Math.abs(srcY - destY) == 2) {
                return true;
            } else if (Math.abs(srcX - destX) == 2 && Math.abs(srcY - destY) == 1) {
                return true;
            }
        } else { // Attacking
            if (isBlack() == destPiece.isBlack()) return false;
            if (Math.abs(srcX - destX) == 1 && Math.abs(srcY - destY) == 2) {
                return true;
            } else if (Math.abs(srcX - destX) == 2 && Math.abs(srcY - destY) == 1) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getType() {
        return "N";
    }
}
