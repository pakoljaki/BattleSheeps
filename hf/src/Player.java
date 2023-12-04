package src;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Player implements Serializable{
    private String name; 
    private List<Tile> ownedTiles; //list of tiles belonging to the given Player
    private int index; //player index (0 or 1)
    private Color c; //color of the player (Black or White)

    // Constructor
    public Player(String name, int i, Color color) {
        this.name = name;
        this.ownedTiles = new ArrayList<>();
        index = i;
        c = color;
    }
    // Getters and setters
    public int getIndex() { // Player index
        return index;
    }
    public Color getColor() { // Player color
        return c;
    }
    public String getName() { // Player name
        return name;
    }
    public List<Tile> getOwnedTiles() { // Returns list of tiles belonging to the given Player
        return ownedTiles;
    }
    public void addNewTile(Tile newTile) { // Add a new tile to the list of Tiles belonging to the Player
        ownedTiles.add(newTile); 
    }

    // Methods
    public void move(Tile source, Tile destination, int numSheep) { // Move the specified number of sheep from the source to the destination
        // Check if the move is valid
        if (isValidMove(source, destination, numSheep)) { // If the number of sheep being moved is within the allowed range and the Tiles are correct
            // Update the game state
            performMove(source, destination, numSheep);  // Move the specified number of sheep from the source to the destination
        } else {
            // Handle invalid move
            System.out.println("Invalid move! Check the game rules."); 
        }
    }

    private boolean isValidMove(Tile source, Tile destination, int numSheep) {
        // Check if the destionation Tile is correct and if the number of sheep being moved is within the allowed range.
        return numSheep >= 1 && numSheep < source.getNumOfSheep() && source.getLegitSteps().contains(destination);
    }

    private void performMove(Tile source, Tile destination, int numSheep) {
        source.setNumOfSheep(source.getNumOfSheep() - numSheep); // Move the specified number of sheep from the source to the destination
        destination.setNumOfSheep(destination.getNumOfSheep() + numSheep); //destination.getNumOfSheep() should always be 0 before adding to it

        // Update the list of owned tiles for the player
        if (!ownedTiles.contains(destination)) { //this should always return true
            ownedTiles.add(destination);
        }
    }

    // Additional method for checking if the player is stuck
    public boolean isStuck() {
        for (Tile tile : ownedTiles) { // Traverse all the Tiles a Player has sheeps on
            if (tile.getNumOfSheep() >= 2 && !tile.getLegitSteps().isEmpty()) { // If there is a tile that has atleast 1 valid step(s), there are STILL valid move(s)
                return false; // The player is NOT stuck
            }
        }
        return true; // If none of the Tiles had any valid moves, it means that the player is stuck
    }

    // Override toString for easy debugging or logging
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", ownedTiles=" + ownedTiles +
                '}';
    }
}
