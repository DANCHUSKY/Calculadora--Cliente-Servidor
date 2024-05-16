/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidorcalculadora;

import java.util.Deque;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Historial {
    private static Deque<String> historialOperaciones = new ConcurrentLinkedDeque<>();
    private static List<String> logs = Collections.synchronizedList(new ArrayList<>());

    public static Deque<String> getHistorialOperaciones() {
        return historialOperaciones;
    }

    public static List<String> getLogs() {
        return logs;
    }

    public static void addOperacion(String operacion, double resultado) {
        historialOperaciones.add(operacion + " = " + resultado);
        if (historialOperaciones.size() > 100) { // Limitar historial a 100 entradas
            historialOperaciones.pop();
        }
    }

    public static void addLog(String log) {
        synchronized (logs) {
            logs.add(log);
        }
    }
}
