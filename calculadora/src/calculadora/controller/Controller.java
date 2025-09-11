/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadora.controller;

import calculadora.enums.OperationEnum;

/**
 *
 * @author denis
 */
public class Controller {

    private double result;

    public Controller() {
        this.result = 0;
    }

    public double getResult() {
        return result;
    }

    public void clearResult() {
        result = 0.0;
    }

    public void calculate(OperationEnum operation, double value) {
        switch (operation) {
            case SUM:
                result += value;
                break;
            case SUBTRACT:
                result = result == 0 ? value : result - value;
                break;
            case MULTIPLY:
                if (result == 0) {
                    result = value;
                } else {
                    result *= value;
                }
                break;
            case DIVIDE:
                if (value == 0) {
                    throw new ArithmeticException("Divisão por zero não é permitida");
                }
                if (result == 0) {
                    result = value;
                } else {
                    result /= value;
                }
                break;
            default:
                throw new UnsupportedOperationException("Operação não suportada: " + operation);
        }
    }
}
