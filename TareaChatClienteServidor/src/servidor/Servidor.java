package servidor;

import ficheros.LecturaEscrituraJSON;
import service.hilos.servidor.HiloSalaEsperaChat;
import service.hilos.servidor.HiloServidorSala;
import service.metodosclases.MetodosGlobales;
import service.metodosclases.MetodosServidor;
import servidor.comandos.Comandos;
import servidor.comandos.api.ApiComandoConnection;
import servidor.comandos.help.ComandoHelp;
import servidor.dm.UsuariosObjects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase servidor de la app
 * @author cristian
 * @version 1.0
 */
public class Servidor {

    public static void main(String[] args) throws IOException {

        //llamamos a los objetos pertinentes
        MetodosServidor metodosServidor = new MetodosServidor();
        MetodosGlobales metodosGlobales = new MetodosGlobales();
        LecturaEscrituraJSON leJson = new LecturaEscrituraJSON();
        Comandos comandos = new Comandos();
        ApiComandoConnection aComandoConnection = new ApiComandoConnection();
        ComandoHelp comandoHelp = new ComandoHelp();
        UsuariosObjects userDm = new UsuariosObjects();

        //establecemos el comando matar servidor
        comandos.setmatar_servidor(comandos.getNumeroAleatorio());


        //iniciamos el servicio
        try (ServerSocket serverSocket = new ServerSocket(metodosGlobales.solicitarPuerto())) {
            metodosServidor.mensajeNoClientesInicioEnChat(metodosServidor.getNumClientesActuales());
            while (true) {

                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());


                /*
                 Si hay 10 o menos clientes, esntras a la sala, si no vas a la de espera
                 */
                if(metodosServidor.checkLimitClientesChat()){
                    metodosServidor.aumentarClientes();
                    Thread hilo = new Thread(new HiloServidorSala(socket, metodosServidor, ois, ous, leJson, comandos, aComandoConnection, comandoHelp, userDm));
                    hilo.start();
                }
                else{
                    Thread hiloEspera = new Thread(new HiloSalaEsperaChat(ois, ous, socket, metodosServidor));
                    hiloEspera.start();
                }


            }
        } catch (IOException e) {
            System.out.println("Ups, el servidor se cayó");
        }

    }
}