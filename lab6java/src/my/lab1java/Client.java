
package my.lab1java;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


public class Client {   
    public static void main(String[] args) {
        String serverAddress = "localhost";     // клиент и сервер на одном компьютере
        int port = 12345;
        System.out.println("Client start");
        
        try (Socket socket = new Socket(serverAddress, port);
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());      // от сервера клиенту
                DataOutputStream out = new DataOutputStream(socket.getOutputStream())){     // от клиента серверу
            
            System.out.println("Client connected to server!");
            while(true){
                try{
                    RecIntegral rec = (RecIntegral) in.readObject();            
                    double lowerLimit = Double.parseDouble(rec.ret()[0]);
                    double upperLimit = Double.parseDouble(rec.ret()[1]);
                    double step = Double.parseDouble(rec.ret()[2]);
                    double result = Double.parseDouble(rec.ret()[3]);
                    
                    System.out.println("lowerLimit = " + lowerLimit);
                    System.out.println("upperLimit = " + upperLimit);
                    System.out.println("step = " + step);
                    System.out.println("result = " + result);
                    
                    double x = lowerLimit;
                    while (x < upperLimit){
                        double nextX = Math.min(x + step, upperLimit);              // Последний отрезок может быть меньше шага
                        result += (Math.sin(x) + Math.sin(nextX)) * (nextX - x) / 2;
                        x = nextX;   
                    }
                    System.out.println("res = " + result);
                    System.out.println("...........................................................");
                    out.writeDouble(result);
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }     
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }        
    }   
}
