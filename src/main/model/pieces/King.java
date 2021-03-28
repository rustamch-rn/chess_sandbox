package model.pieces;

import model.game.Board;

// Represents a King
public class King extends Piece {

    private static final char IDENTIFIER = 'K'; // Identifier of king used by external methods to identify a piece


    // EFFECTS: constructs a new King
    public King(boolean pieceColor, int posX, int posY, Board bd) {
        super(pieceColor, posX, posY, bd, IDENTIFIER);
    }

    public King(boolean pieceColor, int posX, int posY) {
        super(pieceColor, posX, posY, IDENTIFIER);
    }

    //MODIFIES: This
    //EFFECTS: Chooses how in which direction to move King, if given position is unreachable produces false
    @Override
    public boolean makeMove(int destX, int destY) {
        if (firstMove) {
            return firstMove(destX, destY);
        } else {
            return regularMove(destX, destY);
        }
    }

    //EFFECTS: Chooses in which way to move a king to reach a destination square, all the regular king move rules apply
    private boolean regularMove(int destX, int destY) {
        if (Math.abs(destY - posY) > 1 || Math.abs(destX - posX) > 1) {
            return false;
        } else if (destX == posX ^ destY == posY) {
            return moveInStraightLine(destX, destY);
        } else {
            return moveDiagonally(destX, destY);
        }
    }

    //EFFECTS: Chooses in which way to move a king to reach a destination square, all the regular king move rules apply
    //         and first move rules apply
    private boolean firstMove(int destX, int destY) {
        if (shortCastleReqs(destX, destY)) {
            return shortCastleMove(destX, destY);
        } else if (longCastleRqs(destX, destY)) {
            return longCastleMove(destX, destY);
        } else {
            return regularMove(destX, destY);
        }
    }

    //EFFECTS: Checks if the king can be long castled to reach destination square
    private boolean longCastleRqs(int destX, int destY) {
        if (destY != posY || Math.abs(destX - posX) != 2) {
            return false;
        }
        Piece q = bd.getTile(posX - 1, destY);
        Piece b = bd.getTile(posX - 2, destY);
        Piece n = bd.getTile(posX - 3, destY);
        Piece rook = bd.getTile(posX - 4, destY);
        if (rook == null) {
            return false;
        } else if (rook.getPieceColor() != pieceColor || rook.getIdentifier() != 'R' || !rook.getFirstMove()) {
            return false;
        } else if (q != null || b != null || n != null) {
            return false;
        } else {
            boolean attackSqK = bd.checkUnderAttack(pieceColor, posX, posY);
            boolean attackSqB = bd.checkUnderAttack(pieceColor, posX - 2, destY);
            boolean attackSqQ = bd.checkUnderAttack(pieceColor, posX - 1, destY);
            return !attackSqK && !attackSqB && !attackSqQ;
        }
    }

    //EFFECTS: Checks if the king can be short castled to reach destination square
    private boolean shortCastleReqs(int destX, int destY) {
        if (destY != posY || Math.abs(destX - posX) != 2) {
            return false;
        }
        Piece b = bd.getTile(posX + 1, destY);
        Piece n = bd.getTile(posX + 2, destY);
        Piece rook = bd.getTile(posX + 3, destY);
        if (rook == null) {
            return false;
        } else if (rook.getPieceColor() != pieceColor || rook.getIdentifier() != 'R' || !rook.getFirstMove()) {
            return false;
        } else if (b != null || n != null) {
            return false;
        } else {
            boolean attackSqK = bd.checkUnderAttack(pieceColor, posX, posY);
            boolean attackSqN = bd.checkUnderAttack(pieceColor, posX + 2, destY);
            boolean attackSqB = bd.checkUnderAttack(pieceColor, posX + 1, destY);
            return !attackSqK && !attackSqN && !attackSqB;
        }
    }

    //MODIFIES: This, Board bd
    //EFFECTS: Castles king to the right
    private boolean shortCastleMove(int destX, int destY) {
        moveInStraightLine(destX, destY);
        bd.removePiece(destX + 1, destY);
        Piece r = new Rook(pieceColor, destX - 1, destY, bd);
        bd.setTile(r, destX - 1, destY);
        return true;
    }

    //MODIFIES: This, Board bd
    //EFFECTS: Castles king to the left
    private boolean longCastleMove(int destX, int destY) {
        moveInStraightLine(posX - 2, destY);
        bd.removePiece(posX - 4, destY);
        Piece r = new Rook(pieceColor, posX - 1, destY, bd);
        bd.setTile(r, posX - 1, destY);
        return true;
    }

}