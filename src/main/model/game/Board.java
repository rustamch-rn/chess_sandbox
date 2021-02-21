package model.game;

import model.pieces.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a chess game board
public class Board implements Writable {

    private boolean isOver; // Status of the game
    private final Piece[][] tiles; // All the tiles on the board

    public Board() {
        this.tiles = new Piece[8][8]; //
        isOver = false;
        addPieces();
    }

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

    //MODIFIES: This
    //EFFECTS: Sets the current game to be over
    public void setOver() {
        isOver = true;
    }

    // EFFECTS : checks if there is a piece of a given color on the given square
    public boolean checkTile(boolean playerPieceColor, int posX, int posY) {
        if (tiles[posY][posX] != null) {
            Piece piece = tiles[posY][posX];
            return piece.getPieceColor() == playerPieceColor;
        }
        return false;
    }

    // REQUIRES: starting position should contain a piece of player's color
    // MODIFIES: This
    // EFFECTS : Produces false if move is illegal, otherwise makes a move and produces true
    public boolean checkMoveRules(int origX, int origY, int destX, int destY) {
        Piece p = tiles[origY][origX];
        if (p.makeMove(destX, destY)) {
            removePiece(origX, origY);
            return true;
        }
        return false;
    }

    //REQUIRES: The original square should contain a piece.
    //EFFECTS: Checks if something is restricting movement of the figure vertically forward,
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean verticalForwardMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY + count <= 7) {
            Piece currentSquare = tiles[origY + count][origX];
            if (origY + count == destY) {
                return checkDestinationSquare(pieceColor, origX, origY, destX, destY);
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
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean verticalBackwardMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY - count >= 0) {
            Piece currentSquare = tiles[origY - count][origX];
            if (origY - count == destY) {
                return checkDestinationSquare(pieceColor, origX, origY, destX, destY);
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
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean horizontalRightwardMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origX + count <= 7) {
            Piece currentSquare = tiles[origY][origX + count];
            if (origX + count == destX) {
                return checkDestinationSquare(pieceColor, origX, origY, destX, destY);
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
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean horizontalLeftwardMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origX - count >= 0) {
            Piece currentSquare = tiles[origY][origX - count];
            if (origX - count == destX) {
                return checkDestinationSquare(pieceColor, origX, origY, destX, destY);
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
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean diagonalUpRightMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY + count <= 7 && origX + count <= 7) {
            Piece currentSquare = tiles[origY + count][origX + count];
            if (origY + count == destY && origX + count == destX) {
                return checkDestinationSquare(pieceColor, origX, origY, destX, destY);
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
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean diagonalUpLeftMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY + count <= 7 && origX - count >= 0) {
            Piece currentSquare = tiles[origY + count][origX - count];
            if (origY + count == destY && origX - count == destX) {
                return checkDestinationSquare(pieceColor, origX, origY, destX, destY);
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
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean diagonalDownRightMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY - count >= 0 && origX + count <= 7) {
            Piece currentSquare = tiles[origY - count][origX + count];
            if (origY - count == destY && origX + count == destX) {
                return checkDestinationSquare(pieceColor, origX, origY, destX, destY);
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
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean diagonalDownLeftMove(boolean pieceColor, int origX, int origY, int destX, int destY) {
        int count = 1;
        while (origY - count >= 0 && origX - count >= 0) {
            Piece currentSquare = tiles[origY - count][origX - count];
            if (origY - count == destY && origX - count == destX) {
                return checkDestinationSquare(pieceColor, origX, origY, destX, destY);
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
    public boolean checkDestinationSquare(boolean pieceColor, int origX, int origY, int destX, int destY) {
        Piece destSquare = tiles[destY][destX];
        Piece selectedPiece = tiles[origY][origX];
        if (destSquare == null) {
            selectedPiece.setPosX(destX);
            selectedPiece.setPosY(destY);
            tiles[destY][destX] = selectedPiece;
            return true;
        } else if (destSquare.getPieceColor() == pieceColor) {
            return false;
        } else {
            takePiece(destX, destY);
            selectedPiece.setPosX(destX);
            selectedPiece.setPosY(destY);
            tiles[destY][destX] = selectedPiece;
            return true;
        }

    }


    //REQUIRES: There is a piece on a given square
    //MODIFIES: This
    //EFFECTS: Removes a piece from the board, if the piece was a King sets game played on this board to be over
    public void takePiece(int posX, int posY) {
        Piece p = tiles[posY][posX];
        if ('K' == p.getIdentifier()) {
            setOver();
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

    // EFFECTS: Prints a basic visual representation of the board
    public void printBoard() {
        int row = 1;
        for (int i = 7; i >= 0; i--) {
            System.out.println();
            System.out.print((row + i) + " ");
            for (int j = 0; j <= 7; j++) {
                Piece p = getTile(j, i);
                if (p == null) {
                    System.out.print("____|");
                } else if (p.getPieceColor()) {
                    char id = p.getIdentifier();
                    printWhitePieces(id);
                } else {
                    char id = p.getIdentifier();
                    printBlackPieces(id);
                }
            }
        }
        System.out.println();
        printLetters();
        System.out.println();
    }

    // EFFECTS: Prints letter for a responding column on the board
    private void printLetters() {
        System.out.print(" ");
        String letters = "ABCDEFGH";
        for (int i = 0; i <= 7; i++) {
            System.out.print("  " + letters.charAt(i) + "  ");
        }
    }

    //REQUIRES: The id of piece should be an id of a white piece
    //EFFECTS: Produces texts that represent a figure which depend a type of piece
    private void printWhitePieces(char id) {
        switch (id) {
            case 'K':
                System.out.print("_WK_|");
                break;
            case 'Q':
                System.out.print("_WQ_|");
                break;
            case 'N':
                System.out.print("_WN_|");
                break;
            case 'B':
                System.out.print("_WB_|");
                break;
            case 'R':
                System.out.print("_WR_|");
                break;
            default:
                System.out.print("_WP_|");
        }
    }

    //REQUIRES: The id of piece should be an id of a black piece
    //EFFECTS: Produces texts that represent a figure which depend a type of piece
    private void printBlackPieces(char id) {
        switch (id) {
            case 'K':
                System.out.print("_BK_|");
                break;
            case 'Q':
                System.out.print("_BQ_|");
                break;
            case 'N':
                System.out.print("_BN_|");
                break;
            case 'B':
                System.out.print("_BB_|");
                break;
            case 'R':
                System.out.print("_BR_|");
                break;
            default:
                System.out.print("_BP_|");
        }
    }

    //MODIFIES: This
    //EFFECTS: Produces true if the given square is under attack, false otherwise
    public boolean checkUnderAttack(boolean pieceColor, int destX, int destY) {
        for (Piece[] row : tiles) {
            for (Piece p : row) {
                if (p != null) {
                    int origX = p.getPosX();
                    int origY = p.getPosY();
                    if (p.getPieceColor() != pieceColor && p.makeMove(destX, destY)) {
                        removePiece(destX, destY);
                        p.setPosX(origX);
                        p.setPosY(origY);
                        if (p.getIdentifier() == 'K') {
                            continue;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("status",isOver);
        json.put("pieces", piecesToJson());
        return json;
    }

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
        return  jsonArray;
    }

    public void piecesSetBoard() {
        for (Piece[] row : tiles) {
            for (Piece p : row) {
                if (p == null) {
                    continue;
                } else {
                    p.setBd(this);
                }
            }
        }
    }
}
