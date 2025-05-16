package seaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerSocket serverSocket = null;
    private final int serverPort = 60111;
    private static final Client[] client = { null, null };

    @Override
    public void run() {
        System.out.println("server started");

        try {
            serverSocket = new ServerSocket(serverPort);
            String addr = serverSocket.getInetAddress().getHostAddress();
            int port = serverSocket.getLocalPort();
            System.out.println("server listen " + addr + " : " + port);
            
            while(true) {
                Socket socket = serverSocket.accept();              //block
                
                Client c = null;
                if((client[0] == null) || client[0].finished) {
                    c = new Client(socket);
                    client[0] = c;
                } else if((client[1] == null) || client[1].finished) {
                    c = new Client(socket);
                    client[1] = c;
                } else {
                    socket.close();
                }
                
                if(c != null) {
                    Thread clientThread = new Thread(c, "clientThread");
                    clientThread.setDaemon(true);
                    clientThread.start();
                }
            }
            
        } catch (IOException ex) {
            System.out.println("run(): " + ex);
        }
        
        System.out.println("server finished");
    }
    
    public static void onRead(Client c, byte[] b, int len) {
        if(c == client[0]) {
            if(client[1] != null)
                client[1].write(b, len);
        }
        else if(c == client[1]) {
            if(client[0] != null)
                client[0].write(b, len);
        }
    }
    
    /*
    public void close() {
        try {
            if(serverSocket != null) 
                serverSocket.close();                               //unblock
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    */
}
