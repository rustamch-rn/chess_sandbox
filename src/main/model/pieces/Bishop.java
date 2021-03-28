package model.pieces;

import model.game.Board;

// Represents a Bishop
public class Bishop extends Piece {

    private static final char IDENTIFIER = 'B'; // Identifier of bishop by external methods to identify a piece

    //EFFECTS: Constructs a new Bishop
    public Bishop(boolean pieceColor, int posX, int posY, Board bd) {
        super(pieceColor, posX, posY, bd, IDENTIFIER);
    }

    //EFFECTS: Constructs a new Bishop
    public Bishop(boolean pieceColor, int posX, int posY) {
        super(pieceColor, posX, posY, IDENTIFIER);
    }


    //EFFECTS: Chooses how in which direction to move bishop
    @Override
    public boolean makeMove(int destX, int destY) {
        return moveDiagonally(destX, destY);
    }
}