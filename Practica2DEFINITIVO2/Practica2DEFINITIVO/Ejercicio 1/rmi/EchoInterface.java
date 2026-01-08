package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Define el contrato para el servicio de Echo.
 * Cualquier método que pueda ser llamado remotamente debe estar aquí.
 */
public interface EchoInterface extends Remote {
    /**
     * Recibe un String y lo devuelve, posiblemente modificado por el servidor.
     * @param input El mensaje a enviar.
     * @return La respuesta del servidor.
     * @throws RemoteException Si ocurre un error de comunicación.
     */
    String echo(String input) throws RemoteException;
}