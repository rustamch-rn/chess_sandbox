package model;

public class Player {


    private boolean pieceColor;
    private Board playingBoard;
    private String playerName;

    public Player(boolean pieceColor,Board playingBoard,String playerName) {
        this.pieceColor = pieceColor;
        this.playingBoard = playingBoard;
        this.playerName = playerName;

    }

    public String getName() {
        return playerName;
    }

    public boolean getPieceColor() {
        return pieceColor;
    }

    public boolean checkPieceSelection(int origSquare) {
        int x = origSquare % 8;
        int y = origSquare / 8;
        return playingBoard.checkTile(pieceColor,x,y);

    }

    public boolean makeMove(int origSquare,int destSquare) {
        int origX = origSquare % 8;
        int origY = origSquare / 8;
        int destX = destSquare % 8;
        int destY = destSquare / 8;
        if (playingBoard.checkMoveRules(origX,origY,destX,destY)) {
            playingBoard.printBoard();
            return true;
        }
        return false;
    }
}
