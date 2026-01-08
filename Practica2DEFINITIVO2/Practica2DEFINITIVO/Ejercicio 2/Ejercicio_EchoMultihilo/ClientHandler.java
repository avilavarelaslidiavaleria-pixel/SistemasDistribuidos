// Archivo: ClientHandler.java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final EchoObject echoObject;

    public ClientHandler(Socket socket, EchoObject echoObject) {
        this.clientSocket = socket;
        this.echoObject = echoObject;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            // Lee mensajes del cliente hasta que la conexión se cierre (readLine() devuelva null)
            while ((inputLine = in.readLine()) != null) {
                // Llama al objeto de servicio para procesar la petición
                String response = echoObject.echo(inputLine);
                // Envía la respuesta de vuelta al cliente
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Error de comunicación con el cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
            }
        }
    }
}