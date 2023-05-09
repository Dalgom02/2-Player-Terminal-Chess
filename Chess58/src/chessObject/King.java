package chessObject;

import chess.Board;

public class King extends Piece {

    public King(boolean isBlack, int x, int y) {
        super(isBlack, x, y);
    }

    @Override
    public boolean isValidMove(int[] src, int[] dest, Board board) {
        int srcX = src[1];
        int srcY = src[0];
        int destX = dest[1];
        int destY = dest[0];

        Piece destPiece = board.getPiece(destY, destX);

        
        // Handles Castling
        if (isCastlingMove(src, dest, board)) {
            moveRookForCastling(src, dest, board);
            return true;
        }
        // End Castling
        

        // If source and destination are the same, return false
        if (srcX == destX && srcY == destY) {
            return false;
        }

        // If the destination piece is not null and is of the same color, return false
        if (destPiece != null && isBlack() == destPiece.isBlack()) {
            return false;
        }

        // If the vertical movement is more than 1, return false
        if (Math.abs(srcY - destY) > 1) {
            return false;
        }

        // If the horizontal movement is more than 1, return false
        if (Math.abs(srcX - destX) > 1) {
            return false;
        }

        // If the move is valid and not castling, update the firstMove status
        if (!board.isChecking(isBlack())) {
            setHasMoved(true);
        }

        return true;
    }


/// Castling 
    private boolean isCastlingMove(int[] src, int[] dest, Board board) {
        int srcX = src[0];
        int srcY = src[1];
        int destX = dest[0];
        int destY = dest[1];
    
        if (!getHasMoved() && srcY == destY && !board.isChecking(isBlack())) {
            int row = isBlack() ? 7 : 0;
            if (destX == 6 && board.isEmpty(5, row) && board.isEmpty(6, row)
                    && !board.isAttacked(5, row, !isBlack()) && !board.isAttacked(6, row, !isBlack())) { // King-side castling
                Piece rook = board.getPiece(7, row);
                if (rook != null && rook.getType().equals("R") && !rook.getHasMoved()) {
                    return true;
                }
            } else if (destX == 2 && board.isEmpty(1, row) && board.isEmpty(2, row) && board.isEmpty(3, row)
                    && !board.isAttacked(1, row, !isBlack()) && !board.isAttacked(2, row, !isBlack())) { // Queen-side castling
                Piece rook = board.getPiece(0, row);
                if (rook != null && rook.getType().equals("R") && !rook.getHasMoved()) {
                    return true;
                }
            }
        }
        return false;
    }

        private void moveRookForCastling(int[] src, int[] dest, Board board) {
        int srcY = src[1];
        int destX = dest[0];
        int row = isBlack() ? 7 : 0;
    
        if (destX == 6) { // King-side castling
            board.move(new int[] {7, row}, new int[] {5, row}, isBlack());
        } else if (destX == 2) { // Queen-side castling
            board.move(new int[] {0, row}, new int[] {3, row}, isBlack());
        }
    }
    


    @Override
    public String getType() {
        return "K";
    }
}
