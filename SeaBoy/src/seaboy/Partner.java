package seaboy;

import java.util.Scanner;

public class Partner {
    private Client client = null;
    private Game game = null;

    public Partner(Client client) {
        this.client = client;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    public void onReceive(String s) {
        Scanner scanner = new Scanner(s);
        String command = scanner.next();
        if(s.equals("START")) {
            //System.out.println("Partner requests to start the game");
            game.onReceiveStart();
        }
        else if(s.equals("FINISH")) {
            //System.out.println("Partner has finished the game");
            game.onReceiveFinish();
        }
        else if(command.equals("SHOT")) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            //System.out.println(String.format("Partner made a shot at %d %d", x , y));
            game.onReceiveShot(x, y);
        }
        else if(command.equals("MISS")) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            //System.out.println(String.format("Partner missed at %d %d", x , y));
            game.onReceiveMiss(x, y);
        }
        else if(command.equals("HIT")) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            //System.out.println(String.format("Partner hit at %d %d", x , y));
            game.onReceiveHit(x, y);
        }
        else if(command.equals("KILL")) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            game.onReceiveKill(x, y);
        }
    }

    public void sendStart() {
        client.write("START\n");
    }

    public void sendFinish() {
        client.write("FINISH\n");
    }

    public void sendShot(int x, int y) {
        client.write(String.format("SHOT %d %d\n", x, y));
    }

    public void sendMiss(int x, int y) {
        client.write(String.format("MISS %d %d\n", x, y));
    }

    public void sendHit(int x, int y) {
        client.write(String.format("HIT %d %d\n", x, y));
    }

    public void sendKill(int x, int y) {
        client.write(String.format("KILL %d %d\n", x, y));
    }
    
}
