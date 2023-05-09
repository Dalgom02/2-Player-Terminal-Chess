package chess;


import chessObject.*;

public class Board {
    private Piece[][] board;
    private boolean checking = false;
    int amountOfMoves;

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    public Piece getPiece(int x, int y) {
        return board[y][x];
    }

    public void setPiece(Piece piece, int x, int y) {
        board[y][x] = piece;
    }

    public boolean isEmpty(int x, int y) {
        return board[y][x] == null;
    }
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public void initializeBoard() {
         //Black side (its at the top)
        board[0][0] = new Rook(false, 0, 0);
        board[0][1] = new Knight(false, 0, 1);
        board[0][2] = new Bishop(false, 0, 2);
        board[0][3] = new Queen(false, 0, 3);
        board[0][4] = new King(false, 0, 4);
        board[0][5] = new Bishop(false, 0, 5);
        board[0][6] = new Knight(false, 0, 6);
        board[0][7] = new Rook(false, 0, 7);

        for(int i = 0; i < 8; i++){
            board[1][i] = new Pawn(false, 1, i);
        }

        //Unoccupied spaces are taken up by nulls
        for(int i = 2; i < 6; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = null;
            }
        }

        //White side
        for(int i = 0; i < 8; i++){
            board[6][i] = new Pawn(true, 6, i);
        }

