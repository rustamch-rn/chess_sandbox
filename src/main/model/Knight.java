package model;


public class Knight extends Piece {

    private static final char IDENTIFIER = 'N';


    public Knight(boolean pieceColor,int posX,int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
    }

    @Override
    public boolean makeMove(int destX, int destY) {
        if (Math.abs(destY - posY) == 2 && Math.abs(destX - posX) == 1) {
            return bd.checkDestinationSquare(pieceColor, posX, posY, destX, destY);
        } else if (Math.abs(destY - posY) == 1 && Math.abs(destX - posX) == 2) {
            return bd.checkDestinationSquare(pieceColor, posX, posY, destX, destY);
        } else {
            return false;
        }
    }

    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }

}