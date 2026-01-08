package server;

/**
 * Esta clase contiene la implementación real de la lógica del servidor.
 * Recibe la llamada del EchoServer y procesa los datos.
 */
public class EchoObjectSkeleton {

    /**
     * El método que realmente hace el "echo".
     * @param input El mensaje recibido del cliente.
     * @return El mensaje modificado para ser devuelto al cliente.
     */
    public String echo(String input) {
        // Aquí va la lógica de negocio.
        // Para este ejemplo, solo devolvemos el mensaje con un prefijo.
        return "Servidor dice: " + input;
    }
}