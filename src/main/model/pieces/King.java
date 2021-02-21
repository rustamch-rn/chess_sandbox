package model.pieces;

import model.game.Board;

// Represents a King
public class King extends Piece {

    private static final char IDENTIFIER = 'K'; // Identifier of king used by external methods to identify a piece


    // EFFECTS: constructs a new King
    public King(boolean pieceColor, int posX, int posY, Board bd) {
        super(pieceColor,posX,posY,bd,IDENTIFIER);
    }

    public King(boolean pieceColor, int posX, int posY) {
        super(pieceColor,posX,posY,IDENTIFIER);
    }

    //MODIFIES: This
    //EFFECTS: Chooses how in which direction to move King, if given position is unreachable produces false
    @Override
    public boolean makeMove(int destX, int destY) {
        if (firstMove) {
            firstMove = false;
            return firstMove(destX,destY);
        } else  {
            return regularMove(destX,destY);
        }
    }

    //EFFECTS: Chooses in which way to move a king to reach a destination square, all the regular king move rules apply
    private boolean regularMove(int destX, int destY) {
        if (Math.abs(destY - posY) > 1  || Math.abs(destX - posX) > 1) {
            return false;
        } else if (destX == posX || destY == posY) {
            return moveInStraightLine(destX,destY);
        } else {
            return moveDiagonally(destX,destY);
        }
    }

    //EFFECTS: Chooses in which way to move a king to reach a destination square, all the regular king move rules apply
    //         and first move rules apply
    private boolean firstMove(int destX, int destY) {
        if (shortCastleReqs(destX,destY)) {
            return shortCastleMove(destX,destY);
        } else if (longCastleRqs(destX,destY)) {
            return longCastleMove(destX,destY);
        } else {
            return regularMove(destX,destY);
        }
    }

    //EFFECTS: Checks if the king can be long castled to reach destination square
    private boolean longCastleRqs(int destX, int destY) {
        if (destX > posX) {
            return false;
        }
        Piece q = bd.getTile(destX + 2,destY);
        Piece b = bd.getTile(destX + 1,destY);
        Piece n = bd.getTile(destX,destY);
        boolean attackSqK = bd.checkUnderAttack(pieceColor,posX,posY);
        boolean attackSqB = bd.checkUnderAttack(pieceColor,destX + 1,destY);
        boolean attackSqN = bd.checkUnderAttack(pieceColor,destX,destY);
        boolean attackSqQ = bd.checkUnderAttack(pieceColor,destX + 2,destY);
        Piece rook = bd.getTile(destX - 1,destY);
        if (destX != posX - 3 || destY  != posY) {
            return false;
        } else if (n != null || q != null || b != null) {
            return false;
        } else if (rook == null) {
            return false;
        } else if (rook.getPieceColor() != pieceColor || rook.getIdentifier() != 'R') {
            return false;
        } else {
            return !attackSqK && !attackSqB && !attackSqN && !attackSqQ;
        }
    }

    //EFFECTS: Checks if the king can be short castled to reach destination square
    private boolean shortCastleReqs(int destX, int destY) {
        if (destX < posX) {
            return false;
        }
        Piece b = bd.getTile(destX - 1,destY);
        Piece n = bd.getTile(destX,destY);
        boolean attackSqK = bd.checkUnderAttack(pieceColor,posX,posY);
        boolean attackSqN = bd.checkUnderAttack(pieceColor,destX,destY);
        boolean attackSqB = bd.checkUnderAttack(pieceColor,destX - 1,destY);
        Piece rook = bd.getTile(destX + 1,destY);
        if (destX != posX + 2 || destY  != posY) {
            return false;
        } else if (b != null || n != null) {
            return false;
        } else if (rook == null) {
            return false;
        } else if (rook.getPieceColor() != pieceColor || rook.getIdentifier() != 'R') {
            return false;
        } else if (attackSqK || attackSqB || attackSqN) {
            return false;
        } else {
            return true;
        }
    }

    //MODIFIES: This, Board bd
    //EFFECTS: Castles king to the right
    private boolean shortCastleMove(int destX,int destY) {
        moveInStraightLine(destX,destY);
        bd.removePiece(destX + 1,destY);
        Piece r = new Rook(pieceColor,destX - 1,destY,bd);
        bd.setTile(r,destX - 1,destY);
        return true;
    }

    //MODIFIES: This, Board bd
    //EFFECTS: Castles king to the left
    private boolean longCastleMove(int destX,int destY) {
        moveInStraightLine(destX + 1,destY);
        bd.removePiece(destX - 1,destY);
        Piece r = new Rook(pieceColor,destX + 2,destY,bd);
        bd.setTile(r,destX + 2,destY);
        return true;
    }

}