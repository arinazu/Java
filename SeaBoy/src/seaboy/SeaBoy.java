
package seaboy;

import java.awt.*;
import javax.swing.*;

public class SeaBoy {
    private final JButton buttonStart;
    private final JButton buttonPlace;
    private final JButton buttonFinish;
    private final Field fieldMy;
    private final Field fieldPartner;
    private JLabel labelStatus;
    
    SeaBoy(Game game) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = 950;
        int frameHeight = 670;
        
        JFrame f = new JFrame("Sea Boy");
        f.setLayout(new BorderLayout()); 
        f.setLocation((screenSize.width-frameWidth)/2, (screenSize.height-frameHeight)/2);
        f.setSize(frameWidth, frameHeight);
        f.setMinimumSize(new Dimension(frameWidth, frameHeight));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Северная панель
        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(153, 217, 234)); 
        northPanel.setPreferredSize(new Dimension(0, 75));
        northPanel.setLayout(new BorderLayout()); 
        
        JPanel leftNorthPanel = new JPanel();
        leftNorthPanel.setBackground(new Color(153, 217, 234));
        leftNorthPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        
        JPanel rightNorthPanel = new JPanel();
        rightNorthPanel.setBackground(new Color(153, 217, 234)); 
        rightNorthPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 10));  
        
        buttonStart = new JButton("Начать игру");
        buttonPlace = new JButton("Разместить");
        buttonFinish = new JButton("Завершить игру");
        JButton buttonAbout = new JButton("О программе");
        
        buttonStart.setPreferredSize(new Dimension(160, 40));
        buttonStart.setIcon(new ImageIcon("Icons/start(32).png"));
        buttonStart.setFocusPainted(false);
        buttonStart.setBackground(new Color(208, 226, 248));
        buttonStart.setBorder(BorderFactory.createLineBorder(new Color(105, 178, 197)));
        buttonStart.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

        buttonPlace.setPreferredSize(new Dimension(160, 40));
        buttonPlace.setIcon(new ImageIcon("Icons/place(32).png"));
        buttonPlace.setFocusPainted(false);
        buttonPlace.setBackground(new Color(208, 226, 248));
        buttonPlace.setBorder(BorderFactory.createLineBorder(new Color(105, 178, 197)));
        buttonPlace.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        
        buttonFinish.setPreferredSize(new Dimension(160, 40));
        buttonFinish.setIcon(new ImageIcon("Icons/finish.png"));
        buttonFinish.setFocusPainted(false);
        buttonFinish.setBackground(new Color(208, 226, 248));
        buttonFinish.setBorder(BorderFactory.createLineBorder(new Color(105, 178, 197)));
        buttonFinish.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        
        buttonAbout.setPreferredSize(new Dimension(160, 40));
        buttonAbout.setIcon(new ImageIcon("Icons/about(27).png"));
        buttonAbout.setFocusPainted(false);
        buttonAbout.setBackground(new Color(208, 226, 248));
        buttonAbout.setBorder(BorderFactory.createLineBorder(new Color(105, 178, 197)));
        buttonAbout.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
       
        buttonStart.addActionListener(game);
        buttonStart.setActionCommand("start");
        buttonPlace.addActionListener(game);
        buttonPlace.setActionCommand("place");
        buttonFinish.addActionListener(game);
        buttonFinish.setActionCommand("finish");
        buttonFinish.setEnabled(false);
        buttonAbout.addActionListener(game);
        buttonAbout.setActionCommand("about");
        
        leftNorthPanel.add(buttonStart);
        leftNorthPanel.add(buttonPlace);
        leftNorthPanel.add(buttonFinish);
        rightNorthPanel.add(buttonAbout);
        
        northPanel.add(leftNorthPanel, BorderLayout.WEST);
        northPanel.add(rightNorthPanel, BorderLayout.EAST);
        
        // Южная панель
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.setBackground(new Color(208, 226, 248)); 
        southPanel.setPreferredSize(new Dimension(0, 65));      
        labelStatus = new JLabel();  
        labelStatus.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        southPanel.add(labelStatus);

        // Основная панель
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
                
        // Западная панель
        JPanel westPanel = new JPanel();
        westPanel.setBackground(new Color(153, 217, 234));
        westPanel.setPreferredSize(new Dimension(450, 0));
        westPanel.setLayout(new BorderLayout());
        
        JLabel labelNotMy = new JLabel("Мое поле", SwingConstants.CENTER);
        labelNotMy.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        westPanel.add(labelNotMy, BorderLayout.NORTH);
        
        fieldMy = new Field(game, true);
        westPanel.add(fieldMy, BorderLayout.CENTER);

        // Восточная панель
        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(new Color(153, 217, 234)); 
        eastPanel.setPreferredSize(new Dimension(450, 0));
        eastPanel.setLayout(new BorderLayout());
        
        JLabel labelMy = new JLabel("Поле противника", SwingConstants.CENTER);
        labelMy.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        eastPanel.add(labelMy, BorderLayout.NORTH);
        
        fieldPartner = new Field(game, false);
        eastPanel.add(fieldPartner, BorderLayout.CENTER);
         
        mainPanel.add(westPanel);
        mainPanel.add(eastPanel); 
        f.add(northPanel, BorderLayout.NORTH);
        f.add(southPanel, BorderLayout.SOUTH);
        f.add(mainPanel, BorderLayout.CENTER);
              
        f.setVisible(true);  
    }
    
    public void onStateStarted(boolean stateStarted) {
        if(stateStarted) {
            buttonStart.setEnabled(false);
            buttonPlace.setEnabled(false);
            buttonFinish.setEnabled(true);
        }
        else {
            buttonStart.setEnabled(true);
            buttonPlace.setEnabled(true);
            buttonFinish.setEnabled(false);
        }
    }
    
    public void setStatusString(String message){
        labelStatus.setText(message);
    }
    
    public void setFieldCell(boolean my, int x, int y, Cell cell) {
        if(my)
            fieldMy.setCell(x, y, cell);
        else
            fieldPartner.setCell(x, y, cell);
    }
    
    public static void main(String[] args) {
        Client client = new Client(args[0]);
        Thread clientThread = new Thread(client, "clientThread");
        clientThread.setDaemon(true);
        clientThread.start();

        Partner partner = new Partner(client);
        client.setPartner(partner);
        
        Game game = new Game(partner);
        partner.setGame(game);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SeaBoy seaBoy = new SeaBoy(game);
                game.setSeaBoy(seaBoy);
            }
        }); 
    }  
}
