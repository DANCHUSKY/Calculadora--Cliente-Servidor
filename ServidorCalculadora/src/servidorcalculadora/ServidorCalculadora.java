/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package servidorcalculadora;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author daniel
 */




public class ServidorCalculadora {
    static final int puerto = 5003;

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor Calculadora iniciado, esperando por clientes...");

            while (true) {
                Socket cliente = servidor.accept();
                Historial.addLog("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                new ManejadorDeCliente(cliente).start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
