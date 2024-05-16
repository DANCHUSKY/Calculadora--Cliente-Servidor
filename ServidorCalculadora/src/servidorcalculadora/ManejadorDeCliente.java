/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidorcalculadora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManejadorDeCliente extends Thread {
    private final Socket cliente;

    public ManejadorDeCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {

            out.println(ServidorCalculadora.encrypt("Bienvenido al servidor de calculadora. Ingrese operaciones, 'historial' para ver el historial o 'salir' para terminar:"));

            String operacion;
            while ((operacion = ServidorCalculadora.decrypt(in.readLine())) != null && !operacion.equalsIgnoreCase("salir")) {
                if (operacion.equalsIgnoreCase("historial")) {
                    for (String entry : Historial.getHistorialOperaciones()) {
                        out.println(ServidorCalculadora.encrypt(entry));
                    }
                    out.println(ServidorCalculadora.encrypt("")); // Para indicar el final del historial
                } else {
                    try {
                        double resultado = EvaluadorOperacion.evaluarOperacion(operacion);
                        Historial.addOperacion(operacion, resultado);
                        out.println(ServidorCalculadora.encrypt("Resultado: " + resultado));
                    } catch (Exception e) {
                        out.println(ServidorCalculadora.encrypt("Error: " + e.getMessage()));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al manejar al cliente: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cliente.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión con el cliente: " + e.getMessage());
            }
        }
    }
}
