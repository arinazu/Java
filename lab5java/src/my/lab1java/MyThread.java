
package my.lab1java;

import javax.swing.JOptionPane;

/**
 *
 * @author Арина
 */
public class MyThread extends Thread{
    private double lowerLimit;
    private double upperLimit;
    private double step;
    private double res = 0;
    
    public MyThread(String name, double lowerLimit, double upperLimit, double step){
        super(name);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.step = step;
    }
    
    public double getResult() { 
        return res;
    }
    
    @Override
    public void run() {
        double x = lowerLimit;
        while (x < upperLimit){
            double nextX = Math.min(x + step, upperLimit);              // Последний отрезок может быть меньше шага
            res += (Math.sin(x) + Math.sin(nextX)) * (nextX - x) / 2;
            System.out.println("res = " + res + getName());
            x = nextX; 
        }
    }      
}


