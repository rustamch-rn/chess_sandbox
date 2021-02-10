package model;

import java.util.List;

public abstract class Piece {

    protected boolean pieceColor;
    protected int posX;
    protected int posY;
    protected Board bd;


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

    // EFFECTS: Chooses in which direction diagonally should the piece move
    protected boolean moveDiagonally(int destX, int destY) {
        if (destX > posX && destY > posY) {
            return bd.diagonalUpRightMove(pieceColor,posX,posY,destX,destY);
        } else if (destX < posX && destY > posY) {
            return bd.diagonalUpLeftMove(pieceColor,posX,posY,destX,destY);
        } else if (destX > posX &&  destY < posY) {
            return bd.diagonalDownRightMove(pieceColor,posX,posY,destX,destY);
        } else if (destX < posX && destY < posY) {
            return bd.diagonalDownLeftMove(pieceColor, posX, posY, destX, destY);
        } else {
            return false;
        }
    }

    // EFFECTS: Chooses in which direction vertically or horizontally should the piece move
    protected boolean moveInStraightLine(int destX, int destY) {
        if (destY > posY) {
            return bd.verticalForwardMove(pieceColor,posX,posY,destX,destY);
        } else if (destY < posY) {
            return bd.verticalBackwardMove(pieceColor,posX,posY,destX,destY);
        } else  if (destX > posX) {
            return bd.horizontalRightwardMove(pieceColor,posX,posY,destX,destY);
        }  else  if (destX < posX) {
            return bd.horizontalLeftwardMove(pieceColor, posX, posY, destX, destY);
        } else {
            return false;
        }
    }
}


