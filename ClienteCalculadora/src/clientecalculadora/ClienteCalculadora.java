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
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ClienteCalculadora {

    private static final String KEY = "1234567890123456"; // Clave de 16 bytes

    public static String encrypt(String data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    public static void main(String[] args) {
        final String direccionServidor = "localhost";
        final int puerto = 5003;

        try (Socket socket = new Socket(direccionServidor, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            String respuesta = decrypt(in.readLine());
            System.out.println(respuesta);
            System.out.println("Ingrese operaciones (ej: 2 + 2) o 'salir' para terminar:");
            System.out.println("Ingrese 'historial' para ver el historial de operaciones.");

            String operacion;
            while (!(operacion = scanner.nextLine()).equalsIgnoreCase("salir")) {
                out.println(encrypt(operacion));
                respuesta = decrypt(in.readLine());
                System.out.println(respuesta);
            }
            out.println(encrypt("salir"));

        } catch (IOException e) {
            System.out.println("No se pudo conectar al servidor. Asegúrese de que el servidor está ejecutándose.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
