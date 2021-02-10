package model;


public class Bishop extends Piece {

    private static final char IDENTIFIER = 'B';

    //EFFECTS: Constructs a new Bishop
    public Bishop(boolean pieceColor, int posX, int posY, Board bd) {
        super(pieceColor,posX,posY,bd);
    }


    //EFFECTS: Chooses how in which direction to move bishop
    @Override
    public boolean makeMove(int destX, int destY) {
        return moveDiagonally(destX,destY);
    }

    //EFFECTS: Produces identifier of a given piece
    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }


}