package model;

public class Board {

    private boolean isOver;
    private Piece[][] tiles;

    public Board() {
        this.tiles = new Piece[8][8];
        addPieces();

    }

    private void addPieces() {
        for (int i = 0; i < 8; i++) {
            tiles[1][i] = new Pawn(true,i,1,this);
        }
        for (int i = 0; i < 8; i++) {
            tiles[6][i] = new Pawn(false,i,6,this);
        }
        tiles[0][0] = new Rook(true,0,0,this);
        tiles[0][7] = new Rook(true,7,0,this);
        tiles[7][0] = new Rook(false,0,7,this);
        tiles[7][7] = new Rook(false,7,7,this);
        tiles[0][1] = new Knight(true,1,0,this);
        tiles[0][6] = new Knight(true,6,0,this);
        tiles[7][1] = new Knight(false,1,7,this);
        tiles[7][6] = new Knight(false,6,7,this);
        tiles[0][2] = new Bishop(true,2,0,this);
        tiles[0][5] = new Bishop(true,5,0,this);
        tiles[7][2] = new Bishop(false,2,7,this);
        tiles[7][5] = new Bishop(false,5,7,this);
        tiles[0][3] = new Queen(true,3,0,this);
        tiles[0][4] = new King(true,4,0,this);
        tiles[7][3] = new Queen(false,3,7,this);
        tiles[7][4] = new King(false,4,7,this);
    }

    //MODIFIES: This
    //EFFECTS: Places given piece on a tile
    public void setTile(Piece p,int posX, int posY) {
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

    // REQUIRES: starting position should contain a piece of player's color
    // MODIFIES: This
    // EFFECTS : Produces false if move is illegal, otherwise makes a move and produces true
    public boolean checkMoveRules(int origX, int origY, int destX, int destY) {
        Piece p = tiles[origY][origX];
        if (p.makeMove(destX,destY)) {
            removePiece(origX,origY);
            return true;
        }
        return false;
    }

    //MODIFIES: This
    //EFFECTS: Sets the current game to be over
    public void setOver() {
        isOver = true;
    }

    //EFFECTS: Checks if something is restricting moving of the figure on vertically forward,
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean verticalForwardMove(boolean pieceColor,int origX,int origY,int destX,int destY) {
        int count = 1;
        while (origY + count <= 7) {
            Piece currentSquare = tiles[origY + count][origX];
            if (origY + count == destY) {
                return checkDestinationSquare(pieceColor,origX,origY,destX, destY);
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return  false;
    }


    //EFFECTS: Checks if something is restricting moving of the figure on vertically backward,
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean verticalBackwardMove(boolean pieceColor,int origX,int origY,int destX,int destY) {
        int count = 1;
        Piece currentSquare = tiles[origY - count][origX];
        while (origY - count >= 0) {
            if (origY - count == destY) {
                return checkDestinationSquare(pieceColor,origX,origY,destX, destY);
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return  false;
    }

    //EFFECTS: Checks if something is restricting moving of the figure on horizontally rightward ,
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean horizontalRightwardMove(boolean pieceColor,int origX,int origY,int destX,int destY) {
        int count = 1;
        while (origX + count <= 7) {
            Piece currentSquare = tiles[origY][origX + count];
            if (origX + count == destX) {
                return checkDestinationSquare(pieceColor,origX,origY,destX, destY);
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return  false;
    }

    //EFFECTS: Checks if something is restricting moving of the figure on vertically backward,
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean horizontalLeftwardMove(boolean pieceColor,int origX,int origY,int destX,int destY) {
        int count = 1;
        while (origX - count >= 0) {
            Piece currentSquare = tiles[origY][origX - count];
            if (origX - count == destX) {
                return checkDestinationSquare(pieceColor,origX,origY,destX, destY);
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return  false;
    }

    //EFFECTS: Checks if something is restricting moving of the figure on vertically backward,
    //         if nothing is restricting movement of the piece to destination square plays the move
    public boolean diagonalUpRightMove(boolean pieceColor,int origX,int origY,int destX,int destY) {
        int count = 1;
        while (origY + count <= 7 && origY + count <= 7) {
            Piece currentSquare = tiles[origY + count][origX + count];
            if (origY + count == destY && origX + count == destX) {
                return checkDestinationSquare(pieceColor,origX,origY,destX, destY);
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return  false;
    }

    public boolean diagonalUpLeftMove(boolean pieceColor,int origX,int origY,int destX,int destY) {
        int count = 1;
        while (origY + count <= 7 && origX - count >= 0) {
            Piece currentSquare = tiles[origY + count][origX - count];
            if (origY + count == destY && origX - count == destX) {
                return checkDestinationSquare(pieceColor,origX,origY,destX, destY);
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return  false;
    }

    public boolean diagonalDownRightMove(boolean pieceColor,int origX,int origY,int destX,int destY) {
        int count = 1;
        while (origY - count >= 0 && origX + count <= 7) {
            Piece currentSquare = tiles[origY - count][origX + count];
            if (origY - count == destY && origX + count == destX) {
                return checkDestinationSquare(pieceColor,origX,origY,destX, destY);
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return  false;
    }

    public boolean diagonalDownLeftMove(boolean pieceColor,int origX,int origY,int destX,int destY) {
        int count = 1;
        while (origY - count >= 0 && origX - count >= 0) {
            Piece currentSquare = tiles[origY - count][origX - count];
            if (origY - count == destY && origX - count == destX) {
                return checkDestinationSquare(pieceColor,origX,origY,destX, destY);
            }
            if (checkConflictingPieces(currentSquare)) {
                return false;
            }
            count++;
        }
        return  false;
    }


    public boolean checkConflictingPieces(Piece currentSquare) {
        return currentSquare != null;
    }

    //REQUIRES: There is a piece on a original square, and both original square and destination are on board
    //MODIFIES: This
    //EFFECTS: Checks if piece can occupy destination square, if there is enemy piece on the given square
    //         it is taken
    public boolean checkDestinationSquare(boolean pieceColor,int origX,int origY,int destX,int destY) {
        Piece destSquare = tiles[destY][destX];
        Piece selectedPiece = tiles[origY][origX];
        if (destSquare == null) {
            selectedPiece.setPosX(destX);
            selectedPiece.setPosY(destY);
            tiles[destY][destX] = selectedPiece;
            return true;
        } else if (destSquare.getPieceColor() == pieceColor) {
            return  false;
        } else {
            takePiece(destX,destY);
            selectedPiece.setPosX(destX);
            selectedPiece.setPosY(destY);
            tiles[destY][destX] = selectedPiece;
            return true;
        }

    }

    private void takePiece(int posX,int posY) {
        Piece p = tiles[posY][posX];
        if ('K' == p.getIdentifier()) {
            setOver();
        }
        removePiece(posX,posY);
    }

    public void removePiece(int posX, int posY) {
        tiles[posY][posX] = null;
    }


    public Piece getTile(int posX,int posY) {
        return tiles[posY][posX];
    }

    public void printBoard() {
        for (Piece[] row : tiles) {
            System.out.println("");
            for (Piece p : row) {
                if (p == null) {
                    System.out.print("___|");
                } else {
                    System.out.print("_x_|");
                }
            }

        }
        System.out.println("");
    }
}
