package chessObject;

import chess.Board;

public class Bishop extends Piece {

    public Bishop(boolean isBlack, int x, int y) {
        super(isBlack, x, y);
    }

    @Override
    public boolean isValidMove(int[] src, int[] dest, Board board) {
        int srcX = src[1];
        int srcY = src[0];
        int destX = dest[1];
        int destY = dest[0];
    
        Piece destPiece = board.getPiece(destY, destX);
    
        boolean right = srcX < destX;
        boolean up = srcY > destY;
    
        // Will always be illegal if the x's or y's are the same
        if (srcX == destX || srcY == destY) {
            return false;
        }
    
        // Checks for diagonality
        if (Math.abs(srcX - destX) != Math.abs(srcY - destY)) {
            return false;
        }
    
        // Check for blocking pieces
        int stepX = right ? 1 : -1;
        int stepY = up ? -1 : 1;
        int currentX = srcX + stepX;
        int currentY = srcY + stepY;
    
        while (currentX != destX && currentY != destY) {
            if (board.getPiece(currentY, currentX) != null) {
                return false;
            }
            currentX += stepX;
            currentY += stepY;
        }
    
        // Not attacking
        if (destPiece == null) {
            return true;
        } else { // Attacking
            if (isBlack() && destPiece.isBlack()) return false;
            if (!isBlack() && !destPiece.isBlack()) return false;
            return true;
        }
    }
    
    
    @Override
    public String getType() {
        return "B";
    }
}
