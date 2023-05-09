package chessObject;
import chess.Board;


// Define the Piece class as an abstract class, since it will be extended by more specific classes
public abstract class Piece {

    // Instance variables to store whether the piece is black or white, and its position on the board
    private boolean isBlack;
    private int x;
    private int y;
    private boolean isFirstMove;
    private boolean hasMoved;


    // Constructor to initialize the instance variables
    public Piece(boolean isBlack, int x, int y) {
        this.isBlack = isBlack;
        this.x = x;
        this.y = y;
        this.isFirstMove = true;
        this.hasMoved = false;

    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    // Getter and setter methods for the isBlack instance variable
    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean isBlack) {
        this.isBlack = isBlack;
    }

    // Getter and setter methods for the x and y instance variables
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Method to check if a move is valid, to be overridden by subclasses
    public abstract boolean isValidMove(int[] src, int[] dest, Board board);


    // Method to get the name of the piece, consisting of its color and type
    public String getName() {
        // Determine the color of the piece and get its type
        String color = isBlack ? "b" : "w";
        String type = getType();
        // Return the name of the piece as a string
        return color + type;
    }

    // Abstract method to get the type of the piece, to be overridden by subclasses
    public abstract String getType();


        // Getter and setter methods for hasMoved
        public boolean getHasMoved() {
            return hasMoved;
        }
    
        public void setHasMoved(boolean hasMoved) {
            this.hasMoved = hasMoved;
        }

       
    
}

