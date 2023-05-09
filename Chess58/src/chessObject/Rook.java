package chessObject;

import chess.Board;

public class Rook extends Piece {

    public Rook(boolean isBlack, int x, int y) {
        super(isBlack, x, y);
    }

    @Override
    public boolean isValidMove(int[] src, int[] dest, Board board) {
        int srcX = src[1];
        int srcY = src[0];
        int destX = dest[1];
        int destY = dest[0];
    
        boolean right = srcX < destX;
        boolean up = srcY > destY;
        boolean vertical = srcY != destY;
        boolean horizontal = srcX != destX;
    
        // Check for blocking pieces
        if (vertical && !horizontal) { // Vertical movement
            int stepY = up ? -1 : 1;
            int currentY = srcY + stepY;
    
            while (currentY != destY) {
                if (board.getPiece(currentY, srcX) != null) {
                    return false;
                }
                currentY += stepY;
            }
        } else if (horizontal && !vertical) { // Horizontal movement
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
    
        Piece destPiece = board.getPiece(destY, destX);
    
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
        return "R";
    }
}
