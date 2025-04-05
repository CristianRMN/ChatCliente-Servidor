package service.hilos.servidor;


import service.metodosclases.MetodosServidor;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Hilo de sala de espera del chat
 * @author cristian
 * @version 1.0
 */
public class HiloSalaEsperaChat implements Runnable {

    //atributos de clase
    private final ObjectInputStream ois;
    private final ObjectOutputStream ous;
    private final Socket socket;
    private final MetodosServidor metodosServidor;
    private String auxNickName;

    /**
     * Constructor de la clase
     * @param ois el objeto ObjectInputStream
     * @param ous el objeto ObjectOutputStream
     * @param socket el objeto socket
     * @param metodosServidor el objeto metodos del servidor
     */
    public HiloSalaEsperaChat(ObjectInputStream ois, ObjectOutputStream ous, Socket socket, MetodosServidor metodosServidor) {
        this.ois = ois;
        this.ous = ous;
        this.socket = socket;
        this.metodosServidor = metodosServidor;
    }

    //hilo de la sala de espera del chat
    @Override
    public void run() {
        try {

            //recibimos datos del cliente
            String datosRecibidos = (String) ois.readObject();
            auxNickName = datosRecibidos;

            //mandamos el mensaje inicial
            ous.writeObject(mensajeInicialSalaDeEspera(datosRecibidos));

            //aumentamos la lista de clientes de la sala de espera
            metodosServidor.aumentarListaEsperaClientes(ous);
            System.out.println("Numero de clientes en espera: " + metodosServidor.getClientesSalaEsperaList().size());

            //en funcion de lo escrito por el usuario, respondemos diferente
            while(true){
                selectActionServer(ois, ous, datosRecibidos, metodosServidor);
            }
        } catch (IOException e) {
            System.out.println("Adios, " + auxNickName + " mas suerte la próxima vez XD");
            metodosServidor.decrementarListaEsperaClientes(ous);
            System.out.println("Numero de clientes en espera: " + metodosServidor.getClientesSalaEsperaList().size());
        } catch (ClassNotFoundException e) {
            System.out.println("Ups, error al castear la clase");
        } finally {
            try {
                System.out.println("Socket cerrado");
                ois.close();
                ous.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el socket: " + e.getMessage());
            }
        }

    }

    /**
     * Metodo para mandar el mensaje inicial al cliente en la sala de espera
     * @param nickName el alias
     * @return el string con el mensaje inicial
     */
    private String mensajeInicialSalaDeEspera(String nickName) {
        return "Hola " + nickName +
                " lamentablemente la sala tiene actualmente " +
                metodosServidor.getNumClientesActuales() +
                "/10 clientes" +
                "\nPara saber los usuarios conectados a la sala, introduzca (COUNT)" +
                "\nPara salir, pulsa introduce /bye";
    }

    /**
     * Metodo para saber que responderle al cliente en función de lo escrito
     * @param ois el objeto ObjectInputStream
     * @param ous el objeto ObjectOutputStream
     * @param nickname el alias del cliente
     * @param metodosServidor el objeto con los metodos del servidor
     * @throws IOException la excepcion a lanzar
     * @throws ClassNotFoundException excepcion de casteo
     */
    private void selectActionServer(ObjectInputStream ois, ObjectOutputStream ous, String nickname, MetodosServidor metodosServidor) throws IOException, ClassNotFoundException {
        String mensaje = (String) ois.readObject();
        System.out.println(mensaje);
        switch (mensaje) {
            case "COUNT":
                ous.writeObject("Actualmente hay " + metodosServidor.getNumClientesActuales() + " usuarios conectados");
                break;
            case "/bye":
                metodosServidor.decrementarListaEsperaClientes(ous);
            default:
                ous.writeObject(mensajeInicialSalaDeEspera(nickname));
        }
    }




}
