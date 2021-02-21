package model.pieces;


import model.game.Board;

// Represents a Queen
public class Queen extends Piece {

    private static final char IDENTIFIER = 'Q'; // Identifier of queen used by external methods to identify a piece


    //EFFECTS: Constructs a new Queen
    public Queen(boolean pieceColor, int posX, int posY, Board bd) {
        super(pieceColor,posX,posY,bd,IDENTIFIER);
    }

    public Queen(boolean pieceColor, int posX, int posY) {
        super(pieceColor,posX,posY,IDENTIFIER);
    }


    //EFFECTS: Chooses how in which direction to move Queen and if queen can be moved legally to the destination square
    //         return true, false otherwise
    @Override
    public boolean makeMove(int destX, int destY) {
        if (destX == posX || destY == posY) {
            return moveInStraightLine(destX,destY);
        } else {
            return moveDiagonally(destX,destY);
        }
    }
}