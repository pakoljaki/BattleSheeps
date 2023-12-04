package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class GameController implements Serializable { //extends JFrame {
    
    private BattleSheepGame game;
    private HexagonalGridPanel gui;
    //private GameMenu menu;
    private boolean isGameFinished = false;
    Random random = new Random(); //for bot

    public GameController(BattleSheepGame game, HexagonalGridPanel gui) {
        this.game = game;
        this.gui = gui;
        game.getBoard().getBoard().get(0).setPlayer(game.getPlayers().get(0));
        game.getBoard().getBoard().get(31).setPlayer(game.getPlayers().get(1));
    }
    public void setter(BattleSheepGame game, HexagonalGridPanel gui) {
        this.game = game;
        this.gui = gui;
        game.getBoard().getBoard().get(0).setPlayer(game.getPlayers().get(0));
        game.getBoard().getBoard().get(31).setPlayer(game.getPlayers().get(1));
    }
    public boolean isGameOver() {
        return isGameFinished;
    }
    public void saveGame() {
        // Implement save game logic here
        System.out.println("Saving the game...");
    }

    public void loadGame() {
        // Implement load game logic here
        System.out.println("Loading a saved game...");
    }


    /*public void startGame() {
        if (game == null) {
            System.out.println("Game is null");
            return;
        }
        if (gui == null) {
            System.out.println("GUI is null");
            
        }

        // Start the game
        Player currentPlayer = game.getNextPlayer();
        //updateTurnLabel(currentPlayer);
        makeMove(currentPlayer);

        while (!isGameFinished()) {
            if (game.getPlayers().get(game.getCurrentTurn()).isStuck()) {
                int nextPlayerIndex = (game.getCurrentTurn() + 1) % game.getPlayers().size(); // Wrap around
                System.out.println("Player " + game.getCurrentTurn() + " is stuck. Switching to player " + nextPlayerIndex);
                game.setCurrentTurn(nextPlayerIndex);
               
            }
            currentPlayer = game.getNextPlayer();
            //updateTurnLabel(currentPlayer);
            makeMove(currentPlayer);
        }

        // Announce the winner
        Player winner = determineWinner();
        announceWinner(winner);
    }*/
    void move(Tile firstClick, Tile secondClick, int numberOfSheep, int currentId) { //currentId = game.getCurrentTurn()  
        //Check if the next player will be a bot (this works as the player is always the first to go) 
        int nextPlayerId = (currentId + 1) % game.getPlayers().size();
        Player player = game.getPlayers().get(nextPlayerId);
        boolean nextIsBot = player instanceof Bot; //true is the player after this turn will be a bot
        
        if(firstClick.getPlayer().getIndex() == game.getCurrentTurn()) { // If the move is valid (made by the current player, on his/hers Tiles)
            System.out.println("-------\nthe game is working, controller.move() is called");
            if (!isGameFinished()) { // If the game is not finished //KI KEVEGERE AZ EGESZNEK, miutan a bot meg en is leptem
                System.out.println("Game is not finished");
                game.handlePlayerMove(firstClick, secondClick, numberOfSheep); // HANDLE THE MOVE
                game.setCurrentTurn((game.getCurrentTurn() + 1) % game.getPlayers().size());
                //after handleplayermove the the currentTurn is updated
            } else {
                System.out.println("Game is finished");
                isGameFinished = true;
                Player winner = determineWinner(); 
                announceWinner(winner);
            }
        } else {
            System.out.println("Not you turn");
        }

        // If the next player is a bot and the bot cna move
        if (nextIsBot && !game.getPlayers().get(nextPlayerId).isStuck()) { // If the next player is a bot and the bot cna move
            System.out.println("Next player is a bot");
            makeMove(); // After the user the bot will move too if its not stuck 
            game.setCurrentTurn(0); // Reset the currentTurn //this should be 0 anyway
        }

        //bot stuck then the player comes next
        if (nextIsBot && game.getPlayers().get(1).isStuck()) {
            game.setCurrentTurn(0);
        }
        if (nextIsBot) {
            if (game.getPlayers().get(game.getCurrentTurn()).isStuck()) { //get(0)
                while (!game.getPlayers().get(1).isStuck()) { //get(1)
                    game.setCurrentTurn(1);
                    makeMove();
                }  
            }
        }
        //player stuck after a move
        if(game.getPlayers().get(game.getCurrentTurn()).isStuck()){ 
            // If the real player is stuck after the bots move
            int nextPlayerIndex = (game.getCurrentTurn() + 1) % game.getPlayers().size(); // Wrap around
            System.out.println("Player " +  game.getPlayers().get(game.getCurrentTurn()) + " is stuck. Switching to player " + game.getPlayers().get(nextPlayerIndex));
            game.setCurrentTurn(nextPlayerIndex); // update currentTurn again so this Player is skipped
            if (isGameFinished()) {
                System.out.println("Game is finished");
                isGameFinished = true;
                Player winner = determineWinner();
                announceWinner(winner);
            }
        }
        if (isGameFinished) {
            System.out.println("Game is finished");
                Player winner = determineWinner(); 
                announceWinner(winner);
        }
        
    }

    //Logic for the bot to make a move
    public void makeMove() {
        System.out.println("-------\n bot.makeMove() is called");
        // Choose a tile with more than 1 sheep
        List<Tile> eligibleTiles = getEligibleTiles();
        if (!eligibleTiles.isEmpty()) {  // If there are eligible tile(s)
            // Randomly select one tile
            Tile source = eligibleTiles.get(random.nextInt(eligibleTiles.size()));
            // Get legitimate steps from the selected tile
            List<Tile> legitSteps = source.getLegitSteps();

            if (!legitSteps.isEmpty()) { // If there are legitimate steps from the choosen tile (which there must be as we checked for it)
                // Randomly select one Tile from the Tiles that the bot can move to
                Tile destination = legitSteps.get(random.nextInt(legitSteps.size()));

                // Get the number of sheeps on the original randomly selected tile
                int numSheep = source.getNumOfSheep();

                // Randomly select a number between one and numOfSheep-1 
                int numToMove = random.nextInt(numSheep - 1) + 1;

                game.handlePlayerMove(source, destination, numToMove);
                // Call the move() function from the Player class
                //move(source, destination, numToMove); // Move the specified number of sheep from the source to the destination
            }
        } else {
            System.out.println("No eligible tiles found. Bot is Stuck");
        }
    }
    

    // Helper method to get tiles with more than 1 sheep on them for the bot. These are the Tiles that the Bot can move from.
    private List<Tile> getEligibleTiles() {
        List<Tile> ownedTiles = game.getPlayers().get(game.getCurrentTurn()).getOwnedTiles();
        List<Tile> eligibleTiles = new ArrayList<>();

        for (Tile tile : ownedTiles) {
            if (tile.getNumOfSheep() > 1 && !tile.getLegitSteps().isEmpty()) { // If the tile has more than 1 sheep and it has legitimate step(s)
                eligibleTiles.add(tile);
            }
        }

        return eligibleTiles; // Return the list of tiles the bot can move from
    }

    

    private boolean isGameFinished() {
        // Check if all players are stuck
        for (Player player : game.getPlayers()) {
            if (!player.isStuck()) {
                return false; // At least one player is not stuck
            }
        }
        return true; // All players are stuck
    }

    Player determineWinner() {
        // Find the player with the highest total number of sheeps on their tiles
       Player winner = null;
       if (game.getPlayers().get(0).getOwnedTiles().size() > game.getPlayers().get(1).getOwnedTiles().size()) {
           winner = game.getPlayers().get(0);
       } else  if ( game.getPlayers().get(0).getOwnedTiles().size() < game.getPlayers().get(1).getOwnedTiles().size()) {
           winner = game.getPlayers().get(1);
       }

        return winner;
    }

    private void announceWinner(Player winner) {
        // Display a message announcing the winner in the GUI (e.g., use a JLabel)
        String message;
        if (winner == null) {
            message = "No winner. Game ended in a draw.";
        }
        else {
            message = "Winner: " + winner.getName();
        }
        JLabel winnerLabel = new JLabel(message);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gui.add(winnerLabel, BorderLayout.EAST);
        gui.revalidate();
        gui.repaint();
    }

    private void updateTurnLabel(Player currentPlayer) {
        // Display whose turn it is in the GUI (e.g., use a JLabel)
        JLabel turnLabel = new JLabel("Turn: " + currentPlayer.getName());
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gui.add(turnLabel, BorderLayout.EAST); //NEM KELL VALAMI LAYOUT CONTROLLER?
        gui.revalidate();
        gui.repaint();
    }

    public void startNewGame(int selectedPlayers) {
        // Add your game start logic here based on the number of players
        BattleSheepGame newGame = new BattleSheepGame(null ,selectedPlayers);
        newGame.initializeGUI();
        //newGame.setGui(newGame.getGui());
        setter(newGame, newGame.getGui());
        //this.startGame();

        System.out.println("Starting new game with " + selectedPlayers + " players...");
    }
}
