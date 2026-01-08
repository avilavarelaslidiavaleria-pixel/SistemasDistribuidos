package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import rmi.EchoInterface;

public class EchoClient {

    public static void main(String[] args) {
        try {
            // a. Generar una instancia del stub
            EchoInterface stub = new EchoObjectStub();

            // ¡IMPORTANTE! El stub por defecto apunta al puerto 7.
            // Debemos configurarlo para que apunte al puerto del servidor (1007).
            // Si el stub fuera una clase más genérica, se castearía: ((EchoObjectStub) stub).setHostAndPort(...)
            // ((EchoObjectStub) stub).setHostAndPort("localhost", 1007);
            ((EchoObjectStub) stub).setHostAndPort("100.95.250.70", 1007);
            
            // c. Preparar para leer del teclado
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            System.out.println("Cliente de Echo iniciado. Escribe un mensaje y presiona Enter.");
            System.out.println("Escribe 'exit' para terminar.");

            // b. Declarar un bucle infinito (que se romperá con 'exit')
            while (true) {
                System.out.print("> ");
                userInput = reader.readLine(); // c. Leer del teclado

                if (userInput == null || "exit".equalsIgnoreCase(userInput)) {
                    break; // Salir del bucle
                }

                // d. Invocar (llamar) al stub
                String serverResponse = stub.echo(userInput);

                // e. Imprimir en pantalla la respuesta del servidor
                System.out.println(serverResponse);
            }

            System.out.println("Cliente desconectado.");

        } catch (Exception e) {
            System.err.println("Excepción en el cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}