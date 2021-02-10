package model;


public class Queen extends Piece {

    private static final char IDENTIFIER = 'Q';


    public Queen(boolean pieceColor,int posX, int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
    }


    @Override
    public boolean makeMove(int destX, int destY) {
        if (destX == posX || destY == posY) {
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