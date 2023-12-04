package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

public class LoadGame {

    public static BattleSheepGame loadGame() {
        JFileChooser fileChooser = new JFileChooser("./savedGamesMenu"); // Set the default directory to 'saved'
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return LoadGame.loadGame(selectedFile.getAbsolutePath());
        } else {
            return null;
        }
    }
    
    public static BattleSheepGame loadGame(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            BattleSheepGame game = (BattleSheepGame) ois.readObject();
            System.out.println("Game loaded successfully from: " + filePath);
            return game;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
