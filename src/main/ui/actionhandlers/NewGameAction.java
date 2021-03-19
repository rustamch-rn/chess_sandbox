package ui.actionhandlers;

import model.game.Game;
import ui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Action listener for new game button
 */
public class NewGameAction implements ActionListener {

    private final List<String> gameNames; // Names of already existing games
    private JFrame parentFrame; // The frame of the main game menu


    // EFFECTS : Constructs a newGame action listener
    public NewGameAction(JFrame parentFrame, List<String> gameNames) {
        this.parentFrame = parentFrame;
        this.gameNames = gameNames;
    }

    // MODIFIES : This
    // EFFECTS : On activation creates a new Game with a given name, and player names
    @Override
    public void actionPerformed(ActionEvent e) {
        parentFrame.setVisible(false);
        String gameName = getGameName();
        String player1Name = getPlayer1Name();
        String player2Name = getPlayer2Name();
        Game g = new Game(gameName, player1Name, player2Name);
        new ChessGameGUI(g, gameNames, parentFrame);
    }

    // EFFECTS : Gets the name of the player with black pieces from user's input
    private String getPlayer2Name() {
        String playerName = JOptionPane.showInputDialog("What is the name of the player playing with black pieces?");
        return playerName;
    }

    // EFFECTS : Gets the name of the player with white pieces from user's input
    private String getPlayer1Name() {
        String playerName = JOptionPane.showInputDialog("What is the name of the player playing with white pieces?");
        return playerName;
    }

    // EFFECTS : Gets the name of the game from user's input, while the game with a given name exists already
    //           continues to require user's input
    private String getGameName() {
        String gameName = JOptionPane.showInputDialog("How do you want to name this game?");
        while (gameNames.contains(gameName)) {
            gameName = JOptionPane.showInputDialog("Game with this name exists already, please enter a different name");
        }
        return gameName;
    }
}
