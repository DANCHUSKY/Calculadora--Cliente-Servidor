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
/**
 *
 * @author daniel
 */


public class ManejadorDeCliente extends Thread {
    private final Socket cliente;

    public ManejadorDeCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {

            String operacion;
            out.println("Bienvenido al servidor de calculadora. Ingrese operaciones, 'historial' para ver el historial o 'salir' para terminar:");
            while ((operacion = in.readLine()) != null && !operacion.equalsIgnoreCase("salir")) {
                if (operacion.equalsIgnoreCase("historial")) {
                    for (String entry : Historial.getHistorialOperaciones()) {
                        out.println(entry);
                    }
                    out.println(); // Para indicar el final del historial
                } else {
                    Historial.addLog("Operación solicitada: " + operacion);
                    try {
                        double resultado = EvaluadorOperacion.evaluarOperacion(operacion);
                        Historial.addOperacion(operacion, resultado);
                        Historial.addLog("Resultado: " + resultado);
                        out.println("Resultado: " + resultado);
                    } catch (Exception e) {
                        out.println("Error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al manejar al cliente: " + e.getMessage());
        } finally {
            try {
                cliente.close();
                Historial.addLog("Cliente desconectado: " + cliente.getInetAddress().getHostAddress());
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión con el cliente: " + e.getMessage());
            }
        }
    }
}
