package model.pieces;

import model.game.Board;
import org.json.JSONObject;
import persistence.Writable;

// Abstract template for a piece of all the pieces
public abstract class Piece implements Writable {

    protected boolean pieceColor; // Color of this piece: true == White ; false == Black
    protected int posX; // X position of this piece on the board
    protected int posY; // Y position of this piece on the board
    protected Board bd; // Board on which current piece resides
    protected char identifier; // Identifier of bishop by external methods to identify a piece
    protected boolean firstMove; // Shows if it is a first time this piece is moved or not


    //EFFECTS: Constructs a new piece
    protected Piece(boolean pieceColor, int posX, int posY, Board bd, char identifier) {
        this.pieceColor = pieceColor;
        this.posX = posX;
        this.posY = posY;
        this.bd = bd;
        this.identifier = identifier;
        this.firstMove = true;
    }

    protected Piece(boolean pieceColor, int posX, int posY, char identifier) {
        this.pieceColor = pieceColor;
        this.posX = posX;
        this.posY = posY;
        this.identifier = identifier;
        this.firstMove = true;
    }


    public void setBd(Board bd) {
        this.bd = bd;
    }

    //REQUIRES: Position where piece should be moved, is valid and is on the board
    //EFFECTS: Moves this piece
    public abstract boolean makeMove(int destX, int destY);

    // EFFECTS: Returns identifier of the current piece
    public char getIdentifier() {
        return identifier;
    }

    //EFFECTS: Returns a color of the current piece
    public boolean getPieceColor() {
        return pieceColor;
    }

    //MODIFIES: This
    //EFFECTS: Changes X-positions of the current piece
    public void setPosX(int posX) {
        this.posX = posX;
    }

    //MODIFIES: This
    //EFFECTS: Changes X-positions of the current piece
    public void setPosY(int posY) {
        this.posY = posY;
    }

    // EFFECTS: Chooses in which direction diagonally should the piece move, if movement of the piece
    //          does not describe a straight line returns false
    protected boolean moveDiagonally(int destX, int destY) {
        if (Math.abs(destX - posX) != Math.abs(destY - posY)) {
            return false;
        } else if (destX > posX && destY > posY) {
            return bd.diagonalUpRightMove(posX, posY, destX, destY);
        } else if (destX < posX && destY > posY) {
            return bd.diagonalUpLeftMove(posX, posY, destX, destY);
        } else if (destX > posX) {
            return bd.diagonalDownRightMove(posX, posY, destX, destY);
        } else {
            return bd.diagonalDownLeftMove(posX, posY, destX, destY);
        }
    }

    // EFFECTS: Chooses in which direction vertically or horizontally should the piece move, if movement of the piece
    //          does not describe a straight line returns false
    protected boolean moveInStraightLine(int destX, int destY) {
        if (destX != posX && destY != posY) {
            return false;
        } else if (destY > posY) {
            return bd.verticalForwardMove(posX, posY, destX, destY);
        } else if (destY < posY) {
            return bd.verticalBackwardMove(pieceColor, posX, posY, destX, destY);
        } else if (destX > posX) {
            return bd.horizontalRightwardMove(posX, posY, destX, destY);
        } else {
            return bd.horizontalLeftwardMove(posX, posY, destX, destY);
        }
    }

    //EFFECTS: Returns X-position of this piece
    public int getPosX() {
        return posX;
    }

    //EFFECTS: Returns Y-position of this piece
    public int getPosY() {
        return posY;
    }

    // EFFECTS: Transforms a piece to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieceColor", pieceColor);
        jsonObject.put("posX", posX);
        jsonObject.put("posY", posY);
        jsonObject.put("firstMove", firstMove);
        jsonObject.put("identifier", identifier);
        return jsonObject;
    }

    // EFFECTS: Returns if it is the first move or not
    public boolean getFirstMove() {
        return firstMove;
    }

    // EFFECTS: Sets the first move value to be either true or false
    public void setFirstMove(boolean b) {
        this.firstMove = b;
    }
}


