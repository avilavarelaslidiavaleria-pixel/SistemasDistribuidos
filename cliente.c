/* cliente.c */
#include <stdio.h>      // libreria para entrada y salida estandar
#include <stdlib.h>     // libreria para funciones generales como exit
#include <string.h>     // libreria para manejo de cadenas
#include <unistd.h>     // libreria para funciones de unix como close y read
#include <sys/socket.h> // libreria para manejo de sockets
#include <netinet/in.h> // libreria para estructuras de direcciones de red
#include <arpa/inet.h>  // libreria para conversion de direcciones ip

#define PORT 9999          // puerto en el que se conectara al servidor
#define BUFFER_SIZE 1024   // tamanio del buffer para recibir datos

int main(int argc, char const *argv[]) {
    int sock = 0; // descriptor del socket
    struct sockaddr_in serv_addr; // estructura que guarda la info del servidor
    char buffer[BUFFER_SIZE] = {0}; // buffer para recibir datos del servidor
    char *mensaje = "Hola\n"; // mensaje que se enviara, el \n es importante como delimitador

    // creo el socket tcp
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\n Error en la creacion del socket \n");
        return -1;
    }

    serv_addr.sin_family = AF_INET; // familia de direcciones (ipv4)
    serv_addr.sin_port = htons(PORT); // puerto en orden de bytes de red

    // convierto la ip en formato texto a binario
    if(inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr) <= 0) {
        printf("\nDireccion invalida / Direccion no soportada \n");
        return -1;
    }

    // intento conectarme al servidor
    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        printf("\nConexion Fallida \n");
        return -1;
    }

    // mando mensaje al servidor
    send(sock, mensaje, strlen(mensaje), 0);
    printf("Mensaje enviado al servidor: %s", mensaje);

    // leo la respuesta que me manda el servidor
    read(sock, buffer, BUFFER_SIZE);
    printf("Respuesta del servidor: %s\n", buffer);

    close(sock); // cierro el socket
    return 0; // fin del programa
}
