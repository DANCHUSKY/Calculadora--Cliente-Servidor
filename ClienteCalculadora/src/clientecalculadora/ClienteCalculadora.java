/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clientecalculadora;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author daniel
 */

public class ClienteCalculadora {

    public static void main(String[] args) {
        final String direccionServidor = "localhost";
        final int puerto = 5003;

        try (Socket socket = new Socket(direccionServidor, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            String respuesta = in.readLine();
            System.out.println(respuesta);
            System.out.println("Ingrese operaciones (ej: 2 + 2, sqrt 9, 2 ^ 3) o 'salir' para terminar:");
            System.out.println("Ingrese 'historial' para ver el historial de operaciones.");

            String operacion;
            while (!(operacion = scanner.nextLine()).equalsIgnoreCase("salir")) {
                if (operacion.equalsIgnoreCase("historial")) {
                    out.println(operacion);
                    respuesta = in.readLine();
                    while (respuesta != null && !respuesta.isEmpty()) {
                        System.out.println(respuesta);
                        respuesta = in.readLine();
                    }
                } else {
                    out.println(operacion);
                    respuesta = in.readLine();
                    System.out.println(respuesta);
                }
            }
            out.println(operacion); // Enviar "salir" al servidor para informar el fin de la sesión.

        } catch (IOException e) {
            System.out.println("No se pudo conectar al servidor. Asegúrese de que el servidor está ejecutándose.");
            e.printStackTrace();
        }
    }
}
