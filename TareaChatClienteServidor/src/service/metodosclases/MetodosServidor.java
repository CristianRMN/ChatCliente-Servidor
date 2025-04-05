package service.metodosclases;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la lógica del servidor, incluyendo la gestión de clientes
 * conectados, clientes en la sala de espera y mensajes informativos sobre el estado del servidor.
 * @author cristian
 * @version 1.0
 */
public class MetodosServidor {

    //atributos de clase
    private int numClientesActuales = 0;
    private boolean inicioServidor = false;
    private final List<ObjectOutputStream> clientesSalaList = new ArrayList<>();
    private final List<ObjectOutputStream> clientesSalaEsperaList = new ArrayList<>();

    /**
     * Aumenta el número de clientes conectados al servidor de manera segura.
     */
    public synchronized void aumentarClientes() {
        numClientesActuales++;
    }

    /**
     * Disminuye el número de clientes conectados al servidor de manera segura.
     */
    public synchronized void decrementarClientes() {
        numClientesActuales--;
    }

    /**
     * Obtiene el número actual de clientes conectados al servidor.
     *
     * @return el número de clientes actualmente conectados.
     */
    public synchronized int getNumClientesActuales() {
        return numClientesActuales;
    }

    /**
     * Agrega un cliente a la lista de espera de manera segura.
     *
     * @param clienteEspera el cliente a añadir a la lista de espera.
     */
    public synchronized void aumentarListaEsperaClientes(ObjectOutputStream clienteEspera){
        clientesSalaEsperaList.add(clienteEspera);
    }

    /**
     * Elimina un cliente de la lista de espera de manera segura.
     *
     * @param clienteEspera el cliente a eliminar de la lista de espera.
     */
    public synchronized void decrementarListaEsperaClientes(ObjectOutputStream clienteEspera){
        clientesSalaEsperaList.remove(clienteEspera);
    }

    /**
     * Obtiene la lista de clientes en la sala de espera.
     *
     * @return una lista con los clientes en la sala de espera.
     */
    public synchronized List<ObjectOutputStream> getClientesSalaEsperaList() {
        return clientesSalaEsperaList;
    }

    /**
     * Comprueba si aún se pueden conectar más clientes al chat.
     *
     * @return true si el número de clientes actuales es menor al máximo permitido, false en caso contrario.
     */
    public synchronized boolean checkLimitClientesChat() {
        int maxClientes = 10;
        return numClientesActuales < maxClientes;
    }

    /**
     * Agrega un cliente a la sala de chat de manera segura.
     *
     * @param cliente el cliente a añadir a la sala de chat.
     */
    public synchronized void aumentarListaClientes(ObjectOutputStream cliente) {
        clientesSalaList.add(cliente);
    }

    /**
     * Elimina un cliente de la sala de chat de manera segura.
     *
     * @param cliente el cliente a eliminar de la sala de chat.
     */
    public synchronized void decrementarListaClientes(ObjectOutputStream cliente) {
        clientesSalaList.remove(cliente);
    }

    /**
     * Obtiene la lista de clientes conectados en la sala de chat.
     *
     * @return una lista con los clientes en la sala de chat.
     */
    public synchronized List<ObjectOutputStream> getClientesSalaList() {
        return clientesSalaList;
    }


    /**
     * Muestra un mensaje informativo sobre la cantidad de usuarios conectados al servidor.
     * Si es la primera vez que se inicia el servidor, se muestra un mensaje indicando que el servidor ha arrancado correctamente.
     *
     * @param numero el número de usuarios actualmente conectados.
     */
    public synchronized void mensajeNoClientesInicioEnChat(int numero){
        if(numero == 0 && !inicioServidor){
            System.out.println("Servidor de WhatHappen iniciado correctamente");
            System.out.println("Ningún usuario conectado");
            inicioServidor = true;
        }
        else{
            if(numero == 0){
                System.out.println("Ningún usuario conectado");
            }
            else{
                System.out.println("Actualmente hay " + numero + " usuarios conectados");
            }
        }
    }




}