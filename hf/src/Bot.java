package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

// This class represents the bot player
public class Bot extends Player implements Serializable {
    Random random = new Random();

    // Constructor
    public Bot(String name, int i, Color color) {
        super(name, i, color);
    }

    // Method for the bot to make a move
    public void makeMove() {
        // Choose a tile with more than 1 sheep
        List<Tile> eligibleTiles = getEligibleTiles();
        if (!eligibleTiles.isEmpty()) {
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

                // Call the move() function from the Player class
                move(source, destination, numToMove); // Move the specified number of sheep from the source to the destination
            }
        } else {
            System.out.println("No eligible tiles found.");
        }
    }
    

    // Helper method to get tiles with more than 1 sheep on them for the bot. These are the Tiles that the Bot can move from.
    private List<Tile> getEligibleTiles() {
        List<Tile> ownedTiles = getOwnedTiles();
        List<Tile> eligibleTiles = new ArrayList<>();

        for (Tile tile : ownedTiles) {
            if (tile.getNumOfSheep() > 1 && !tile.getLegitSteps().isEmpty()) { // If the tile has more than 1 sheep and it has legitimate step(s)
                eligibleTiles.add(tile);
            }
        }

        return eligibleTiles; // Return the list of tiles the bot can move from
    }
}
