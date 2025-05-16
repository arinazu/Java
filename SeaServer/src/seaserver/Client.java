package seaserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket = null;
    public boolean finished = false;

    public Client(Socket socket) {
        this.socket = socket;        
        String addr = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        System.out.println("client connected " + addr + " : " + port);
    }
    
    @Override
    public void run() {
        System.out.println("client started");
        read();
        System.out.println("client finished");
        finished = true;
    }
 
    private void read() {
        byte[] b = new byte[256];
        while(true) {
            try {
                InputStream in = socket.getInputStream();
                int len = in.read(b);                               //block
                if(len > 0) {
                    //write(b, len);
                    Server.onRead(this, b, len);
                }
            } catch (IOException ex) {
                System.out.println("read(): " + ex);
                break;
            }
        }
    }
    
    public void write(byte[] b, int len) {
        try {
            OutputStream out = socket.getOutputStream();
            out.write(b, 0, len);
        } catch (IOException ex) {
            System.out.println("write(): " + ex);
        }
    }
    
    /*
    public void close() {
        try {
            if(socket != null) 
                socket.close();                                     //unblock
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    */
}
