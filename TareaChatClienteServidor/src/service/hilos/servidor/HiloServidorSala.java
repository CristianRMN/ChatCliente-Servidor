package service.hilos.servidor;

import ficheros.LecturaEscrituraJSON;
import service.metodosclases.MetodosServidor;
import servidor.comandos.Comandos;
import servidor.comandos.api.ApiComandoConnection;
import servidor.comandos.help.ComandoHelp;
import servidor.dm.UsuariosObjects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * Hilo de la sala del servidor
 * @author cristian
 * @version 1.0
 */
public class HiloServidorSala implements Runnable {

    //atributos de clase
    private final Socket socket;
    private String auxErrorNombre;
    private final MetodosServidor metodosServidor;
    private final ObjectInputStream ois;
    private final ObjectOutputStream ous;
    private final LecturaEscrituraJSON leJson;
    private final Comandos comandos;
    private final ApiComandoConnection api;
    private final ComandoHelp comandoHelp;
    private final UsuariosObjects userDm;

    //path de la bandeja de mensajes del JSON
    private static final String PATH_JSON = "mensajesWhatHappen.json";

    /**
     * Constructor de la clase
     * @param socket el socket del servidor
     * @param metodosServidor el objeto metodos del servidor
     * @param ois el objeto ObjectInputStream
     * @param ous el objeto ObjectOutputStream
     * @param leJson el objeto de lectura del JSON
     * @param comandos el objeto de metodos de comandos
     * @param api el objeto de conexion con la api del tiempo
     * @param comandoHelp el objeto del comando help
     * @param userDm el objeto de mandar dms
     */
    public HiloServidorSala(Socket socket, MetodosServidor metodosServidor, ObjectInputStream ois, ObjectOutputStream ous, LecturaEscrituraJSON leJson, Comandos comandos, ApiComandoConnection api, ComandoHelp comandoHelp, UsuariosObjects userDm) {
        this.socket = socket;
        this.metodosServidor = metodosServidor;
        this.ois = ois;
        this.ous = ous;
        this.leJson = leJson;
        this.comandos = comandos;
        this.api = api;
        this.comandoHelp = comandoHelp;
        this.userDm = userDm;
    }

