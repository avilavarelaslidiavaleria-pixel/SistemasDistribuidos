// Archivo: EchoMultiServer.java
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoMultiServer {
    public static void main(String[] args) {
        int port = 8888; // Puerto en el que escuchará el servidor
        EchoObject echoObject = new EchoObject(); // Una única instancia del objeto de servicio

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor de Echo Multihilo iniciado en el puerto " + port);
            System.out.println("Esperando conexiones de clientes...");

            // Bucle infinito para aceptar conexiones de clientes continuamente
            while (true) {
                // accept() bloquea la ejecución hasta que un cliente se conecta
                Socket clientSocket = serverSocket.accept();
                System.out.println("¡Cliente conectado! -> " + clientSocket.getInetAddress().getHostAddress());

                // Crea un nuevo hilo para manejar la comunicación con este cliente
                // y le pasa el socket y la referencia al objeto de servicio.
                new ClientHandler(clientSocket, echoObject).start();
            }
        } catch (IOException e) {
            System.err.println("Error: No se pudo escuchar en el puerto " + port);
            System.err.println(e.getMessage());
        }
    }
}