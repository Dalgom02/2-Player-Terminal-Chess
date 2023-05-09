package chessObject;

import chess.Board;

public class Queen extends Piece {

    public Queen(boolean isBlack, int x, int y) {
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
        boolean vertical = srcY != destY;
        boolean horizontal = srcX != destX;
        boolean diagonal = Math.abs(srcX - destX) == Math.abs(srcY - destY);
    
        // Check for blocking pieces
        if (horizontal && vertical) { // Diagonal movement
            if (!diagonal) return false;
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
        } else if (vertical) { // Vertical movement
            int stepY = up ? -1 : 1;
            int currentY = srcY + stepY;
    
            while (currentY != destY) {
                if (board.getPiece(currentY, srcX) != null) {
                    return false;
                }
                currentY += stepY;
            }
        } else if (horizontal) { // Horizontal movement
            int stepX = right ? 1 : -1;
            int currentX = srcX + stepX;
    
            while (currentX != destX) {
                if (board.getPiece(srcY, currentX) != null) {
                    return false;
                }
                currentX += stepX;
            }
        } else {
            return false;
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
        return "Q";
    }
}
