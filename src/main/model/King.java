package model;


public class King extends Piece {

    private static final char IDENTIFIER = 'K';

    public King(boolean pieceColor,int posX,int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
    }

    @Override
    public boolean makeMove(int destX, int destY) {
        if (destX - posX > 1 || destX - posX < - 1 || destY - posY > 1 || destX - posY < 1) {
            return false;
        } else if (destX == posX || destY == posY) {
            return moveInStraightLine(destX,destY);
        } else {
            return moveDiagonally(destX,destY);
        }
    }

    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }

}