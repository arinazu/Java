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
    private String lower_limit;
    private String upperLimit;
    private String step;
    private String result;
    
    public RecIntegral(String lower_limit, String upperLimit, String step, String result){
        this.lower_limit = lower_limit;
        this.upperLimit = upperLimit;
        this.step = step;
        this.result = result;
    }
    public String[] ret()
    {
        return new String[]{lower_limit,upperLimit,step,result };
    }
}