    //hilo de la sala del chat
    @Override
    public void run() {
        try{

            //aumentamos el numero de clientes
            metodosServidor.aumentarListaClientes(ous);

            //recibimos el alias del jugador y lo guardamos
            String datosRecibidos = (String) ois.readObject();
            auxErrorNombre = datosRecibidos;
            System.out.println("Nuevo cliente conectado: " + datosRecibidos);


            userDm.putNewUsuariosDms(datosRecibidos, ous);

            //mandamos un mensaje de que el cliente X se ha conectado
            mandarMensajeConexionTodosClientes(datosRecibidos);

            //mandamos la badeja de entrada solo al cliente que se conecto, no a todos
            mandarTodaBandejaMensajeClientes(leJson, ous);

            System.out.println("Actualmente hay " + metodosServidor.getNumClientesActuales() + " usuarios conectados");

            //hacemos que, si el servidor recibe algo, lo mande a todos
            while(true) {
                auxMandarMensaje(ois, datosRecibidos, leJson, comandos, api, comandoHelp, userDm);
            }

            /*
             Si se desconecta un cliente:
             1. decrementamos el numero de clientes
             2. borramos el alias del jugador del hashMap
             3. borramos el ObjectOutpit para no mandar mensajes a ese cliente
             4. mandamos el mensaje de desconexion a todos los clientes
             */
        } catch (IOException e) {
            System.out.println(auxErrorNombre + " dejó este chat");
            metodosServidor.decrementarClientes();
            userDm.deleteUsuariosDms(auxErrorNombre);
            metodosServidor.mensajeNoClientesInicioEnChat(metodosServidor.getNumClientesActuales());
            mandarMensajeDesconexionTodosClientes(auxErrorNombre);
        } catch (ClassNotFoundException e) {
            System.out.println("Ups, clase no encontrada");
        } finally {
            try {
                System.out.println("Socket cerrado");
                metodosServidor.decrementarListaClientes(ous);
                ois.close();
                ous.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el socket: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para mandar bandeja de entrada a todos los clientes
     * @param leJson el objeto para leer el JSON
     * @param ous el objeto ObjectOutputStream
     * @throws IOException la excepcion a lanzar
     */
    private void mandarTodaBandejaMensajeClientes(LecturaEscrituraJSON leJson, ObjectOutputStream ous)throws IOException{

        String [] mensajes = leJson.readMensajesMaps(PATH_JSON);
        if(mensajes != null){
            for(String mensaje : mensajes){
                ous.writeObject(mensaje);
            }
        }
    }

    /**
     * Metodo para guardar el mensaje en la bandeja de mensajes del JSON
     * @param nickName el alias del jugador
     * @param mensaje el mensaje que escribio
     * @param leJson el objetoJSON para registar el mensaje
     */
    private void guardarMensajeEnBandejaEntrada(String nickName, String mensaje, LecturaEscrituraJSON leJson){
        String [] newMensaje = {nickName, mensaje};
        leJson.registrarMensajesJson(PATH_JSON, newMensaje);
    }

    /**
     * Metodo auxiliar para mandar mensajes a todos los clientes
     * @param ois el ObjetoObjectInputStream
     * @param datosRecibidos los datos recibidos
     * @param leJson el Objeto para leer el fichero JSON
     * @param comandos el objeto con los metodos de comandos
     * @param api el objeto para conectarse a una api
     * @param comandoHelp el objeto de comandos de ayuda
     * @param userDM el objeto para mandar dms
     * @throws IOException la excepcion a lanzar
     * @throws ClassNotFoundException excepcion de casteo
     */
    private void auxMandarMensaje(ObjectInputStream ois, String datosRecibidos, LecturaEscrituraJSON leJson, Comandos comandos, ApiComandoConnection api, ComandoHelp comandoHelp, UsuariosObjects userDM) throws IOException, ClassNotFoundException {
        String mensajes = (String) ois.readObject();
        System.out.println(datosRecibidos + ": " + mensajes);
        guardarMensajeEnBandejaEntrada(datosRecibidos, mensajes, leJson);
        manageBehaviorMensajes(datosRecibidos, mensajes, comandos, api, leJson, comandoHelp, userDM);
    }

    /**
     * Metodo para mandar mensaje de conexion a todos los clientes
     * @param datosRecibidos el alias del jugador conectado
     */
    private void mandarMensajeConexionTodosClientes(String datosRecibidos)  {
        mandarMensajesTodosClientes(datosRecibidos + " acaba de conectarse a la sala");
    }

    /**
     * Metodo para mandar mensaje de desconexion a todos los clientes
     * @param datosRecibidos el alias del jugador desconectado
     */
    private void mandarMensajeDesconexionTodosClientes(String datosRecibidos){
        mandarMensajesTodosClientes(datosRecibidos + " dejó este chat");
    }

    /**
     * Metodo que mandará una respuesta u otra dependiendo de lo escrito por el usuario
     * @param nickName el alias del cliente
     * @param mensaje el mensaje a recibir
     * @param comando el objeto de metodos de comandos
     * @param api el objeto de conexion con la api
     * @param lefJSON el objeto de lectura de ficheros JSOn
     * @param comandoHelp el objeto de comandos de ayuda
     * @param userDm el objeto de mandar dms
     */
    public void manageBehaviorMensajes(String nickName, String mensaje, Comandos comando, ApiComandoConnection api, LecturaEscrituraJSON lefJSON, ComandoHelp comandoHelp, UsuariosObjects userDm){

        /*
          Explicacion:
          1. Si el usuario manda algún mensaje que signifique un comando
          2. Por ejemplo -> PING, TIME_(ZONA PARA SABER LA HORA)...
          3. El servidor le responde con un mensaje relacionado al comando mandado
          4. Si el servidor recibe un @(alias) y coincide con alguno conectad0
          5. mandará un Dm a ese usuario
          6. Si no, mandará el mensaje recibido a todos los clientes conectados
         */

        if(mensaje.equalsIgnoreCase(comando.getmatar_servidor())){
            System.exit(0);
        }
        else if(mensaje.equalsIgnoreCase("PING")){
            mandarMensajesTodosClientes(comandos.getMensaje_pong());
        }
        else if(mensaje.startsWith("TIME_")){
            String fechaHora = comando.checkMensajeDate(mensaje);
            mandarMensajesTodosClientes(Objects.requireNonNullElse(fechaHora, "Zona desconocida"));
        }
        else if(mensaje.equalsIgnoreCase("COUNT")){
            mandarMensajesTodosClientes("Actualmente hay " + metodosServidor.getNumClientesActuales() + " usuarios conectados");
        }
        else if(mensaje.equalsIgnoreCase("JOKE")){
            mandarMensajesTodosClientes(comando.getChisteAleatorio());
        }
        else if(mensaje.startsWith("REVERSE")){
            mandarMensajesTodosClientes(comando.getTextoAlReves(mensaje));
        }
        else if(mensaje.startsWith("WEATHER_")){
            mandarMensajesTodosClientes(comando.getTiempoApi(mensaje, api, lefJSON));
        }
        else if (mensaje.equalsIgnoreCase("HELP")) {
            mandarMensajesTodosClientes(comandoHelp.comandoGeneralHelp());
        }
        else if(mensaje.startsWith("@")){
            ObjectOutputStream userTarget = setDmsUsuarios(mensaje, userDm);
            if(userTarget != null){
                mandarMensajesAlosDmsDeClientes(nickName +  ": " +  mensaje, userTarget);
            }
            else{
                mandarMensajesTodosClientes(nickName +  ": " + mensaje);
            }
        }
        else{
            mandarMensajesTodosClientes(nickName + ": " + mensaje);
        }
    }

    /**
     * Metodo para mandar Dms a clientes
     * @param mensaje el mensaje a mandar
     * @param userDm el objeto de mandar dms
     * @return un objectOut para saber el destinatario
     */
    public ObjectOutputStream setDmsUsuarios(String mensaje, UsuariosObjects userDm){
            String usuario = userDm.separateArobaMensaje(mensaje);
            return userDm.getOutPutByNickName(usuario);
    }


    /**
     * Metodo para mandar mensajes a todos los clientes
     * @param mensaje el mensaje a mandar
     */
    public  void mandarMensajesTodosClientes(String mensaje){
        for(ObjectOutputStream ous : metodosServidor.getClientesSalaList()){
            try{
                if(ous != null){
                    ous.writeObject(mensaje);
                    ous.flush();
                }
            } catch (IOException e) {
                System.out.println("Ups, error al mandar los mensajes a los clientes");
            }
        }
    }

    /**
     * Metodo para mandar dms a clientes si sabemos el ObjectOutputStream
     * @param mensaje el mensaje a mandar
     * @param targetUser el destinatario
     */
    public void mandarMensajesAlosDmsDeClientes(String mensaje, ObjectOutputStream targetUser){
        try{
            targetUser.writeObject(mensaje);
        }catch(IOException e){
            System.out.println("Ups, error al mandar el dm");
        }
    }
}
