/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.lab1java;

//import java.io.Serializable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author Вика
 */
public class RecIntegral implements Externalizable{
    private String lower_limit;
    private String upper_limit;
    private String step;
    private String result;
    
    // конструктор без параметров для десериализации
    public RecIntegral() {}
    // конструктор с параметрами
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
    
    // реализация метода writeExternal (сохранение(запись) в файл)
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(lower_limit);
        out.writeUTF(upper_limit);
        out.writeUTF(step);
        out.writeUTF(result);
    }

    // реализация метода readExternal (загрузка(чтение) из файла)
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        lower_limit = in.readUTF();
        upper_limit = in.readUTF();
        step = in.readUTF();
        result = in.readUTF();
    }
        
    public String[] ret()
    {
        return new String[]{lower_limit,upper_limit,step,result};
    }
}
