/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidorcalculadora;
import java.util.*;
/**
 *
 * @author daniel
 */



public class EvaluadorOperacion {

    public static double evaluarOperacion(String operacion) throws Exception {
        List<String> expresion = tokenizar(operacion);
        List<String> salidaPostfija = shuntingYard(expresion);
        return evaluarPostfija(salidaPostfija);
    }

    private static List<String> tokenizar(String operacion) {
        List<String> tokens = new ArrayList<>();
        StringBuilder numero = new StringBuilder();

        for (int i = 0; i < operacion.length(); i++) {
            char c = operacion.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                numero.append(c);
            } else {
                if (numero.length() > 0) {
                    tokens.add(numero.toString());
                    numero.setLength(0);
                }

                if (c == 's') {
                    if (operacion.startsWith("sqrt", i)) {
                        tokens.add("sqrt");
                        i += 3; // Skip the 'qrt'
                    }
                } else if (c != ' ') {
                    tokens.add(String.valueOf(c));
                }
            }
        }

        if (numero.length() > 0) {
            tokens.add(numero.toString());
        }

        return tokens;
    }

    private static List<String> shuntingYard(List<String> tokens) throws Exception {
        List<String> salida = new ArrayList<>();
        Deque<String> pila = new ArrayDeque<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) { // Número
                salida.add(token);
            } else if ("+-*/^".contains(token)) { // Operador
                while (!pila.isEmpty() && esMayorPrecedencia(pila.peek(), token)) {
                    salida.add(pila.pop());
                }
                pila.push(token);
            } else if ("sqrt".equalsIgnoreCase(token)) {
                pila.push(token);
            } else if ("(".equals(token)) {
                pila.push(token);
            } else if (")".equals(token)) {
                while (!"(".equals(pila.peek())) {
                    salida.add(pila.pop());
                }
                pila.pop(); // Eliminar el '('
                if (!pila.isEmpty() && "sqrt".equalsIgnoreCase(pila.peek())) {
                    salida.add(pila.pop());
                }
            } else {
                throw new Exception("Token inválido: " + token);
            }
        }

        while (!pila.isEmpty()) {
            salida.add(pila.pop());
        }

        return salida;
    }

    private static boolean esMayorPrecedencia(String op1, String op2) {
        if ("^".equals(op2)) return false;
        if ("sqrt".equalsIgnoreCase(op1)) return false;
        return (op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"));
    }

    private static double evaluarPostfija(List<String> tokens) throws Exception {
        Deque<Double> pila = new ArrayDeque<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) { // Número
                pila.push(Double.valueOf(token));
            } else if (token.equals("sqrt")) {
                double numero = pila.pop();
                if (numero < 0) {
                    throw new Exception("Número negativo dentro de una raíz cuadrada.");
                }
                pila.push(Math.sqrt(numero));
            } else {
                double segundo = pila.pop();
                double primero = pila.isEmpty() ? 0 : pila.pop();

                switch (token) {
                    case "+":
                        pila.push(primero + segundo);
                        break;
                    case "-":
                        pila.push(primero - segundo);
                        break;
                    case "*":
                        pila.push(primero * segundo);
                        break;
                    case "/":
                        if (segundo == 0) throw new Exception("División por cero.");
                        pila.push(primero / segundo);
                        break;
                    case "^":
                        pila.push(Math.pow(primero, segundo));
                        break;
                    default:
                        throw new Exception("Operador inválido: " + token);
                }
            }
        }

        return pila.pop();
    }
}
