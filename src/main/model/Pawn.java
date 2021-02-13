package model;


public class Pawn extends Piece {

    private static final char IDENTIFIER = 'P';

    private boolean firstMove;

    public Pawn(boolean pieceColor,int posX,int posY,Board bd) {
        super(pieceColor,posX,posY,bd);
        this.firstMove = true;

    }


    //EFFECTS: Checks color of the pawn and moves it accordingly
    @Override
    public boolean makeMove(int destX, int destY) {
        if (pieceColor == true) {
            return pawnMoveWhite(destX,destY);
        } else {
            return pawnMoveBlack(destX,destY);
        }
    }

    //MODIFIES: This
    //EFFECTS: Checks white pawn move rules, if it is a first move of a given pawn allows move 2 squares forward,
    //         otherwise checks for 1 square moves, if given destination square is unreachable produces false
    private boolean pawnMoveBlack(int destX, int destY) {
        if (destY - posY > -3 && firstMove && destX == posX && destY - posY < 0) {
            firstMove = false;
            return forwardMove(destX,destY);
        } else if (destX == posX && destY - posY == -1) {
            return forwardMove(destX,destY);
        } else if (Math.abs(destY - posY) == 1 && destY - posY == -1) {
            return pawnTakes(destX,destY);
        }
        return false;
    }

    //MODIFIES: This
    //EFFECTS: Checks black pawn move rules, if it is a first move of a given pawn allows move 2 squares forward,
    //         otherwise checks for 1 square moves, if given destination square is unreachable produces false
    private boolean pawnMoveWhite(int destX, int destY) {
        if (destY - posY < 3 && firstMove && destX == posX && destY - posY > 0) {
            firstMove = false;
            return forwardMove(destX,destY);
        } else if (destX == posX && destY - posY == 1) {
            return forwardMove(destX,destY);
        } else if (Math.abs(destY - posY) == 1 && destY - posY == 1) {
            return pawnTakes(destX,destY);
        }
        return false;
    }

    //MODIFIES: Board bd
    //EFFECTS: Checks diagonal move rules if given pawn move satisfies rules for piece promotion, creates a queen on
    //         a destination square
    private boolean pawnTakes(int destX, int destY) {
        if (bd.getTile(destX,destY) == null) {
            return false;
        } else if  (destY == 0) {
            Piece p = new Queen(false, destX, destY, bd);
            bd.takePiece(destX,destY);
            bd.setTile(p, destX, destY);
            return true;
        } else if  (destY == 7) {
            Piece p = new Queen(true, destX, destY, bd);
            bd.setTile(p, destX, destY);
            return true;
        }
        return moveDiagonally(destX,destY);
    }

    //MODIFIES: Board bd
    //EFFECTS: Checks forward move rules if given pawn move satisfies rules for piece promotion, creates a queen on
    //         a destination square
    public boolean forwardMove(int destX,int destY) {
        if (bd.getTile(destX,destY) != null) {
            return false;
        } else if  (destY == 0) {
            Piece p = new Queen(false, destX, destY, bd);
            bd.setTile(p, destX, destY);
            return true;
        } else if  (destY == 7) {
            Piece p = new Queen(true, destX, destY, bd);
            bd.setTile(p, destX, destY);
            return true;
        } else {
            return moveInStraightLine(destX, destY);
        }
    }

    //EFFECTS: Returns identifier of a given piece
    @Override
    public char getIdentifier() {
        return IDENTIFIER;
    }


}