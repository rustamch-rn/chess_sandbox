package model.game;

import model.pieces.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a chess game board
public class Board implements Writable {

    private boolean isOver; // Status of the game
    private final Piece[][] tiles; // All the tiles on the board


    // EFFECTS : Constructs a new board
    public Board() {
        this.tiles = new Piece[8][8];
        isOver = false;
        addPieces();
    }

    // EFFECTS : Constructs a new board
    public Board(Piece[][] tiles, boolean status) {
        this.tiles = tiles;
        this.isOver = status;
    }

    //MODIFIES: This
    //EFFECTS: Adds all the chess pieces to the board
    private void addPieces() {
        for (int i = 0; i < 8; i++) {
            tiles[1][i] = new Pawn(true, i, 1, this);
        }
        for (int i = 0; i < 8; i++) {
            tiles[6][i] = new Pawn(false, i, 6, this);
        }
        tiles[0][0] = new Rook(true, 0, 0, this);
        tiles[0][7] = new Rook(true, 7, 0, this);
        tiles[7][0] = new Rook(false, 0, 7, this);
        tiles[7][7] = new Rook(false, 7, 7, this);
        tiles[0][1] = new Knight(true, 1, 0, this);
        tiles[0][6] = new Knight(true, 6, 0, this);
        tiles[7][1] = new Knight(false, 1, 7, this);
        tiles[7][6] = new Knight(false, 6, 7, this);
        tiles[0][2] = new Bishop(true, 2, 0, this);
        tiles[0][5] = new Bishop(true, 5, 0, this);
        tiles[7][2] = new Bishop(false, 2, 7, this);
        tiles[7][5] = new Bishop(false, 5, 7, this);
        tiles[0][3] = new Queen(true, 3, 0, this);
        tiles[0][4] = new King(true, 4, 0, this);
        tiles[7][3] = new Queen(false, 3, 7, this);
        tiles[7][4] = new King(false, 4, 7, this);
    }

    //MODIFIES: This
    //EFFECTS: Places given piece on a tile
    public void setTile(Piece p, int posX, int posY) {
        tiles[posY][posX] = p;
    }

    //EFFECTS: Returns the current of the current game, played on this board.
    public boolean getStatus() {
        return isOver;
    }

    //EFFECTS: Produces a list with all tiles on this board
    public Piece[][] getTiles() {
        return tiles;
    }

    // EFFECTS : checks if there is a piece of a given color on the given square
    public boolean checkTile(boolean playerPieceColor, int posX, int posY) {
        if (tiles[posY][posX] != null) {
            Piece piece = tiles[posY][posX];
            return piece.getPieceColor() == playerPieceColor;
        }
        return false;
    }

