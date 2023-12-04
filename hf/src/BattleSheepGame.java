package src;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import java.awt.*;



public class BattleSheepGame implements Serializable {
    private BattleSheepBoard board;
    private HexagonalGridPanel gui;
    private List<Player> players;
    private int currentTurn;
    private static final long serialVersionUID = 1L;

    public BattleSheepGame(HexagonalGridPanel gui, int numRealPlayers) {
        this.gui = gui;
        this.board = new BattleSheepBoard(this);
        if (numRealPlayers == 2) {
            players = new ArrayList<>(2);
            players.add(new Player("Player 1", 0, Color.WHITE));
            players.add(new Player("Player 2", 1, Color.BLACK));
        } else {
            players = new ArrayList<>(2);
            players.add(new Player("Player 1", 0, Color.WHITE));
            players.add(new Bot("Player 2", 1, Color.BLACK));
        }

        players.get(0).addNewTile(board.getTiles(0));
        players.get(1).addNewTile(board.getTiles(31));
        currentTurn = 0; // 0 or 1
        //currentturn
        
        // Additional setup for players, bots, etc.
    }

    //constructor
    public BattleSheepGame() {
        this.board = new BattleSheepBoard(this);
    }

    //setter, getter methods
    public void setGui(HexagonalGridPanel gui) {
        this.gui = gui;
    }
    public HexagonalGridPanel getGui() {
        return this.gui;
    }
    public BattleSheepBoard getBoard() {
        return board;
    }
    public int getCurrentTurn() {
        return currentTurn;
    }
    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public Player getNextPlayer() {
        // For example, use a turn counter and modulo operation
        int nextPlayerIndex = (currentTurn + 1) % players.size();
        return players.get(nextPlayerIndex);
    }

    //methods
    public void initializeGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hexagon Grid");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui = new HexagonalGridPanel(this);
            setGui(gui);
            frame.add(gui);

            // Set the preferred size based on the hexagonal grid
            frame.setPreferredSize(new Dimension(800, 540));

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public void handlePlayerMove(Tile source, Tile destination, int numSheep) {
        // Perform move logic
        if (isValidMove(source, destination, numSheep)) {
            int id1 = source.getId();
            int id2 = destination.getId();
            board.getBoard().get(id1).setNumOfSheep(board.getBoard().get(id1).getNumOfSheep() - numSheep);
            board.getBoard().get(id2).setNumOfSheep(board.getBoard().get(id2).getNumOfSheep() + numSheep); //0 + valami
            players.get(currentTurn).addNewTile(destination); //players az null
            destination.setPlayer(players.get(currentTurn)); //beallitani a jatekost a csempehez
            //az is lehet hogy nem currentturn hanem source.getplayer()
            System.out.println("Move successful! " + numSheep + " sheep moved from " + id1 + " to " + id2);
            //ITT KELL HOGY ATALITSUK MOST MELYIK JATEKOS JON
            //int nextPlayerIndex = (currentTurn + 1) % players.size();
            //currentTurn = nextPlayerIndex;
            System.out.println("Next player: " + players.get(currentTurn).getName());
        } else {
            // Invalid Move
            System.out.println("Invalid move! Check the game rules.");
        }
        
        //gui.repaint();
        gui.updateButtonValues();
    }

    private boolean isValidMove(Tile source, Tile destination, int numSheep) {
        if (source.getPlayer() == null) {
            return false;
        } else {
            return numSheep >= 1 && numSheep < source.getNumOfSheep() && source.getLegitSteps().contains(destination);
        }
    } 
}
