package servidor.comandos.help;

/**
 * Clase con los metodos del comando Help
 * @author cristian
 * @version 1.0
 */
public class ComandoHelp {

    /**
     * Metodo general que devuelve otros metodos dependiendo de lo escrito por el usuario
     * @return los metodos correspondientes al mensaje del usuario
     */
    public String comandoGeneralHelp(){
        return comandoHelpPing("PING") + "\n" +
                comandoHelpTime("TIME_") + "\n" +
                comandoHelpCount("COUNT") + "\n" +
                comandoHelpMatar("DEAD_") + "\n" +
                comandoHelpChiste("JOKE") + "\n" +
                comandoHelpReverse("REVERSE") + "\n" +
                comandoHelpApiWeather("WEATHER");
    }

    /**
     * Metodo que devuelve una respuesta al comando PING
     * @param ping el comando PING
     * @return la respuesta al comando PING
     */
    public String comandoHelpPing(String ping){
        return "El comando " + ping + " sirve para que el servidor te responda con un PONG" +
                "\nEjemplo de uso:" +
                "\nCliente dice: PING" +
                "\nServidor responde: PONG";
    }

    /**
     * Metodo que devuelve una respuesta al comando TIME_
     * @param time el comando TIME_
     * @return la respuesta al comando TIME_
     */
    public String comandoHelpTime(String time){
        return "\nEl comando " + time + " sirve para saber la fecha y hora exacta de un lugar" +
                "\nEjemplo de uso:" +
                "\nCliente dice: TIME_America/St_Johns" +
                "\nServidor responde: Fecha y hora en America/St_Johns: 2025-02-24 13:03:17 NST";

    }

    /**
     * Metodo que devuelve una respuesta al comando COUNT
     * @param count el comando COUNT
     * @return la respuesta al comando COUNT
     */
    public String comandoHelpCount(String count){
        return "\nEl comando " + count + " sirve para saber el número de usuarios conectados a la sala" +
                "\nEjemplo de uso: " +
                "\nCliente dice: COUNT" +
                "\nServidor responde: Actualmente hay x usuarios conectados, siendo (x) el número de usuarios";
    }

    /**
     * Metodo que devuelve una respuesta al comando DEAD_
     * @param matar el comando DEAD_
     * @return la respuesta al comando DEAD_
     */
    public String comandoHelpMatar(String matar){
        return "\nEl comando " + matar + " sirve para detener el servidor" +
                "\nSu funcionamiento se basa en: " +
                "\nEl comando es DEAD_(numero aleatorio" +
                "\nEl número aleatorio va del 1 al 100" +
                "\nSi lo aciertas, el servidor se detiene";
    }

    /**
     * Metodo que devuelve una respuesta al comando JOKE
     * @param chiste el comando JOKE
     * @return la respuesta al comando JOKE
     */
    public String comandoHelpChiste(String chiste){
        return "\nEl comando " + chiste + " sirve para que el servidor te cuente un chiste aleatorio" +
                "\nEjemplo de uso: " +
                "\nCliente dice: JOKE" +
                "\nServidor responde: " +
                "\nVan dos ciegos y le dice uno al otro: " +
                "\n- Ojalá lloviera..." +
                "\n- Ojalá yo también...";
    }

    /**
     * Metodo que devuelve una respuesta al comando REVERSE
     * @param reverse el comando REVERSE
     * @return la respuesta al comando REVERSE
     */
    public String comandoHelpReverse(String reverse){
        return "\nEl comando " + reverse + " sirve para que el servidor te devuelva un texto al revés" +
                "\nEjemplo de uso: " +
                "\nCliente dice: REVERSE hola" +
                "\nServidor responde: aloh";
    }

    /**
     * Metodo que devuelve una respuesta al comando WEATHER_
     * @param weather el comando WEATHER
     * @return la respuesta al comando WEATHER
     */
    public String comandoHelpApiWeather(String weather){
        return "\nEl comando " + weather + " sirve para conocer el tiempo y condiciones climatológicas de una ciudad del planeta" +
                "\nEjemplo de uso: " +
                "\nCliente dice: WEATHER_Madrid" +
                "\nServidor responde: " +
                "\nClima en la ciudad de Madrid" +
                "\nTemperatura mínima: 13.45cº" +
                "\nTemperatura máxima: 15.68cº" +
                "\nHumedad: 60.0" +
                "\nVelocidad del viento: 5.66km/h" +
                "\nDescripcion general: clear sky";
    }
}
