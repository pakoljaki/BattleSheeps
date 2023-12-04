package src;
import javax.print.DocFlavor.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HexagonalGridPanel extends JPanel {
    private static final int HEX_SIZE = 30;
    private BattleSheepGame game;
    private GameController gameController;
    private Map<Integer, Coordinates> hexagonCenters;
    private Tile firstClick;
    private Tile secondClick;

    public HexagonalGridPanel(BattleSheepGame game) {
        this.game = game;
        setLayout(null);
        setPreferredSize(new Dimension(300, 300));
        hexagonCenters = new HashMap<>();
        setHexagonCentersFromBoard();
        addButtons();
        try {
            // Sleep for 100 milliseconds (0.1 seconds)
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Handle the exception if needed
            e.printStackTrace();
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }


    public void updateButtonValues() {
        removeAll(); // Clear existing buttons
        addButtons(); // Add buttons with updated values
        revalidate();
        repaint();
    }

    /*private void addButtons() {
        for (Tile tile : game.getBoard().getBoard().values()) {
            //tile.getPlayerColor();
            //tile.getNumOfSheep();
            Coordinates center = tile.getCoordinates();
            JButton button = new JButton(Integer.toString(tile.getNumOfSheep()));
            int buttonX = center.getX() - HEX_SIZE / 2;
            int buttonY = center.getY() - HEX_SIZE / 2;
            button.setBounds(buttonX, buttonY, HEX_SIZE, HEX_SIZE);

            button.setBackground(tile.getPlayerColor());
            button.addActionListener(new ButtonClickListener(tile));

            add(button);
        }
    }*/
    private void addButtons() {
        for (Tile tile : game.getBoard().getBoard().values()) {
            Coordinates center = tile.getCoordinates();
            JButton button = new JButton(Integer.toString(tile.getNumOfSheep()));
            int buttonX = center.getX() - HEX_SIZE / 2 - 5;
            int buttonY = center.getY() - HEX_SIZE / 2 - 5;
            button.setBounds(buttonX, buttonY, 40, 40);

            button.setBackground(tile.getPlayerColor());

            // Use SwingWorker to load images in the background
            SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
                @Override
                protected ImageIcon doInBackground() {
                    String imageName = getImageName(tile);
                    /*URL resource = getClass().getResource("/images/w16.png");
                    if (resource == null) {
                        System.out.println("Resource not found!");
                    }*/
                    return new ImageIcon(getClass().getResource("/images/" + imageName + ".png"));

                }                
                @Override
                protected void done() {
                    try {
                        ImageIcon icon = get();
                        button.setIcon(icon);
                        button.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            worker.execute(); // Start the SwingWorker

            button.addActionListener(new ButtonClickListener(tile));
            add(button);
        }
    }
    private String getImageName(Tile tile) {
        StringBuilder imageName = new StringBuilder();

        // Add color code
        if (tile.getPlayerColor() == Color.BLACK) {
            imageName.append("b");
        } else if (tile.getPlayerColor() == Color.WHITE) {
            imageName.append("w");
        } else {
            imageName.append("g");
        }

        // Add sheep count
        int sheepCount = tile.getNumOfSheep();
        if (sheepCount > 0) {
            imageName.append(sheepCount);
        }

        return imageName.toString();
    }


    private void setHexagonCentersFromBoard() {
        for (Map.Entry<Integer, Tile> entry : game.getBoard().getBoard().entrySet()) {
            int index = entry.getKey();
            Tile tile = entry.getValue();
            hexagonCenters.put(index, tile.getCoordinates());
        }
    }

    private class ButtonClickListener implements ActionListener {
        private Tile clickedTile;

        public ButtonClickListener(Tile tile) {
            this.clickedTile = tile;
        }

@Override
public void actionPerformed(ActionEvent e) {
    if (firstClick == null) {
        firstClick = clickedTile;
        System.out.println("First click: " + firstClick.getId());
    } else {
        secondClick = clickedTile;
        System.out.println("Second click: " + secondClick.getId());
        showMoveOptions();
        firstClick = null;
        secondClick = null;
        repaint();
    }
}
    }

    private void showMoveOptions() {
        if (firstClick != null && secondClick != null) {
            if (firstClick.getPlayer() == null || firstClick.getNumOfSheep() < 2) {
                System.out.println("Cannot move like this");
                
            } else {
                int maxSheep = firstClick.getNumOfSheep() - 1;

            Integer[] options = new Integer[maxSheep];
            for (int i = 0; i < maxSheep; i++) {
                options[i] = i + 1;
            }

            JComboBox<Integer> comboBox = new JComboBox<>(options);
            int result = JOptionPane.showOptionDialog(
                    null, //center
                    comboBox,
                    "Select number of sheep to move",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null
            );

            if (result == JOptionPane.OK_OPTION) {
                int selectedSheep = (Integer) comboBox.getSelectedItem();
                gameController.move(firstClick, secondClick, selectedSheep, game.getCurrentTurn());
            }
            }
            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Tile tile : game.getBoard().getBoard().values()) {
            Color color = tile.getPlayerColor();
            Coordinates center = tile.getCoordinates();
            drawHexagon(g, center.getX(), center.getY(), color);
            g.setColor(Color.BLUE);
            g.drawString(Integer.toString(tile.getNumOfSheep()), center.getX(), center.getY());
        }

        int lineX = getWidth() / 2;
        g.setColor(Color.BLACK);
        g.drawLine(lineX, 0, lineX, getHeight());

        String turnText = "Now it's " + game.getPlayers().get(game.getCurrentTurn()).getName() + "'s turn";
        //drawTurnInfo(g, turnText);

        if (this.gameController.isGameOver()) {
            Player wPlayer = gameController.determineWinner();
            if (wPlayer != null) {
                String message = "Winner is " + wPlayer.getName();
                //JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE); //ez mi?
                drawTurnInfo(g, message);
            } else {
                String message = "Game ended in a draw";
                //JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                drawTurnInfo(g, message);
            }
            
            
            
        }
    }

    private void drawHexagon(Graphics g, int x, int y, Color c) {
        Polygon p = new Polygon();

        for (int i = 0; i < 6; i++) {
            p.addPoint((int) (x + HEX_SIZE * Math.cos(i * 2 * Math.PI / 6)),
                    (int) (y + HEX_SIZE * Math.sin(i * 2 * Math.PI / 6)));
        }

        g.setColor(c);
        g.fillPolygon(p);
        Color c1 = new Color(0, 102, 0);
        g.setColor(c1);
        g.drawPolygon(p);
    }
    
    private void drawTurnInfo(Graphics g, String turnText) {
        // Draw the turn information on the right side of the screen
        int x = getWidth() - 250; // Adjust the x-coordinate based on your layout
        int y = 50; // Adjust the y-coordinate based on your layout
    
        // Use a JLabel for displaying text
        JLabel turnLabel = new JLabel(turnText);
        turnLabel.setBounds(x, y, 250, 50); // Set the bounds of the label
        add(turnLabel); // Assuming HexagonalGridPanel is a JPanel
    
        // Optionally, you can set the font, foreground color, etc.
        //turnLabel.setFont(new Font("sans-serif", Font.PLAIN, 20));
        //turnLabel.setForeground(Color.BLACK);
    }

    
}

