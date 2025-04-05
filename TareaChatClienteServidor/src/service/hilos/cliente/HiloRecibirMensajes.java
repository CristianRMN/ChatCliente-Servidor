package service.hilos.cliente;

import service.metodosclases.MetodosCliente;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Hilo para recibir mensajes del cliente
 * @author cristian
 * @version 1.0
 */
public class HiloRecibirMensajes implements Runnable {

    //atributos de clase
    private final ObjectInputStream ois;
    private final MetodosCliente metodosCliente;

    /**
     * Constructor de la clase
     * @param ois el objeto objectOutpitStream
     * @param metodosCliente el objeto de metodos de cliente
     */
    public HiloRecibirMensajes(ObjectInputStream ois, MetodosCliente metodosCliente) {
        this.ois = ois;
        this.metodosCliente = metodosCliente;
    }

    //hilo para ejecutar el metodo de recibir mensajes
    @Override
    public void run() {
        try{
            while(true){
                metodosCliente.recibirMensajes(ois);
            }
        }catch(IOException e){
            System.out.println("Conexion cerrada con el servidor");
        }catch(ClassNotFoundException e){
            System.out.println("Clase no encontrada");
        }
    }
}
