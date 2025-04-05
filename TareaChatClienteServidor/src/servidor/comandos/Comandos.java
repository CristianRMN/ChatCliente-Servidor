package servidor.comandos;

import ficheros.LecturaEscrituraJSON;
import servidor.comandos.api.ApiComandoConnection;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase comandos con los metodos de comandos
 * @author cristian
 * @version 1.0
 */
public class Comandos {

    //atributo para matar al servidor
    private String matar_servidor = "DEAD_";

    //atributo de zonas horarias
    private final String[] zonasHorarias = {
            "America/New_York",  // EST (Eastern Standard Time)
            "Pacific/Honolulu",  // HST (Hawaiian Standard Time)
            "America/Denver",    // MST (Mountain Standard Time)
            "Australia/Darwin",  // ACT (Australian Central Time)
            "Australia/Sydney",  // AET (Australian Eastern Time)
            "America/Argentina/Buenos_Aires",  // AGT (Argentina Time)
            "Asia/Dubai",        // AST (Arabian Standard Time)
            "America/Sao_Paulo", // BET (Brazil Eastern Time)
            "Europe/London",     // BST (British Summer Time)
            "Africa/Johannesburg", // CAT (Central Africa Time)
            "America/St_Johns",  // CNT (Newfoundland Time)
            "America/Chicago",   // CST (Central Standard Time)
            "Asia/Shanghai",     // CTT (China Time)
            "Africa/Nairobi",    // EAT (East Africa Time)
            "Europe/Paris",      // ECT (Central European Time)
            "America/Indiana/Indianapolis", // IET (Indiana Eastern Time)
            "Asia/Kolkata",      // IST (India Standard Time)
            "Asia/Tokyo",        // JST (Japan Standard Time)
            "Pacific/Apia",      // MIT (Midway Islands Time)
            "Asia/Yerevan",      // NET (Near East Time)
            "America/St_Johns",  // NST (Newfoundland Standard Time)
            "Asia/Karachi",      // PLT (Pakistan Lahore Time)
            "America/Phoenix",   // PNT (Phoenix Standard Time)
            "America/Puerto_Rico", // PRT (Puerto Rico and US Virgin Islands Time)
            "America/Los_Angeles", // PST (Pacific Standard Time)
            "Pacific/Pago_Pago", // SST (Samoa Standard Time)
            "Asia/Ho_Chi_Minh"   // VST (Vietnam Standard Time)
    };

    //chistes del servidor
    private final String[] chistes = {
            """
- Mis tíos se han ido a Nueva Orleans
-¿Luisiana?
- No, Paco y Merche""",

            """
- Dime con quién andas y te diré quién eres.
- No ando con nadie...
- Eres feo
""",

            """

Va el marido completamente borracho y le dice a su mujer al irse para cama:
- Me ha pasado algo increíble. He ido al baño y al abrir la puerta se ha encendido la luz automáticamente, sin hacer nada.
- ¡La madre que te parió!, ¡Te mato!, ya te has vuelto a mear en la nevera
            """,

            """        
- ¡Rápido, necesitamos sangre!
- Yo soy 0 positivo.
- Pues muy mal, necesitamos una mentalidad optimista
            """,

            """
- El otro día unas chicas llamarón a mi puerta y me pidieron una pequeña donación para una piscina local.
- Les di un garrafa de agua
            """,

            """
- ¿Por qué Bob Esponja no va al gimnasio?
- Porque ya está cuadrado
            """,

            """
Van dos ciegos y le dice uno al otro:
- Ojalá lloviera...
- Ojalá yo también...
            """,

            """
- Noticia de última hora!!
- Muere una suegra atropellada en Canarias
- Y esto es todo, las 8 en España y UNA menos en Canarias...
            """,

            """
- Qué pasa si te expulsan de cuatro univerdades?
- ....
- Que estás perdiendo facultades
            """,

            """
En una discoteca, un chico pregunta a una chica:
- ¿Bailamos?
- Claro. ¿Pero quién saca a mi amiga?
- Ahhh, por eso no te preocupes. ¡SEGURIDAAAAD!
            """

    };

    /**
     * metodo para hacer el comando DEAD_
     * @param numeroAleatorio un numero entre el 1 al 100
     */
    public synchronized void setmatar_servidor(int numeroAleatorio) {
        this.matar_servidor += String.valueOf(numeroAleatorio);
    }

    //getter
    public synchronized String getmatar_servidor() {
        return matar_servidor;
    }

    /**
     * Metodo para hacer un numero aleatorio entre el 1 al 100
     * @return el numero aleatorio
     */
    public int getNumeroAleatorio() {
        return (int) (Math.random() * 101);
    }

    //getters
    public synchronized String getMensaje_pong() {
        return "PONG";
    }

    public synchronized String[] getChistes() {
        return chistes;
    }


    /**
     * Metodo para saber si el usuario introdujo una zona horaria
     * @param mensaje el mensaje del usuario
     * @return una respuesta con la hora
     */
    public synchronized String checkMensajeDate(String mensaje) {
        for (String zonas : zonasHorarias) {
            String mensaje_time = "TIME_";
            String zonaCorrecta = mensaje_time + zonas;
            if (zonaCorrecta.equals(mensaje)) {
                ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(zonas));
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
                return "Fecha y hora en " + zonas + ": " + zonedDateTime.format(formato);
            }
        }
        return null;
    }

    /**
     * Metodo para hacer un chiste aleatorio
     * @return el chiste aleatorio
     */
    public synchronized String getChisteAleatorio() {
            int numAletorio = (int)(Math.random() * getChistes().length);
            return getChistes()[numAletorio];
    }

    /**
     * Metodo para devolver un mensaje del usuario al reves
     * @param mensaje el mensaje del usuario
     * @return el mensaje del usuario al reves
     */
    public synchronized String getTextoAlReves(String mensaje){
        String mensajeCortado = mensaje.substring(7);
        return new StringBuilder(mensajeCortado).reverse().toString();
    }

    /**
     * Metodo para leer del JSON recibido de la api, el tiempo de la ciudad solicitada por el usuario
     * @param mensaje el mensaje del usuario
     * @param api el objeto de conecction con la API
     * @param lefJson el objeto de lectura/escritura del JSON
     * @return un mensaje con la información del tiempo de la ciudad
     */
    public synchronized String getTiempoApi(String mensaje, ApiComandoConnection api, LecturaEscrituraJSON lefJson){
        String ciudad = mensaje.substring(8);
        boolean hayTiempo =  api.getTiempoComando(ciudad, api.getPathTiempoJson(), lefJson);
        if(hayTiempo){
            return lefJson.readJsonTiempo(api.getPathTiempoJson());
        }
        else {
            return "La ciudad no existe o la escribiste mal";
        }

    }




}
