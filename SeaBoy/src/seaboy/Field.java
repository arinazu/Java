
package seaboy;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Field extends JPanel{
    private final JButton[][] button = new JButton[10][10];
    private final ImageIcon iconEmpty = new ImageIcon("Icons/empty.png");
    private final ImageIcon iconShip = new ImageIcon("Icons/ship.png");
    private final ImageIcon iconMiss = new ImageIcon("Icons/point.png");
    private final ImageIcon iconHit = new ImageIcon("Icons/cross.png");
    private final ImageIcon iconKill = new ImageIcon("Icons/kill.png");
    
    public Field(Game game, boolean my) {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel gridPanel = new JPanel(); 
        setBackground(new Color(153, 217, 234));
        gridPanel.setLayout(new GridLayout(10, 10));
        //gridPanel.setPreferredSize(new Dimension(400, 400)); 
        
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                button[x][y] = new JButton();
                button[x][y].setPreferredSize(new Dimension(40, 40));
                button[x][y].setIcon(iconEmpty);
                button[x][y].addActionListener(game);
                if(my)
                    button[x][y].setActionCommand(String.format("my %d %d", x, y));
                else
                    button[x][y].setActionCommand(String.format("shot %d %d", x, y));
                gridPanel.add(button[x][y]);
            }
        }
        add(gridPanel); 
    }
    
    public void setCell(int x, int y, Cell cell) {
        switch (cell) {
            case EMPTY -> {
                button[x][y].setIcon(iconEmpty);
            }
            case SHIP -> {
                button[x][y].setIcon(iconShip);
            }
            case MISS -> {
                button[x][y].setIcon(iconMiss);
            }
            case HIT -> {
                button[x][y].setIcon(iconHit);
            }
            case KILL -> {
                button[x][y].setIcon(iconKill);
            }
        }
    }
}
