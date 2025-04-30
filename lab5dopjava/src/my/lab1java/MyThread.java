
package my.lab1java;

import java.util.concurrent.Callable;


public class MyThread implements Callable<Double>{
    private double lowerLimit;
    private double upperLimit;
    private double step;
    private String name;
    private double res = 0;
    
    public MyThread(String name, double lowerLimit, double upperLimit, double step){
        this.name = name;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.step = step;
    }
    
        
    @Override
    public  Double call()  {
        double x = lowerLimit;
        while (x < upperLimit){
            double nextX = Math.min(x + step, upperLimit);              // Последний отрезок может быть меньше шага
            res += (Math.sin(x) + Math.sin(nextX)) * (nextX - x) / 2;
            System.out.println("res = " + res + " " + name);
            x = nextX; 
        }
        return res;
    }      
}


