package model.pieces;


import model.game.Board;

// Represents a knight
public class Knight extends Piece {

    private static final char IDENTIFIER = 'N'; // Identifier of king used by external methods to identify a piece


    public Knight(boolean pieceColor, int posX, int posY, Board bd) {
        super(pieceColor,posX,posY,bd,IDENTIFIER);
    }

    public Knight(boolean pieceColor, int posX, int posY) {
        super(pieceColor,posX,posY,IDENTIFIER);
    }

    //EFFECTS: Moves a Knight to destination square if it there exists a legal move that would move knight there,
    //         returns false otherwise
    @Override
    public boolean makeMove(int destX, int destY) {
        if (Math.abs(destY - posY) == 2 && Math.abs(destX - posX) == 1) {
            bd.placePieceOnNewSquare(posX, posY, destX, destY);
            return true;
        } else if (Math.abs(destY - posY) == 1 && Math.abs(destX - posX) == 2) {
            bd.placePieceOnNewSquare(posX, posY, destX, destY);
            return true;
        } else {
            return false;
        }
    }
}