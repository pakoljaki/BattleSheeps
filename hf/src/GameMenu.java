/*package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JMenuBar {
    private final GameController gameController;

    public GameMenu(GameController gameController) {
        this.gameController = gameController;

        JMenu fileMenu = new JMenu("Menu");
        add(fileMenu);

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem quitItem = new JMenuItem("Quit");

        fileMenu.add(newGameItem);
        fileMenu.add(saveGameItem);
        fileMenu.add(loadGameItem);
        fileMenu.add(quitItem);

        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlayerSelection();
            }
        });

        saveGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.saveGame();
            }
        });

        loadGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.loadGame();
            }
        });

        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void showPlayerSelection() {
        JFrame playerSelectionFrame = new JFrame("Player Selection");
        playerSelectionFrame.setSize(300, 150);

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Select number of real players:");
        JComboBox<Integer> playerComboBox = new JComboBox<>(new Integer[]{1, 2});

        JButton startGameButton = new JButton("Start Game");

        panel.add(label);
        panel.add(playerComboBox);
        panel.add(startGameButton);

        playerSelectionFrame.add(panel);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer selectedPlayers = (Integer) playerComboBox.getSelectedItem();
                gameController.startNewGame(selectedPlayers);
                playerSelectionFrame.dispose();
            }
        });

        playerSelectionFrame.setVisible(true);
    }
}*/