    // REQUIRES : Selected square should be a valid square on the board that contains a piece
    // MODIFIES: This
    // EFFECTS : Produces false if move is illegal, otherwise makes a move and produces true
    public boolean checkMoveRules(boolean pieceColor, int origX, int origY, int destX, int destY) {
        Piece selectedPiece = tiles[origY][origX];
        Piece destSquare = tiles[destY][destX];
        if (selectedPiece.getPieceColor() != pieceColor) {
            return false;
        } else if (destSquare != null && destSquare.getPieceColor() == selectedPiece.getPieceColor()) {
            return false;
        } else if (selectedPiece.makeMove(destX, destY)) {
            placePieceOnNewSquare(origX, origY, destX, destY);
            removePiece(origX, origY);
            selectedPiece.setFirstMove(false);
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure vertically forward,
    //         if nothing is restricting movement of the piece returns true
    public boolean verticalForwardMove(int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY + count <= 7) {
            Piece currentSquare = tiles[origY + count][origX];
            if (origY + count == destY) {
                return true;
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return false;
    }


    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure vertically backward,
    //         if nothing is restricting movement of the piece returns true
    public boolean verticalBackwardMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY - count >= 0) {
            Piece currentSquare = tiles[origY - count][origX];
            if (origY - count == destY) {
                return true;
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return false;
    }

    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure horizontally rightward ,
    //         if nothing is restricting movement of the piece returns true
    public boolean horizontalRightwardMove(int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origX + count <= 7) {
            Piece currentSquare = tiles[origY][origX + count];
            if (origX + count == destX) {
                return true;
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return false;
    }

    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure horizontally rightward,
    //         if nothing is restricting movement of the piece returns true
    public boolean horizontalLeftwardMove(int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origX - count >= 0) {
            Piece currentSquare = tiles[origY][origX - count];
            if (origX - count == destX) {
                return true;
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return false;
    }

    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure diagonally to the top right corner,
    //         if nothing is restricting movement of the piece returns true
    public boolean diagonalUpRightMove(int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY + count <= 7 && origX + count <= 7) {
            Piece currentSquare = tiles[origY + count][origX + count];
            if (origY + count == destY && origX + count == destX) {
                return true;
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return false;
    }

    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure diagonally to the top left corner,
    //         if nothing is restricting movement of the piece returns true
    public boolean diagonalUpLeftMove(int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY + count <= 7 && origX - count >= 0) {
            Piece currentSquare = tiles[origY + count][origX - count];
            if (origY + count == destY && origX - count == destX) {
                return true;
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return false;
    }

    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure diagonally to the bottom right corner,
    //         if nothing is restricting movement of the piece returns true
    public boolean diagonalDownRightMove(int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY - count >= 0 && origX + count <= 7) {
            Piece currentSquare = tiles[origY - count][origX + count];
            if (origY - count == destY && origX + count == destX) {
                return true;
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return false;
    }

    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure diagonally to the bottom left corner,
    //         if nothing is restricting movement of the piece returns true
    public boolean diagonalDownLeftMove(int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY - count >= 0 && origX - count >= 0) {
            Piece currentSquare = tiles[origY - count][origX - count];
            if (origY - count == destY && origX - count == destX) {
                return true;
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return false;
    }


    //EFFECTS: Returns false if there is a piece on a given square already
    public boolean checkConflictingPieces(Piece currentSquare) {
        return currentSquare != null;
    }

    //REQUIRES: There is a piece on a original square, and both original square and destination are on board
    //MODIFIES: This
    //EFFECTS: Checks if piece can occupy destination square, if there is enemy piece on the given square
    //         it is taken
    public void placePieceOnNewSquare(int origX, int origY, int destX, int destY) {
        Piece destSquare = tiles[destY][destX];
        Piece selectedPiece = tiles[origY][origX];
        if (destSquare != null) {
            takePiece(destX, destY);
        }
        selectedPiece.setPosX(destX);
        selectedPiece.setPosY(destY);
        tiles[destY][destX] = selectedPiece;

    }


    //REQUIRES: There is a piece on a given square
    //MODIFIES: This
    //EFFECTS: Removes a piece from the board, if the piece was a King sets game played on this board to be over
    public void takePiece(int posX, int posY) {
        Piece p = tiles[posY][posX];
        if ('K' == p.getIdentifier()) {
            setStatus(true);
        }
        removePiece(posX, posY);
    }


    //MODIFIES: This
    //EFFECTS: removes a piece on a given tile
    public void removePiece(int posX, int posY) {
        tiles[posY][posX] = null;
    }

    //EFFECTS: Produces the piece on a given tile
    public Piece getTile(int posX, int posY) {
        return tiles[posY][posX];
    }


    //MODIFIES: This
    //EFFECTS: Produces true if the given square is under attack, false otherwise
    public boolean checkUnderAttack(boolean pieceColor, int destX, int destY) {
        Piece destSquare = getTile(destX, destY);
        for (Piece[] row : tiles) {
            for (Piece p : row) {
                if (p != null && p.getPieceColor() != pieceColor) {
                    int origX = p.getPosX();
                    int origY = p.getPosY();
                    boolean firstMove = p.getFirstMove();
                    if ((p.getIdentifier() != 'K' || !p.getFirstMove()) && p.makeMove(destX, destY)) {
                        setTile(destSquare, destX, destY);
                        p.setPosX(origX);
                        p.setPosY(origY);
                        setStatus(false);
                        p.setFirstMove(firstMove);
                        return true;
                    }
                    p.setFirstMove(firstMove);
                }
            }
        }
        return false;
    }

    public void setStatus(boolean b) {
        this.isOver = b;
    }


    // EFFECTS : Creates a json object from this game
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("status", isOver);
        json.put("pieces", piecesToJson());
        return json;
    }


    // EFFECTS : Creates a json array that contains information about all the tiles on the board
    private JSONArray piecesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Piece[] row : tiles) {
            for (Piece p : row) {
                if (p == null) {
                    jsonArray.put("null");
                } else {
                    jsonArray.put(p.toJson());
                }
            }
        }
        return jsonArray;
    }

    // EFFECTS: Sets the board of the piece to this board
    public void piecesSetBoard() {
        for (Piece[] row : tiles) {
            for (Piece p : row) {
                if (p != null) {
                    p.setBd(this);
                }
            }
        }
    }
}
