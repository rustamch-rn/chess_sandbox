package model.pieces;

import model.game.Board;

// Represents a rook
public class Rook extends Piece {

    private static final char IDENTIFIER = 'R'; // Identifier of rook used by external methods to identify a piece

    //EFFECTS: Constructs a new Rook
    public Rook(boolean pieceColor, int posX, int posY, Board bd) {
        super(pieceColor, posX, posY, bd, IDENTIFIER);
    }

    public Rook(boolean pieceColor, int posX, int posY) {
        super(pieceColor, posX, posY, IDENTIFIER);
    }

    //EFFECTS: Moves a rook, returns true if rook can be moved legally to the destination square
    @Override
    public boolean makeMove(int destX, int destY) {
        return moveInStraightLine(destX, destY);
    }
}
