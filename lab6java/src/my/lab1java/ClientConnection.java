
package my.lab1java;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientConnection {
    private Socket socket;
    private DataInputStream in;         // от клиента серверу
    private ObjectOutputStream out;     // от сервера клиенту
 
    public ClientConnection(Socket s){
        try{
            socket = s;
            in = new DataInputStream(s.getInputStream());       // получаем поток ввода, который позволяет читать поступающее через сокет
            out = new ObjectOutputStream(s.getOutputStream());
        }
        catch(IOException e){
            e.getMessage();        
        }  
    }
    public ObjectOutputStream getOut() {
        return out;
    }

    public DataInputStream getIn() {
        return in;
    }
}
