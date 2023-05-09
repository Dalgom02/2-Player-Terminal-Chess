package chessObject;
import chess.Board;

public class Pawn extends Piece {

    private boolean enPassant;    
    // Constructor for Pawn, sets the initial position and color of the pawn
    public Pawn(boolean isBlack, int x, int y) {
        super(isBlack, x, y);
    }

    // Returns the type of the piece as a string, in this case "P" for Pawn
    @Override
    public String getType() {
        return "P";
    }

    // Checks if a given move for the pawn is valid
    @Override
    public boolean isValidMove(int[] src, int[] dest, Board board) {
        int srcX = src[1];
        int srcY = src[0];
        int destX = dest[1];
        int destY = dest[0];

        Piece destPiece = board.getPiece(destY, destX);
       

        if (destPiece == null && destX != srcX) {
            if (!isBlack()) {
                if (isFirstMove() && (destX == (srcX + 1) || destX== (srcX + 2))) {

                    if (destX == srcX + 2 && board.getPiece(destY, destX - 1) != null) {
                        return false;
                    }
        
                    if( destX == srcX + 1){
                        if(destY == srcY){
                            enPassant = false;
                            setFirstMove(false);
                            return true;
                        }
                        ///cannot do dia moves without opp
                        if((destX == srcX + 1 && (destY - srcY != -1 || destY - srcY != 1)) && destPiece == null ){
                            return false;
                        }
                    }
                    setFirstMove(false);
                    if(destX == srcX + 2){enPassant = true;}
                    return true;
                }
                
                ///cannot move dia without opp
                else if((destX == srcX + 1 && (destY == srcY -1 || destY == srcY + 1)) && destPiece == null ){
               
                    ///checking for en passant
                    if(enPassant){
                        if(destY == srcY + 1) {
                            if(!board.isEmpty(destY , destX - 1)){
                                board.setPiece(null, destY, destX - 1);
                                enPassant = false;
                                return true;
                            }
                            enPassant = false;
                        }else if(destY == srcY - 1){
                            if(!board.isEmpty(destY, destX -1)){
                                board.setPiece(null, destY, destX -1);
                                enPassant = false;
                                return true;
                            } 
                            enPassant = false;
                        }
                    }
                    return false;
                } 
                ///move forward one
                if (destX == (srcX + 1)) return true;
                return false;
            } else {
                if (isFirstMove() && (destX == (srcX - 1) || destX == (srcX - 2))) {
                    if (destX == srcX - 2 && board.getPiece(destY, destX + 1) != null) {
                        return false;
                    }

                    if( destX == srcX - 1){
                        if(destY == srcY){
                            enPassant = false;
                            setFirstMove(false);
                            return true;
                        }
                        /// do dia moves without opp
                        if((destX == srcX - 1 && (destY - srcY != -1 || destY - srcY != 1)) && destPiece == null ){
                            return false;
                        }
                    }

                    setFirstMove(false);
                    if(destX == srcX - 2) {enPassant = true;}
                    return true;
                }
                
                /// tries to move dia will fail w/o en passant
               else if((destX == srcX - 1 && (destY == srcY -1 || destY == srcY + 1)) && destPiece == null ){

                    //checking for en Passant BP
                    if(enPassant){
                        if(destY == srcY + 1) {
                            if(!board.isEmpty(destY, destX + 1)){
                                board.setPiece(null, destY, destX + 1);
                                enPassant = false;
                                return true;
                            }
                            enPassant = false;
                        }else if(destY == srcY -1){
                            if(!board.isEmpty(destY, destX + 1)){
                                board.setPiece(null, destY, destX + 1);
                                enPassant = false;
                                return true;
                            } 
                            enPassant = false;
                        }
                    }
                    return false;

                }
                if (destX == (srcX - 1)) return true;
                return false;
            }
        } 

        else {
            // ... Attacking and other conditions
            /// diagonal attacks
            if(destPiece != null){
            if(!isBlack()){
                if((destX == srcX + 1 && (destY == srcY -1 || destY == srcY + 1)))return true;

            }else{
                if((destX == srcX - 1 && (destY == srcY -1 || destY == srcY + 1)))return true;
            }
            }
        }

        // Shouldn't happen
        return false;
    }


}