import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 8080;
        
        System.out.println("=== Cliente iniciado ===");

        try (Socket socket = new Socket(host, puerto);
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
             DataInputStream entrada = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Conectado al servidor en " + host + ":" + puerto);
            System.out.println("Ingresa numeros enteros (0 para terminar):");

            int numero;
            do {
                System.out.print("Numero a enviar: ");
                numero = scanner.nextInt();

                // ENVIAR SIN CONVERSIÓN DE BYTE ORDER
                salida.writeInt(numero);  // ← Cambio aquí
                salida.flush();

                if (numero != 0) {
                    // RECIBIR SIN CONVERSIÓN DE BYTE ORDER  
                    int respuesta = entrada.readInt();  // ← Cambio aquí
                    System.out.println("Servidor respondio: " + respuesta);
                    System.out.println();
                }

            } while (numero != 0);

            System.out.println("Conexión terminada por el cliente.");

        } catch (ConnectException e) {
            System.err.println("Error: No se pudo conectar al servidor. Asegúrate de que el servidor esté ejecutándose.");
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}