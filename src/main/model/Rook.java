package model;


public class Rook extends Piece {

    private static final char IDENTIFIER = 'R';

    public Rook(boolean pieceColor, int posX,int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
    }

    @Override
    public boolean makeMove(int destX, int destY) {
        if (destX != posX && destY != posY) {
            return false;
        } else {
            return moveInStraightLine(destX, destY);
        }
    }

    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }
}
