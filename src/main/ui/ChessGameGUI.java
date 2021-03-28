package ui;

import model.game.Game;
import model.pieces.Piece;
import ui.actionhandlers.SaveGameAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * The graphical user interface of the chess game
 */
public class ChessGameGUI extends JDialog implements MouseListener, MouseMotionListener {

    private static final int BOARD_WIDTH = 800; // Width of the frame
    private static final int BOARD_HEIGHT = 800; // Height of the board
    private static final int MENUBAR_HEIGHT = 20; // Height of the menu bar
    private static final int LABEL_HEIGHT = 40; // Height of the menu bar

    private Game gameDisplayed; // The game displayed on the screen
    private List<String> gameNames; // List the names of the currently existing games
    private JFrame parentFrame; // The frame of the of chess menu
    private JPanel chessBoard; // Panel that contains representation of chess board
    private boolean pieceSelected; // Shows if any piece is selected by either of the players
    private int pieceX; // X-coordinate of the selected piece
    private int pieceY; // Y-coordinate of the selected piece
    private JLayeredPane layeredPane; // Panel that contains all the details of the game
    private JMenuBar menuBar; // Menu bar that contains tall the buttons

    // EFFECTS : Constructs a new game
    public ChessGameGUI(Game g, List<String> gameNames, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.gameDisplayed = g;
        this.gameNames = gameNames;
        setupFrame();
        createMenuBar();
        createChessBoard();
        addTiles();
        addPieces();
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    // MODIFIES : This
    // EFFECTS : Creates a new chess board
    private void createChessBoard() {
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);
        chessBoard = new JPanel();
        chessBoard.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        JLabel jbl1 = new JLabel("<html><H1>" + gameDisplayed.getPlayer2().getName() + "</H1>");
        JLabel jbl2 = new JLabel("<html><H1>" + gameDisplayed.getPlayer1().getName() + "</H1>");
        layeredPane.add(jbl1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(jbl2, JLayeredPane.DEFAULT_LAYER);
        jbl1.setBounds(0, MENUBAR_HEIGHT, BOARD_WIDTH, LABEL_HEIGHT);
        jbl2.setBounds(0, LABEL_HEIGHT + BOARD_HEIGHT + MENUBAR_HEIGHT, BOARD_WIDTH, LABEL_HEIGHT);
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        chessBoard.setBounds(0, LABEL_HEIGHT + MENUBAR_HEIGHT, BOARD_WIDTH, BOARD_HEIGHT);
    }

    // MODIFIES: This
    // EFFECTS : Setups the frame of the chess game
    private void setupFrame() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parentFrame.setVisible(true);
            }
        });
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT + MENUBAR_HEIGHT + 2 * LABEL_HEIGHT));
        this.setResizable(false);
    }

    // MODIFIES : This
    // EFFECTS: Creates a menubar and adds the save button
    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, BOARD_WIDTH, MENUBAR_HEIGHT);
        getContentPane().add(menuBar);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveGameAction(gameDisplayed, gameNames));
        menuBar.add(saveButton);

    }


    // MODIFIES : This
    // EFFECTS : Adds piece images to the board
    private void addPieces() {
        for (int i = 0; i < 64; i++) {
            int x = i % 8;
            int y = (63 - i) / 8;
            Piece p = gameDisplayed.getPiece(y * 8 + x);
            if (p != null) {
                JLabel pieceImage;
                if (p.getPieceColor()) {
                    pieceImage = assignWhitePieceImage(p);
                } else {
                    pieceImage = assignBlackLabel(p);
                }
                JPanel tile = (JPanel) chessBoard.getComponent(i);
                tile.add(pieceImage);
            }
        }
    }

    // EFFECTS : Returns image for a given white piece based on its identifier
    private JLabel assignWhitePieceImage(Piece p) {
        char identifier = p.getIdentifier();
        switch (identifier) {
            case 'P':
                return new JLabel(new ImageIcon("data/images/white_pawn.png"));
            case 'N':
                return new JLabel(new ImageIcon("data/images/white_horse.png"));
            case 'R':
                return new JLabel(new ImageIcon("data/images/white_rook.png"));
            case 'B':
                return new JLabel(new ImageIcon("data/images/white_bishop.png"));
            case 'K':
                return new JLabel(new ImageIcon("data/images/white_king.png"));
            default:
                return new JLabel(new ImageIcon("data/images/white_queen.png"));
        }
    }

    // EFFECTS : Returns image for a given white piece based on its identifier
    private JLabel assignBlackLabel(Piece p) {
        char identifier = p.getIdentifier();
        switch (identifier) {
            case 'P':
                return new JLabel(new ImageIcon("data/images/black_pawn.png"));
            case 'N':
                return new JLabel(new ImageIcon("data/images/black_horse.png"));
            case 'R':
                return new JLabel(new ImageIcon("data/images/black_rook.png"));
            case 'B':
                return new JLabel(new ImageIcon("data/images/black_bishop.png"));
            case 'K':
                return new JLabel(new ImageIcon("data/images/black_king.png"));
            default:
                return new JLabel(new ImageIcon("data/images/black_queen.png"));
        }
    }


    // MODIFIES : This
    // EFFECTS :  Renders tiles on the board
    private void addTiles() {
        boolean startingTileColor = false;
        for (int j = 0; j < 8; j++) {
            if (j % 2 == 0) {
                startingTileColor = false;
            } else if (j % 2 == 1) {
                startingTileColor = true;
            }
            chooseSquareColor(startingTileColor);
        }
    }

    // MODIFIES : This
    // EFFECTS : Chooses the color for a given square
    private void chooseSquareColor(boolean startingTileColor) {
        for (int i = 0; i < 8; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);
            if (startingTileColor == true) {
                if (i % 2 == 0) {
                    square.setBackground(new Color(0, 130, 206));
                } else {
                    square.setBackground(new Color(255, 255, 255));
                }
            } else {
                if (i % 2 == 0) {
                    square.setBackground(new Color(255, 255, 255));
                } else {
                    square.setBackground(new Color(0, 130, 206));
                }
            }
        }
    }

    // EFFECTS : Re-renders chessboard image based on the game's current progress
    private void drawGame() {
        chessBoard.removeAll();
        chessBoard.repaint();
        addTiles();
        addPieces();
        chessBoard.revalidate();
    }


    // DO NOTHING!
    @Override
    public void mouseClicked(MouseEvent e) {
    }


    // MODIFIES : This
    // EFFECTS : Selects a piece based on mouse click position if it of player's color and there no piece has been
    //           selected already
    @Override
    public void mousePressed(MouseEvent e) {
        int y = ((MENUBAR_HEIGHT + BOARD_HEIGHT + LABEL_HEIGHT) - e.getY()) / 100;
        int x = e.getX() / 100;
        if (gameDisplayed.checkPieceSelection(x, y) == false || pieceSelected == true) {
            this.pieceSelected = false;
        } else {
            this.pieceSelected = true;
            this.pieceX = x;
            this.pieceY = y;
        }

    }

    // MODIFIES : This
    // EFFECTS : Moves a selected piece to mouse release position if it is a valid move
    @Override
    public void mouseReleased(MouseEvent e) {
        if (pieceSelected) {
            int y = ((MENUBAR_HEIGHT + BOARD_HEIGHT + LABEL_HEIGHT) - e.getY()) / 100;
            int x = e.getX() / 100;
            if (gameDisplayed.makeMove(pieceX, pieceY, x, y)) {
                if (gameDisplayed.getBoardStatus()) {
                    JOptionPane.showMessageDialog(null, gameDisplayed.producePlayerWonMessage());
                }
            }
        }
        pieceSelected = false;
        drawGame();
    }


    // DO NOTHING!
    @Override
    public void mouseEntered(MouseEvent e) {

    }


    // DO NOTHING!
    @Override
    public void mouseExited(MouseEvent e) {

    }


    // DO NOTHING!
    @Override
    public void mouseDragged(MouseEvent e) {

    }


    // DO NOTHING!
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
