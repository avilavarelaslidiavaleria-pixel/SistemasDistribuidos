// Archivo: EchoObject.java
public class EchoObject {

    /**
     * Devuelve el mensaje recibido después de una pausa de 3 segundos.
     * Esta pausa simula una tarea que consume tiempo.
     * @param msg El mensaje del cliente.
     * @return El mismo mensaje precedido por "Echo: ".
     */
    public String echo(String msg) {
        try {
            // Imprime en la consola del servidor para ver qué hilo está trabajando.
            System.out.println("-> Petición '" + msg + "' recibida. Procesando durante 3 segundos...");
            
            // Hacemos que el hilo actual duerma por 3000 milisegundos (3 segundos).
            Thread.sleep(3000);
            
            System.out.println("   <- Petición '" + msg + "' completada. Enviando respuesta.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El hilo fue interrumpido: " + e.getMessage());
        }
        return "Echo: " + msg;
    }
}