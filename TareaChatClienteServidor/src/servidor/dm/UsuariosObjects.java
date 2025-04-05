package servidor.dm;

import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Clase con lo necesario para realizar DMs de un usuario a otro
 * @author cristian
 * @version 1.0
 */
public class UsuariosObjects {

    //atributo de clase
    private final HashMap<String, ObjectOutputStream> usuariosDms;

    /**
     * Constructor de la clase
     */
    public UsuariosObjects() {
        usuariosDms = new HashMap<>();
    }

    /**
     * Metodo para establecer un nuevo usuario para dms
     * @param nickname el alias del usuario
     * @param out el objectOutputStream del usuario
     */
    public synchronized void putNewUsuariosDms(String nickname, ObjectOutputStream out) {
        this.usuariosDms.put(nickname, out);
    }

    /**
     * Metodo para eliminar un usuario de dms cuando se va del chat
     * @param nickname el alias del usuario
     */
    public synchronized void deleteUsuariosDms(String nickname) {
        this.usuariosDms.remove(nickname);
    }

    /**
     * Metodo para separar el aroba del mensaje
     * @param mensaje el mensaje del usuario
     * @return el nuevo mensaje separado
     */
    public synchronized String separateArobaMensaje(String mensaje) {
        if (!mensaje.startsWith("@")) {
            return null;
        }

        String[] partes = mensaje.split(" ", 2);
        if (partes.length < 2) {
            return null;
        }

        return partes[0].substring(1);
    }


    /**
     * Metodo para obtener el objectOutputStream del usuario por el alias
     * @param nickname el alias del usuario
     * @return el objectOutput asociado a el
     */
    public synchronized ObjectOutputStream getOutPutByNickName(String nickname) {
        return usuariosDms.get(nickname);
    }






}
