package model;


public class Pawn extends Piece {

    private static final char IDENTIFIER = 'P';

    private boolean firstMove;

    public Pawn(boolean pieceColor,int posX,int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
        this.firstMove = true;

    }


    @Override
    public boolean makeMove(int destX, int destY) {
        if (pieceColor == true) {
            return pawnMoveWhite(destX,destY);
        } else {
            return pawnMoveBlack(destX,destY);
        }
    }

    private boolean pawnMoveBlack(int destX, int destY) {
        if (destY - posY > -3 && firstMove && destX == posX && destY - posY < 0) {
            firstMove = false;
            return forwardMove(destX,destY);
        } else if (destX == posX && destY - posY == -1) {
            forwardMove(destX,destY);
        } else if ((destX - posX == 1 || destX - posX == -1) && destY - posY == -1) {
            if (bd.getTile(destX, destY) != null && destY == 0) {
                moveDiagonally(destX, destY);
                Piece p = new Queen(pieceColor, destX, destY, bd);
                bd.setTile(p, destX, destY);
                return true;
            }
        }
        return false;
    }

    private boolean pawnMoveWhite(int destX, int destY) {
        if (destY - posY < 3 && firstMove && destX == posX && destY - posY > 0) {
            firstMove = false;
            return forwardMove(destX,destY);
        } else if (destX == posX && destY - posY == 1) {
            return forwardMove(destX,destY);
        } else if ((destX - posX == 1 || destX - posX == -1) && destY - posY == 1) {
            moveDiagonally(destX, destY);
            Piece p = new Queen(pieceColor, destX, destY, bd);
            bd.setTile(p, destX, destY);
            return true;
        }
        return false;
    }

    //
    public boolean forwardMove(int destX,int destY) {
        if (bd.getTile(destX,destY) != null) {
            return false;
        } else if  (destY == 0 && pieceColor == true && moveInStraightLine(destX,destY) == true) {
            Piece p = new Queen(pieceColor, destX, destY, bd);
            bd.setTile(p, destX, destY);
            return true;
        } else if  (destY == 7 && pieceColor == false && moveInStraightLine(destX,destY) == true) {
            Piece p = new Queen(pieceColor, destX, destY, bd);
            bd.setTile(p, destX, destY);
            return true;
        } else {
            return moveInStraightLine(destX, destY);
        }
    }

    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }


}