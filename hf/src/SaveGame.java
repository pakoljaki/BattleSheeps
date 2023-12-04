package src;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

public class SaveGame {
    public static void saveGame(BattleSheepGame game) {
        String fileName = JOptionPane.showInputDialog("Enter the file name:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            SaveGame.saveGame(game, "savedGames/" + fileName + ".ser");
        }
    }
    
    public static void saveGame(BattleSheepGame game, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(game);
            System.out.println("Game saved successfully to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
