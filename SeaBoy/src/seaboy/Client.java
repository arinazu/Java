package seaboy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket = null;
    private final String serverAddr;
    private final int serverPort = 60111;
    private Partner partner = null;

    public Client(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    
    @Override
    public void run() {
        System.out.println("client started");

        while(true) {
            try {
                socket = new Socket(serverAddr, serverPort);
                String addr = socket.getLocalAddress().getHostAddress();
                int port = socket.getLocalPort();
                System.out.println("client connected " + addr + " : " + port);
                readline();
            } catch (IOException ex) {
                System.out.println("run(): " + ex);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex);
                break;
            }
        }
        
        System.out.println("client finished");
    }
    
    /*
    private void read() {
        byte[] b = new byte[256];
        while(true) {
            try {
                InputStream in = socket.getInputStream();
                int len = in.read(b);                               //block
                if(len > 0) {
                    String s = new String(b, 0, len);
                    //System.out.println("read len = " + len + " : " + s);
                    if(partner != null) partner.onReceive(s);
                }
            } catch (IOException ex) {
                System.out.println("read(): " + ex);
                break;
            }
        }
    }
    */
    
    private void readline() {
        try {
            InputStream in = socket.getInputStream();
            InputStreamReader r = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(r);
            
            while(true) {
                String s = br.readLine();
                //System.out.println("read len = " + s.length() + " : " + s);
                if(partner != null) partner.onReceive(s);
            }
        } catch (IOException ex) {
            System.out.println("readline(): " + ex);
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

    public void write(String s) {
        byte[] b = s.getBytes();
        write(b, b.length);
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
