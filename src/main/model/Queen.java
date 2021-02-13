package model;


public class Queen extends Piece {

    private static final char IDENTIFIER = 'Q';


    //EFFECTS: Constructs a new Queen
    public Queen(boolean pieceColor,int posX, int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
    }


    //EFFECTS: Chooses how in which direction to move Queen
    @Override
    public boolean makeMove(int destX, int destY) {
        if (destX == posX || destY == posY) {
            return moveInStraightLine(destX,destY);
        } else {
            return moveDiagonally(destX,destY);
        }
    }

    //EFFECTS: Produces Queen's identifier
    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }



}