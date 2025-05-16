package seaboy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Game implements ActionListener {
    private final Partner partner;
    private SeaBoy seaBoy = null;
    private boolean stateStarted = false;
    private boolean sentStart = false;
    private boolean receivedStart = false;
    private final Cell[][] cellMy = new Cell[10][10];
    private final Cell[][] cellPartner = new Cell[10][10];
    private boolean placed = false;
    private boolean turn = false;   //очередь хода
    
    public Game(Partner partner) {
        this.partner = partner;
        clearCellPartner();
        new Place(cellMy);
        placed = true;
    }
    
    public void setSeaBoy(SeaBoy seaBoy) {
        this.seaBoy = seaBoy;
        updateField(false);
        updateField(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        Scanner scanner = new Scanner(s);
        String command = scanner.next();
        if(s.equals("start")) {
            onButtonStart();
        } 
        else if(s.equals("place")) {
            onButtonPlace();
        }
        else if(s.equals("finish")) {
            onButtonFinish();
        }
        else if(s.equals("about")) {
            onButtonAbout();
        }     
        else if(command.equals("shot")) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            onButtonShot(x, y);
        }
        else if(command.equals("my")) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            onButtonMy(x, y);
        }
    }
    
    public void onReceiveStart() {
        System.out.println("Partner requests to start the game");
        
        if(sentStart){
            setStateStarted(true);
            turn = false;
            seaBoy.setStatusString("Игра началась! Ход вашего друга");
        }
        else{
            receivedStart = true;
            seaBoy.setStatusString("Ваш друг хочет начать игру");
        }
    }

    public void onReceiveFinish() {                                 // мы приняли 
        if(!stateStarted) return;
        System.out.println("Partner has finished the game");
        seaBoy.setStatusString("Ваш друг завершил игру");
        setStateStarted(false);
        JOptionPane.showMessageDialog(null, new String[] {"Ваш друг завершил игру!"}, "Конец игры", 
                    JOptionPane.INFORMATION_MESSAGE);
    }

    public void onReceiveShot(int x, int y) {
        if(!stateStarted) return;
        System.out.println(String.format("Partner made a shot at %d %d", x , y));
        if(cellMy[x][y] == Cell.EMPTY) {
            seaBoy.setStatusString(
                String.format("Ваш друг промахнулся (%d, %d). Ваш ход", x, y));
            partner.sendMiss(x, y);
            cellMy[x][y] = Cell.MISS;
            seaBoy.setFieldCell(true, x, y, Cell.MISS);
            turn = true;
        }
        else if(cellMy[x][y] == Cell.SHIP) {
            seaBoy.setStatusString(
                String.format("Ваш друг попал (%d, %d). Ход вашего друга", x, y));
            partner.sendHit(x, y);
            cellMy[x][y] = Cell.HIT;
            seaBoy.setFieldCell(true, x, y, Cell.HIT);
            
            if(isKilled(x, y))
                setKilled(x, y);

            if(isOver(true)) {
                seaBoy.setStatusString("Вы проиграли!");
                setStateStarted(false);
                JOptionPane.showMessageDialog(null, new String[] {"Вы проиграли!"}, "Конец игры", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void onReceiveMiss(int x, int y) {
        System.out.println(String.format("You missed at %d %d", x , y));
        seaBoy.setStatusString(
            String.format("Вы промахнулись (%d, %d). Ход вашего друга", x, y));
        cellPartner[x][y] = Cell.MISS;
        seaBoy.setFieldCell(false, x, y, Cell.MISS);
        turn = false;
    }

    public void onReceiveHit(int x, int y) {
        System.out.println(String.format("You hit at %d %d", x , y));
        seaBoy.setStatusString(
            String.format("Вы попали! (%d, %d). Ваш ход", x, y));
        cellPartner[x][y] = Cell.HIT;
        seaBoy.setFieldCell(false, x, y, Cell.HIT);
    }

    public void onReceiveKill(int x, int y) {
        System.out.println(String.format("You killed at %d %d", x , y));
        cellPartner[x][y] = Cell.KILL;
        seaBoy.setFieldCell(false, x, y, Cell.KILL);

        if(isOver(false)) {
            seaBoy.setStatusString("Вы выиграли!");
            setStateStarted(false);
            JOptionPane.showMessageDialog(null, new String[] {"Вы выиграли!"}, "Конец игры", 
                    JOptionPane.INFORMATION_MESSAGE);   
        }
    }
    
    private void onButtonStart() {
        if(stateStarted) return;
        partner.sendStart();
        if(receivedStart){
            setStateStarted(true);
            turn = true;
            seaBoy.setStatusString("Игра началась! Ваш ход");
        }
        else{
            sentStart = true;
            seaBoy.setStatusString("Вы отправили запрос на начало игры");
        }    
    }

    private void onButtonPlace() {
        if(stateStarted) return;
        new Place(cellMy);
        placed = true;
        updateField(true);
    }

    private void onButtonFinish() {
        if(!stateStarted) return;
        partner.sendFinish();
        setStateStarted(false);
        seaBoy.setStatusString("Игра завершена!");
    }
    
    private void onButtonAbout() {
        JOptionPane.showMessageDialog(null, new String[] {"Игра 'Морской бой'",
                                "Выполнила: Зубриянова А.А. 22ВВП1",
                                "Руководитель: Юрова О.В.",
                                "Кафедра: Вычислительная техника",
                                "Пензенский государственный университет, 2025"}, "О программе", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onButtonShot(int x, int y) {
        if(!stateStarted) return;
        if(!turn) {
            seaBoy.setStatusString("Ход вашего друга");
            return;
        }
        switch (cellPartner[x][y]) {
            case EMPTY -> {
                partner.sendShot(x, y);
            }
            case MISS -> {
                seaBoy.setStatusString(String.format("Вы давно промахнулись (%d, %d)", x, y));
            }
            case HIT, KILL -> {
                seaBoy.setStatusString(String.format("Вы давно попали! (%d, %d)", x, y));
            }
        }
    }

    private void onButtonMy(int x, int y) {
        if(!stateStarted) return;
        switch (cellMy[x][y]) {
            case EMPTY -> {
                seaBoy.setStatusString(String.format("Плещется водичка"));
            }
            case SHIP -> {
                seaBoy.setStatusString(String.format("Ваш корабль"));
            }
            case MISS -> {
                seaBoy.setStatusString(String.format("Дырка от бублика"));
            }
            case HIT, KILL -> {
                seaBoy.setStatusString(String.format("Ваш дырявый корабль"));
            }
        }
    }

    private void setStateStarted(boolean state) {
        if(stateStarted == state) 
            return;
        stateStarted = state;
        System.out.println("stateStarted = " + stateStarted);
        
        seaBoy.onStateStarted(stateStarted);
        if(stateStarted) {
            if(!placed) {
                new Place(cellMy);
                updateField(true);
            }
        }
        else {
            clearCellPartner();
            updateField(false);
        }

        sentStart = false;
        receivedStart = false;
        placed = false;
    }
    
    private void updateField(boolean my) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(my)
                    seaBoy.setFieldCell(true, x, y, cellMy[x][y]);
                else
                    seaBoy.setFieldCell(false, x, y, cellPartner[x][y]);
            }
        }
    }

    private void clearCellPartner() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                cellPartner[x][y] = Cell.EMPTY;
            }
        }
    }
    
    private boolean isOver(boolean my) {
        int c = 0;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(my) {
                    if(cellMy[x][y] == Cell.KILL) c++;
                } else {
                    if(cellPartner[x][y] == Cell.KILL) c++;
                }
            }
        }
        return (c == 20);
    }
    
    private boolean isKilled(int x, int y) {
        for (int i = x; i < 10; i++) {
            if((cellMy[i][y] == Cell.EMPTY) || (cellMy[i][y] == Cell.MISS)) break;
            else if(cellMy[i][y] == Cell.SHIP) return false;
        }
        for (int i = x; i >= 0; i--) {
            if((cellMy[i][y] == Cell.EMPTY) || (cellMy[i][y] == Cell.MISS)) break;
            else if(cellMy[i][y] == Cell.SHIP) return false;
        }
        for (int j = y; j < 10; j++) {
            if((cellMy[x][j] == Cell.EMPTY) || (cellMy[x][j] == Cell.MISS)) break;
            else if(cellMy[x][j] == Cell.SHIP) return false;
        }
        for (int j = y; j >= 0; j--) {
            if((cellMy[x][j] == Cell.EMPTY) || (cellMy[x][j] == Cell.MISS)) break;
            else if(cellMy[x][j] == Cell.SHIP) return false;
        }
        return true;
    }

    private void setKilled(int x, int y) {
        for (int i = x; i < 10; i++) {
            if(cellMy[i][y] == Cell.HIT) {
                partner.sendKill(i, y);
                cellMy[i][y] = Cell.KILL;
                seaBoy.setFieldCell(true, i, y, Cell.KILL);
            }
            else if(cellMy[i][y] == Cell.KILL) {}
            else break;
        }
        for (int i = x; i >= 0; i--) {
            if(cellMy[i][y] == Cell.HIT) {
                partner.sendKill(i, y);
                cellMy[i][y] = Cell.KILL;
                seaBoy.setFieldCell(true, i, y, Cell.KILL);
            }
            else if(cellMy[i][y] == Cell.KILL) {}
            else break;
        }
        for (int j = y; j < 10; j++) {
            if(cellMy[x][j] == Cell.HIT) {
                partner.sendKill(x, j);
                cellMy[x][j] = Cell.KILL;
                seaBoy.setFieldCell(true, x, j, Cell.KILL);
            }
            else if(cellMy[x][j] == Cell.KILL) {}
            else break;
        }
        for (int j = y; j >= 0; j--) {
            if(cellMy[x][j] == Cell.HIT) {
                partner.sendKill(x, j);
                cellMy[x][j] = Cell.KILL;
                seaBoy.setFieldCell(true, x, j, Cell.KILL);
            }
            else if(cellMy[x][j] == Cell.KILL) {}
            else break;
        }
    }
}
