/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.lab1java;

/**
 *
 * @author Вика
 */
public class RecIntegral {
    private String lowerLimit;
    private String upperLimit;
    private String step;
    private String result;
    
    public RecIntegral(String lowerLimit, String upperLimit, String step, String result){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.step = step;
        this.result = result;
    }
    public String[] ret()
    {
        return new String[]{lowerLimit,upperLimit,step,result };
    }
    
    public double calculate(){
        double res = 0;
        double lowerLimit = Double.parseDouble(this.lowerLimit);
        double upperLimit = Double.parseDouble(this.upperLimit);
        double step = Double.parseDouble(this.step);
        
        double x = lowerLimit;

        while (x < upperLimit) {
            double nextX = Math.min(x + step, upperLimit);              // Последний отрезок может быть меньше шага
            res += (Math.sin(x) + Math.sin(nextX)) * (nextX - x) / 2;
            x = nextX; 
        }
        
        this.result = Double.toString(res);
        return res;
    }
}
