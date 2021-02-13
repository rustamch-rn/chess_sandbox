package model;


public class Rook extends Piece {

    private static final char IDENTIFIER = 'R';

    public Rook(boolean pieceColor, int posX,int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
    }

    //EFFECTS: Moves a rook in a straight line
    @Override
    public boolean makeMove(int destX, int destY) {
        return moveInStraightLine(destX, destY);
    }

    //EFFECTS: Produces identifier of a given piece
    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }
}
