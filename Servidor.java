/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Servidor; // paquete donde esta la clase servidor

// Servidor.java
import java.net.*;   // libreria para trabajar con sockets y conexiones de red
import java.io.*;    // libreria para manejar entrada y salida de datos

public class Servidor { // clase principal llamada Servidor
    public static void main(String[] args) throws IOException { // metodo main, puede lanzar excepciones de entrada/salida
        
        ServerSocket serverSocket = new ServerSocket(9999); // creo un socket servidor que escucha en el puerto 9999
        System.out.println("Servidor esperando cliente en el puerto 9999..."); // mensaje para saber que el server esta esperando

        Socket clientSocket = serverSocket.accept(); // el servidor se queda esperando hasta que un cliente se conecte
        System.out.println("¡Cliente conectado!"); // mensaje cuando ya se conecto un cliente

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // objeto para enviar datos al cliente
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // objeto para leer datos que envia el cliente

        String inputLine = in.readLine(); // leo una linea que mando el cliente
        System.out.println("Recibido del cliente: " + inputLine); // imprimo lo que recibi del cliente
        
        if (inputLine != null) { // si recibi algo valido
            out.println("Hola que tal"); // mando una respuesta fija al cliente
        }

        System.out.println("Respuesta enviada. Cerrando conexion."); // aviso de que ya se mando la respuesta y se cerrara la conexion
        out.close(); // cierro el flujo de salida
        in.close(); // cierro el flujo de entrada
        clientSocket.close(); // cierro el socket del cliente
        serverSocket.close(); // cierro el socket del servidor
    }
}
