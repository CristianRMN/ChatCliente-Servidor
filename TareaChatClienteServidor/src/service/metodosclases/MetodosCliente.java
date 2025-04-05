package service.metodosclases;

import ui.SalaUI;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Clase con los metodos del cliente
 * @author cristian
 * @version 1.0
 */
public class MetodosCliente {

    //objeto scanner
    Scanner sc = new Scanner(System.in);


    /**
     * Metodo para preguntar el puerto e Ip
     * @param metodosGlobales objeto con los metodos globales
     * @return el array de String con la Ip y el puerto
     */
    public String[] preguntarIpYPuerto(MetodosGlobales metodosGlobales) {
        String[] respuestas = new String[2];

        String ip = JOptionPane.showInputDialog("Bienvenido a WhatHappen" +
                "\nIntroduce la dirección IP del servidor:");
        int puerto = metodosGlobales.solicitarPuerto();
        String auxPuerto = String.valueOf(puerto);
        respuestas[0] = ip;
        respuestas[1] = auxPuerto;
        return respuestas;
    }

    /**
     * Metodo para saber el alias del cliente
     * @return el String con el alias del cliente
     */
    public String establecerNickName() {
        return JOptionPane.showInputDialog("Introduce el nickName con el cual serás identificado en WhatHappen");
    }


    /**
     * Metodo para mandar mensajes continuamente
     * @param metodosCliente el objeto de metodos de cliente
     * @param out el ObjectOuputStream
     */
    public void mandarMensajes(MetodosCliente metodosCliente, ObjectOutputStream out) {
        while (true) {
            try {
                metodosCliente.AuxMandarMensaje(out);
            } catch (IOException e) {
                System.out.println("El servidor se desconectó");
                break;
            }
        }
    }

    /**
     * Metodo auxiliar para saber si el cliente ha escrito /bye para cerrar conexion o sigue mandando mensajes
     * @param objectOutputStream el objeto ObjectOutputStream
     * @throws IOException la excepcion a lanzar
     */
    public void AuxMandarMensaje(ObjectOutputStream objectOutputStream) throws IOException {
        String mensaje = sc.nextLine();
        if (cerrarConexion(mensaje)) {
            System.exit(0);
        } else {
            continuarConexion(objectOutputStream, mensaje);
        }
    }

    /**
     * Metodo para recibir mensajes
     * @param objectInputStream el ObjectOutPut del servidor
     * @throws IOException la excepcion a lanzar
     * @throws ClassNotFoundException la excepcion de casteo
     */
    public void recibirMensajes(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String mensaje = (String) objectInputStream.readObject();
        SalaUI.getInstance().agregarMensaje(mensaje);
    }

    /**
     * Metodo para saber si el cliente ha escrito el comando de cierre de conexion
     * @param mensaje el mensaje mandado
     * @return true o false dependiendo de si escribiste eso o no
     */
    private boolean cerrarConexion(String mensaje) {
        return mensaje.equalsIgnoreCase("/bye");
    }


    /**
     * Metodo que continua la conexion mandando mensajes al servidor
     * @param objectOutputStream el ObjectOutputStream al servidor
     * @param mensaje el mensaje a mandar
     * @throws IOException la excepcion a lanzar
     */
    private void continuarConexion(ObjectOutputStream objectOutputStream, String mensaje) throws IOException {
        objectOutputStream.writeObject(mensaje);
        objectOutputStream.reset();
        objectOutputStream.flush();
    }





}
