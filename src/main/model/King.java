package model;


public class King extends Piece {

    private static final char IDENTIFIER = 'K';


    // EFFECTS: constructs a new King
    public King(boolean pieceColor,int posX,int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
    }

    //EFFECTS: Chooses how in which direction to move King, if given position is unreachable produces false
    @Override
    public boolean makeMove(int destX, int destY) {
        if (Math.abs(destY - posY) > 1  || Math.abs(destX - posX) > 1) {
            return false;
        } else if (destX == posX || destY == posY) {
            return moveInStraightLine(destX,destY);
        } else {
            return moveDiagonally(destX,destY);
        }
    }

    //EFFECTS: Produces identifier of a given piece
    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }

}