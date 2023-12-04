package src;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import java.awt.*;

// These Tiles are the nodes in the graph that make up the BattleSheepBoard
public class Tile implements Serializable{
    private int numOfSheep = 0; //each tile starts with 0
    private Player player = null; //each tile starts with no player
    private Coordinates coord; //x, y
    private HashMap<Integer, Integer> neighbours; //side, indexOfTile
    private int id; //index of the Tile
    private BattleSheepBoard board; // BattleSheepBoard that the tile is on


    // Constructor
    public Tile(int id, BattleSheepBoard board) {
        neighbours = new HashMap<>(6);
        this.id = id;
        this.board = board;
    }

    // Getters and setters
    public Color getPlayerColor() {
        Color green = new Color(0, 204, 0);
        return (player != null) ? player.getColor() : green; //if player is null, return green, else return it's color
    }

    public void setNeighbours(int[] n) { //setting up the graph
        for (int i = 0; i < 6; i++) {
            ((HashMap<Integer, Integer>) neighbours).put(i, n[i]); //this.neighbours is null 
        }
    }
    public int getNeighbour(int direction) { //returns the index of the tile in that direction
        if (neighbours.get(direction) == 100) { //if there is no tile in that direction
            return 100;
        } else {
            return neighbours.get(direction);
        }
    }

    public int getId() {
        return id;
    }

    public int getNumOfSheep() {
        return numOfSheep;
    }
    public void setNumOfSheep(int n) {
        this.numOfSheep = n;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player p) {
        this.player = p;
    }

    public void setCoordinates(int x, int y) {
        Coordinates coordi = new Coordinates(x, y);
        this.coord = coordi;
    }
    public Coordinates getCoordinates() {
        return coord;
    }

    // Method for getting all the legit steps from the current tile (returns List<Tile>, where number of Tiles is between 0 and 6)
    public List<Tile> getLegitSteps() {
        List<Tile> legitSteps = new ArrayList<>();

        for (int direction : neighbours.keySet()) {
            int currentTileIndex = this.getNeighbour(direction);
            
            // Traverse in the same direction until a player or no tile is encountered
            while (currentTileIndex != 100 && board.getTiles(currentTileIndex).getNumOfSheep() == 0) {
                int nextTileIndex = board.getTiles(currentTileIndex).getNeighbour(direction);
                Tile nextTile = board.getTiles(nextTileIndex);

                if (nextTileIndex == 100 || nextTile.getNumOfSheep() != 0) {
                    // If there is no next tile or the next tile has sheep, add the current tile and break
                    legitSteps.add(board.getTiles(currentTileIndex));
                    break;
                }

                currentTileIndex = nextTileIndex;
            }
        }

        return legitSteps;
    }
}