        board[7][0] = new Rook(true, 7, 0);
        board[7][1] = new Knight(true, 7, 1);
        board[7][2] = new Bishop(true, 7, 2);
        board[7][3] = new Queen(true, 7, 3);
        board[7][4] = new King(true, 7, 4);
        board[7][5] = new Bishop(true, 7, 5);
        board[7][6] = new Knight(true, 7, 6);
        board[7][7] = new Rook(true, 7, 7);
    }

    public void printBoard() {
       for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j];
                if (p != null) {
                    System.out.print(p.getName() + " ");
                } else {
                    if ((i + j) % 2 == 0) {
                        System.out.print("## ");
                    } else {
                        System.out.print("   ");
                    }
                }
            }
            System.out.println();
        }
    
        System.out.print("  ");
        for (char c = 'a'; c <= 'h'; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();
        System.out.println();

    }

    public boolean move(int[] startCoord, int[] endCoord, boolean isBlackMove) {
        Piece thisPiece = getPiece(startCoord[0], startCoord[1]);
    
        if (thisPiece == null) {
            return false;
        } else if ((thisPiece.isBlack() && isBlackMove) || (!thisPiece.isBlack() && !isBlackMove)) {
            if (thisPiece.isValidMove(startCoord, endCoord, this)) {
                Piece thatPiece = getPiece(endCoord[0], endCoord[1]);
    
                // Temporarily move the piece
                setPiece(null, startCoord[0], startCoord[1]);
                setPiece(thisPiece, endCoord[0], endCoord[1]);
    
                boolean ownKingInCheck = isChecking(isBlackMove); // Check if the player's own king is in check
    
                // Revert the move
                setPiece(thisPiece, startCoord[0], startCoord[1]);
                setPiece(thatPiece, endCoord[0], endCoord[1]);
    
                if (!ownKingInCheck) {
                    // Update the board state with the chosen move
                    setPiece(null, startCoord[0], startCoord[1]);
                    setPiece(thisPiece, endCoord[0], endCoord[1]);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
    
    


    public boolean makeMove(String start, String end, boolean isWhiteTurn) {
        if (start.length() == 2 && end.length() == 2) {
            int startX = start.charAt(0) - 'a';
            int startY = start.charAt(1) - '1';
            int endX = end.charAt(0) - 'a';
            int endY = end.charAt(1) - '1';
    
            if (isValidCoordinate(startX, startY) && isValidCoordinate(endX, endY)) {
                int[] startCoord = {startX, startY};
                int[] endCoord = {endX, endY};
    
                return move(startCoord, endCoord, !isWhiteTurn);
            }
        }
        return false;
    }


    public int [] destLocation ( String end) {
        if (end.length() == 2) {
            int endX = end.charAt(0) - 'a';
            int endY = end.charAt(1) - '1';
    
            if (isValidCoordinate(endX, endY)) {
                int[] endLoc = {endX, endY};
    
                return endLoc;
            }
        }
        return null;
    }




public void matchingPiece(String p, boolean color, int dest[]){
    if(p.equals("Q")){
        Piece promo = new Queen(color, dest[0], dest[1]);
        setPiece(promo, dest[0], dest[1]);
       
    } 
    if(p.equals("R")){
        Piece promo = new Rook (color, dest[0], dest[1]);
        setPiece(promo, dest[0], dest[1]);
    }
    if(p.equals("N")){
        Piece promo = new Knight(color, dest[0], dest[1]);
        setPiece(promo, dest[0], dest[1]);
    }
    if(p.equals("B")){
        setPiece(null, dest[0],dest[1]);
        Piece promo = new Bishop(color, dest[0], dest[1]);
        setPiece(promo, dest[0], dest[1]);
    }
}

//wasd
public boolean isChecking(boolean isBlack) {
    int[] kingCoords = findKing(isBlack);
    if (kingCoords == null) {
        return false;
    }

    int kingX = kingCoords[0];
    int kingY = kingCoords[1];

    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Piece piece = getPiece(col, row);
            if (piece != null && piece.isBlack() != isBlack) {
                int[] src = {col, row};
                int[] dest = {kingX, kingY};
                if (piece.isValidMove(src, dest, this)) {
                    return true;
                }
            }
        }
    }

    return false;
}

    
    private int[] findKing(boolean isBlack) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(col, row);
                if (piece != null && piece.isBlack() == isBlack && piece.getType().equals("K")) {
                    return new int[]{col, row};
                }
            }
        }
        return null;
    }

    //checks if king is being attacked
    public boolean isAttacked(int x, int y, boolean isAttackerBlack) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(col, row);
                if (piece != null && piece.isBlack() == isAttackerBlack) {
                    int[] src = {col, row};
                    int[] dest = {x, y};
                    if (piece.isValidMove(src, dest, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean kingCanMove(int[] startCoord, int[] endCoord, boolean white) {
        if (endCoord[0] < 0 || endCoord[0] > 7 || endCoord[1] < 0 || endCoord[1] > 7)
            return false;
    
        Piece thisPiece = getPiece(startCoord[0], startCoord[1]);
    
        Piece thatPiece = getPiece(endCoord[0], endCoord[1]);
    
        if (thatPiece != null){
            return false;
        }
    
        checking = true;
        if (thisPiece.isValidMove(startCoord, endCoord, this)) {
            setPiece(null, startCoord[0], startCoord[1]);
            setPiece(thisPiece, endCoord[0], endCoord[1]);
            checking = false;
    
            boolean inCheck = isAttacked(endCoord[0], endCoord[1], !white);
    
            // Revert the move
            setPiece(thisPiece, startCoord[0], startCoord[1]);
            setPiece(thatPiece, endCoord[0], endCoord[1]);
    
            return !inCheck;
        } else {
            checking = false;
            return false;
        }
    }
    




    public boolean isCheckmate(boolean isBlack, Board board) {
        int[] kingCoord = board.findKing(isBlack);
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue; // Skip the current position of the king
                }
                int newRow = kingCoord[1] + rowOffset;
                int newCol = kingCoord[0] + colOffset;
                int[] newCoord = {newCol, newRow};
                if (board.isValidCoordinate(newCol, newRow) && board.getPiece(newCol, newRow) == null || board.getPiece(newCol, newRow).isBlack() != isBlack) {
                    Piece temp = board.getPiece(kingCoord[0], kingCoord[1]);
                    board.setPiece(null, kingCoord[0], kingCoord[1]);
                    board.setPiece(temp, newCol, newRow);
                    boolean stillInCheck = board.isChecking(isBlack);
                    board.setPiece(temp, kingCoord[0], kingCoord[1]);
                    board.setPiece(null, newCol, newRow);
                    if (!stillInCheck) {
                        return false;
                    }
                }
            }
        }
    
        // Check if any piece can capture the attacking piece or block its path
        if (board.isChecking(isBlack)) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Piece defendingPiece = board.getPiece(col, row);
                    if (defendingPiece != null && defendingPiece.isBlack() == isBlack) {
                        int[] src = {col, row};
                        for (int newRow = 0; newRow < 8; newRow++) {
                            for (int newCol = 0; newCol < 8; newCol++) {
                                int[] dest = {newCol, newRow};
                                if (defendingPiece.isValidMove(src, dest, board)) {
                                    // Temporarily move the defending piece
                                    Piece destPiece = board.getPiece(newCol, newRow);
                                    board.setPiece(null, col, row);
                                    board.setPiece(defendingPiece, newCol, newRow);
    
                                    // Check if the king is still in check
                                    boolean stillInCheck = board.isChecking(isBlack);
    
                                    // Revert the move
                                    board.setPiece(defendingPiece, col, row);
                                    board.setPiece(destPiece, newCol, newRow);
    
                                    if (!stillInCheck) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    
        return true; // No legal moves found, the king is in checkmate
    }
                
            
}
