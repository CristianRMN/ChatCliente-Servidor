package cliente;

import service.hilos.cliente.HiloRecibirMensajes;
import service.metodosclases.MetodosCliente;
import service.metodosclases.MetodosGlobales;
import ui.SalaUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Clase cliente de la aplicacion
 * @author cristian
 * @version 1.0
 */
public class Cliente {

    /**
     * Metodo de conexion con el servidor del cliente
     * @param metodosCliente la clase con los metodos del cliente
     * @param metodosGlobales la clase con los metodos comunes a cliente y servidor
     */
    public static void conexionCliente(MetodosCliente metodosCliente, MetodosGlobales metodosGlobales) {

        //llamamos a un metodo para recoger el puerto y la IP
        String[] IpPuerto = metodosCliente.preguntarIpYPuerto(metodosGlobales);

        //creamos el socket con los datos recibidos
        try (Socket socket = new Socket(IpPuerto[0], Integer.parseInt(IpPuerto[1]))) {

            //preguntamos por el alias a usar en la app
            String nickName = metodosCliente.establecerNickName();
            System.out.println("Conectado a la sala de chat" + "\nBienvenido: " + nickName);

            //objetos input y output y mandamos el nick al servidor
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(nickName);

            //llamamos a la UI para recibir el nick
            SalaUI.getInstance().establecerLabel(nickName.toUpperCase());

            //creamos el hilo para recibir mensajes
            Thread hiloRecibirMensajes = new Thread(new HiloRecibirMensajes(in, metodosCliente));

            hiloRecibirMensajes.start();

            //llamamos al metodo para mandar mensajes continuamente
            metodosCliente.mandarMensajes(metodosCliente, out);

        } catch (IOException e) {
            System.out.println("Ups, no se encontró el servidor");
        }
    }


    /**
     * Metodo main para ejecutar al cliente
     * @param args los argumentos
     */
    public static void main(String[] args) {
        MetodosCliente metodosCliente1 = new MetodosCliente();
        MetodosGlobales metodosGlobales1 = new MetodosGlobales();
        Cliente.conexionCliente(metodosCliente1, metodosGlobales1);
    }


}
