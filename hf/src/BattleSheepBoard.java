package src;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;

// The Tiles are the nodes in the graph that make up the BattleSheepBoard
public class BattleSheepBoard implements Serializable {
    private BattleSheepGame game; //the Game that the Board belongs to
    private Map<Integer, Tile> board; // the graph that makes up the board

    public BattleSheepBoard(BattleSheepGame g){
        // Set up initial game state
        board = new HashMap<>();
        initializeGame(); 
        this.game = g; //set the game
    }

    public void initializeGame() {
        // Initialize the board and set coordinates for each tile
        int[][] tileCoordinates = {
                {90, 102}, {135, 76}, {180, 102}, {225, 76},
                {90, 153}, {135, 127}, {180, 153}, {225, 127},
                {90, 204}, {135, 178}, {180, 204}, {225, 178},
                {90, 255}, {135, 229}, {180, 255}, {225, 229},
                {90, 306}, {135, 280}, {180, 306}, {225, 280},
                {90, 357}, {135, 331}, {180, 357}, {225, 331},
                {90, 408}, {135, 382}, {180, 408}, {225, 382},
                {90, 459}, {135, 433}, {180, 459}, {225, 433}
        };
        // Create 32 tiles and add them to the board with their coordinates
        for (int i = 0; i < 32; i++) {
            board.put(i, new Tile(i, this));
            setTileCoordinates(i, tileCoordinates[i][0], tileCoordinates[i][1]);
            System.out.println("Tile created at index " + i);
        }
        setInitialSheeps(); // Add 2*16 sheeps to two tiles
        System.out.println("Sheeps set");
        setNeigbours(); // Set the neighbours of each tile (create the graph)
        System.out.println("Neighbours set");
    }

    public Map<Integer, Tile> getBoard() {
        return board;
    }
    // Set the coordinates of a tile
    public void setTileCoordinates(int index, int x, int y) {
        board.get(index).setCoordinates(x, y);
    }

    // Add a player to a tile
    public void addPlayerToTile(Player player, int tileIndex) {
        Tile tile = board.get(tileIndex);
        tile.setPlayer(player);
    }

    // Add specified number of sheep to a tile and set the player
    public void addSheepToTile(Player player, int numSheep, int tileIndex) {
        Tile tile = board.get(tileIndex);
        tile.setNumOfSheep(numSheep);
        tile.setPlayer(player);
    }
    
    // Set the initial number of sheeps (put 2*16 in two of the Tiles)
    private void setInitialSheeps() {
        board.get(0).setNumOfSheep(16);
        //board.get(0).setPlayer(game.getPlayers().get(0));
        board.get(31).setNumOfSheep(16);
        //board.get(31).setPlayer(game.getPlayers().get(1));
        System.out.println("Initial sheeps set successfully.");

    }
    
    // Get a tile by its index from the board
    public Tile getTiles(int i){
        return board.get(i);
    }

    // Set the neighbours of each tile (creates the graph)
    public void setNeigbours() {
        int[][] neighbourMatrix = {
            {100, 1, 5, 4, 100, 100},
            {100, 100, 2, 5, 0, 100},
            {100, 3, 7, 6, 5, 1},
            {100, 100, 100, 7, 2, 100},
            {0, 5, 9, 8, 100, 100},
            {1, 2, 6, 9, 4, 0},
            {2, 7, 11, 10, 9, 5},
            {3, 100, 100, 11, 6, 2},
            {4, 9, 13, 12, 100, 100},
            {5, 6, 10, 13, 8, 4},
            {6, 11, 15, 14, 13, 9},
            {7, 100, 100, 15, 10, 6},
            {8, 13, 17, 16, 100, 100},
            {9, 10, 14, 17, 12, 8},
            {10, 15, 19, 18, 17, 13},
            {11, 100, 100, 19, 14, 10},
            {12, 17, 21, 20, 100, 100},
            {13, 14, 18, 21, 16, 12},
            {14, 19, 23, 22, 21, 17},
            {15, 100, 100, 23, 18, 14},
            {16, 21, 25, 24, 100, 100},
            {17, 18, 22, 25, 20, 16},
            {18, 23, 27, 26, 25, 21},
            {19, 100, 100, 27, 22, 18},
            {20, 25, 29, 28, 100, 100},
            {21, 22, 26, 29, 24, 20},
            {22, 27, 31, 30, 29, 25},
            {23, 100, 100, 31, 26, 22},
            {24, 29, 100, 100, 100, 100}, 
            {25, 26, 30, 100, 28, 24},
            {26, 31, 100, 100, 100, 29},
            {27, 100, 100, 100, 30, 26}
        };
    
        //set the neighbours of each tile
        for (int i = 0; i < neighbourMatrix.length; i++) {
            Tile tile = board.get(i);
            tile.setNeighbours(neighbourMatrix[i]);
            System.out.println("Set neighbours for Tile at index " + i);
        }
    }

}

