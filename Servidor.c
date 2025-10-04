#include <stdio.h>
#include <stdlib.h>
#include <winsock2.h>
#include <windows.h>

#pragma comment(lib, "ws2_32.lib")

int main() {
    WSADATA wsa;
    SOCKET server_socket, client_socket;
    struct sockaddr_in server, client;
    int client_len;
    int numero, respuesta;

    printf("=== Servidor iniciado ===\n");

    // Inicializar Winsock
    if (WSAStartup(MAKEWORD(2,2), &wsa) != 0) {
        printf("Error al inicializar Winsock: %d\n", WSAGetLastError());
        return 1;
    }
    printf("Winsock inicializado correctamente.\n");

    // Crear socket
    if ((server_socket = socket(AF_INET, SOCK_STREAM, 0)) == INVALID_SOCKET) {
        printf("Error al crear socket: %d\n", WSAGetLastError());
        WSACleanup();
        return 1;
    }
    printf("Socket creado correctamente.\n");

    // Configurar dirección del servidor
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(8080);

    // Enlazar socket
    if (bind(server_socket, (struct sockaddr*)&server, sizeof(server)) == SOCKET_ERROR) {
        printf("Error en bind: %d\n", WSAGetLastError());
        closesocket(server_socket);
        WSACleanup();
        return 1;
    }
    printf("Bind realizado en puerto 8080.\n");

    // Escuchar conexiones
    if (listen(server_socket, 1) == SOCKET_ERROR) {
        printf("Error en listen: %d\n", WSAGetLastError());
        closesocket(server_socket);
        WSACleanup();
        return 1;
    }
    printf("Escuchando conexiones entrantes...\n");

    // Aceptar conexión
    client_len = sizeof(client);
    client_socket = accept(server_socket, (struct sockaddr*)&client, &client_len);
    if (client_socket == INVALID_SOCKET) {
        printf("Error en accept: %d\n", WSAGetLastError());
        closesocket(server_socket);
        WSACleanup();
        return 1;
    }
    printf("Cliente conectado!\n");

    // Bucle de comunicación
    while (1) {
        // Recibir número
        int bytes_recibidos = recv(client_socket, (char*)&numero, sizeof(numero), 0);

        if (bytes_recibidos <= 0) {
            printf("Cliente desconectado.\n");
            break;
        }

        // Convertir de network byte order
        numero = ntohl(numero);
        printf("Numero recibido: %d\n", numero);

        if (numero == 0) {
            printf("Cliente solicito terminar.\n");
            break;
        }

        // Incrementar y enviar respuesta
        respuesta = numero + 1;
        printf("Enviando respuesta: %d\n", respuesta);

        respuesta = htonl(respuesta);
        if (send(client_socket, (char*)&respuesta, sizeof(respuesta), 0) == SOCKET_ERROR) {
            printf("Error al enviar: %d\n", WSAGetLastError());
            break;
        }
    }

    // Limpiar
    closesocket(client_socket);
    closesocket(server_socket);
    WSACleanup();
    printf("Servidor terminado.\n");

    return 0;
}
