package seaserver;

import java.io.IOException;

public class SeaServer {

    public static void main(String[] args) {
        System.out.println("main started");

        Server server = new Server();
        Thread serverThread = new Thread(server, "serverThread");
        serverThread.setDaemon(true);
        serverThread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
        
        try {
            System.out.println("press <enter> to exit");
            System.in.read();
        } catch (IOException ex) {
        }
        
        System.out.println("main finished");
    }
    
}
