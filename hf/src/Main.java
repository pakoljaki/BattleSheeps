package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
    //static GameController gameController;
    static JFrame gameFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Battle Sheep Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("Choose an option:");
        panel.add(label);

        String[] options = {"New Game", "Load Game", "Quit"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        panel.add(comboBox);

        JButton startButton = new JButton("Choose Option");
        panel.add(startButton);

        gameFrame = new JFrame("Battle Sheep Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        gameFrame.setJMenuBar(menuBar);
        gameFrame.setVisible(true);
        gameFrame.setSize(800, 600);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                if (selectedOption.equals("New Game")) {
                    // Prompt for the number of real players
                    String[] playerOptions = {"1", "2"};
                    String playerChoice = (String) JOptionPane.showInputDialog(
                            null,
                            "Select the number of real players:",
                            "Number of Players",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            playerOptions,
                            playerOptions[0]);

                    if (playerChoice != null) {
                        int numberOfRealPlayers = Integer.parseInt(playerChoice);
                        
                        SwingUtilities.invokeLater(() -> {
                            /*JFrame gameFrame = new JFrame("Battle Sheep Game");
                            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            JMenuBar menuBar = new JMenuBar();
                            gameFrame.setJMenuBar(menuBar);
                            gameFrame.setVisible(true);
                            gameFrame.setSize(800, 600);*/
                            //JMenu fileMenu = new JMenu("Menu");
                            //gameFrame.add(menuBar);
                            BattleSheepGame game = new BattleSheepGame(null, numberOfRealPlayers);
                            game.initializeGUI();
                            HexagonalGridPanel UI = game.getGui();
                            if (UI == null) {
                                System.out.println("UI is null");
                            } 

                            SwingUtilities.invokeLater(() -> {
                                GameController controller = new GameController(game, game.getGui());
                                game.getGui().setGameController(controller);
                                gameFrame.add(game.getGui());
                            });
                            /*try {
                                // Sleep for 100 milliseconds (0.1 seconds)
                                Thread.sleep(1000);
                            } catch (InterruptedException ie) {
                                // Handle the exception if needed
                                ie.printStackTrace();
                            }*/
//
                            //gameFrame.add(game.getGui());
                    
                            //Create and add menu items
                            JMenuItem newGameItem = new JMenuItem("New Game");
                            JMenuItem saveGameItem = new JMenuItem("Save Game");
                            JMenuItem loadGameItem = new JMenuItem("Load Game");
                            JMenuItem quitItem = new JMenuItem("Quit");
                            menuBar.add(newGameItem);
                            menuBar.add(saveGameItem);
                            menuBar.add(loadGameItem);
                            menuBar.add(quitItem);
                            //Add action listeners
                            newGameItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    showPlayerSelection();
                                }
                            });
                            saveGameItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //get the name for the saved game and save
                                    SaveGame.saveGame(game);
                                }
                            });
                            
                            loadGameItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //list the names of saved files

                                    //load the chosen file from the combobox
                                    BattleSheepGame loadedGame = LoadGame.loadGame();
                                    GameController controller = createGameController(loadedGame);

                                }
                            });
                            quitItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.exit(0);
                                }
                            });
                        });
                        //frame.dispose();
                        System.out.println("Starting a new game with " + numberOfRealPlayers + " real player(s)...");
                    }
                    // Load a saved game
                } else if (selectedOption.equals("Load Game")) {
                    JFileChooser fileChooser = new JFileChooser("./savedGames"); // Set the default directory to 'saved'
                    File[] savedFiles = fileChooser.getCurrentDirectory().listFiles();

                    if (savedFiles != null && savedFiles.length > 0) {
                        String[] fileNames = new String[savedFiles.length];

                        for (int i = 0; i < savedFiles.length; i++) {
                            fileNames[i] = savedFiles[i].getName();
                        }
                        //List the files in a ComboBox
                        JComboBox<String> loadComboBox = new JComboBox<>(fileNames);
                        int option = JOptionPane.showOptionDialog(
                                null,
                                loadComboBox,
                                "Choose a saved game",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                null,
                                null
                        );

                        if (option == JOptionPane.OK_OPTION) {
                            String selectedFile = (String) loadComboBox.getSelectedItem();
                            System.out.println("Loading game from file: " + selectedFile);
                            // Add your logic to load the game from the selected file
                            BattleSheepGame loadedGame = LoadGame.loadGame("savedGames/" + selectedFile);
                            GameController controller = createGameController(loadedGame);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No saved games found.", "No Saved Games", JOptionPane.INFORMATION_MESSAGE);
                    }
                    System.out.println("Loading a game...");

                   // Quit the game
                } else if (selectedOption.equals("Quit")) {
                    System.out.println("Quitting the game...");
                    System.exit(0);
                }
            }
            //frame.dispose();
        });
        
        frame.pack();
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    private static GameController createGameController(BattleSheepGame game) {
        game.initializeGUI();
        HexagonalGridPanel UI = game.getGui();
        if (UI == null) {
            System.out.println("UI is null");
        } 

        /*SwingUtilities.invokeLater(() -> {
            
        };)*/
        GameController controller = new GameController(game, game.getGui());
        game.getGui().setGameController(controller); //ezer nem mukodik szerintem a new game
        gameFrame.add(game.getGui());
       

       return controller;
    }
    


    private static void showPlayerSelection() {
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
                BattleSheepGame game = new BattleSheepGame(null, selectedPlayers);
                game.initializeGUI();
                HexagonalGridPanel UI = game.getGui();
                
                GameController controller = new GameController(game, UI);

                //gameController.startNewGame(selectedPlayers);
                //controller.startGame();
                playerSelectionFrame.dispose();
            }
        });

        playerSelectionFrame.setVisible(true);
    }
}
