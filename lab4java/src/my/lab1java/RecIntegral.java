/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.lab1java;

import java.io.Serializable;

/**
 *
 * @author Вика
 */
public class RecIntegral implements Serializable{
    private String lower_limit;
    private String upper_limit;
    private String step;
    private String result;
    
    public RecIntegral(String lower_limit, String upperLimit, String step, String result) throws InvalidNumberException{
        double d_lower_limit = Double.parseDouble(lower_limit);
        double d_upperLimit = Double.parseDouble(upperLimit);
        double d_step = Double.parseDouble(step);
 
        if (d_lower_limit < 0.000001 || d_lower_limit > 1000000 || d_upperLimit < 0.000001 || d_upperLimit > 1000000 || d_step < 0.000001 || d_step > 1000000) {
            throw new InvalidNumberException("Значения должны быть в диапазоне от 0.000001 до 1000000");
        } 
        if (d_lower_limit > d_upperLimit){
            throw new InvalidNumberException("Значение верхней границы должно быть больше значения нижней границы");
        }
               
        this.lower_limit = lower_limit;
        this.upper_limit = upperLimit;
        this.step = step;
        this.result = result;
    }
    public String[] ret()
    {
        return new String[]{lower_limit,upper_limit,step,result };
    }
}
