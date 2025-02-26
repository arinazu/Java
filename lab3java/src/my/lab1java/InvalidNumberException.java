/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.lab1java;

/**
 *
 * @author Вика
 */
public class InvalidNumberException extends Exception {
    public InvalidNumberException() {
        super("Invalid number");
    }
    // Конструктор с сообщением
    public InvalidNumberException(String message) {
        super(message);
    }
}
