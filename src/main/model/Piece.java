package model;

// Abstract template for a piece of all the pieces
public abstract class Piece {

    protected boolean pieceColor; // Color of this piece: true == White ; false == Black
    protected int posX; // X position of this piece on the board
    protected int posY; // Y position of this piece on the board
    protected Board bd; // Board on which current piece resides


    //EFFECTS: Constructs a new piece
    protected Piece(boolean pieceColor,int posX,int posY,Board bd) {
        this.pieceColor = pieceColor;
        this.posX = posX;
        this.posY = posY;
        this.bd = bd;
    }


    //REQUIRES: Position where piece should be moved, is valid and is on the board
    //EFFECTS: Moves this piece
    public abstract boolean makeMove(int destX, int destY);

    // EFFECTS: Returns identifier of the current piece
    public abstract char getIdentifier();

    //EFFECTS: Returns a color of the current piece
    protected boolean getPieceColor() {
        return pieceColor;
    }

    //MODIFIES: This
    //EFFECTS: Changes X-positions of the current piece
    protected  void setPosX(int posX) {
        this.posX = posX;
    }

    //MODIFIES: This
    //EFFECTS: Changes X-positions of the current piece
    protected void setPosY(int posY) {
        this.posY = posY;
    }

    // EFFECTS: Chooses in which direction diagonally should the piece move, if movement of the piece
    //          does not describe a straight line returns false
    protected boolean moveDiagonally(int destX, int destY) {
        if (Math.abs(destX - posX) != Math.abs(destY - posY)) {
            return false;
        } else if (destX > posX && destY > posY) {
            return bd.diagonalUpRightMove(pieceColor,posX,posY,destX,destY);
        } else if (destX < posX && destY > posY) {
            return bd.diagonalUpLeftMove(pieceColor,posX,posY,destX,destY);
        } else if (destX > posX) {
            return bd.diagonalDownRightMove(pieceColor,posX,posY,destX,destY);
        } else {
            return bd.diagonalDownLeftMove(pieceColor, posX, posY, destX, destY);
        }
    }

    // EFFECTS: Chooses in which direction vertically or horizontally should the piece move, if movement of the piece
    //          does not describe a straight line returns false
    protected boolean moveInStraightLine(int destX, int destY) {
        if (destX != posX && destY != posY) {
            return false;
        } else if (destY > posY) {
            return bd.verticalForwardMove(pieceColor,posX,posY,destX,destY);
        } else if (destY < posY) {
            return bd.verticalBackwardMove(pieceColor,posX,posY,destX,destY);
        } else  if (destX > posX) {
            return bd.horizontalRightwardMove(pieceColor,posX,posY,destX,destY);
        }  else {
            return bd.horizontalLeftwardMove(pieceColor, posX, posY, destX, destY);
        }
    }
}


