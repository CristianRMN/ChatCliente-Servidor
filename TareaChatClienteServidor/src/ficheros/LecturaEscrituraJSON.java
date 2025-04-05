package ficheros;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


/**
 * Clase para lectura y escritura de ficheros JSON
 * @author cristian
 * @version 1.0
 */
public class LecturaEscrituraJSON {

    /**
     * Metodo para leer los mensajes de un JSON y me devuelva un array de String
     * @param path el path del archivo
     * @return el array de mensajes
     */
    public String[] readMensajesMaps(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(path);
            return mapper.readValue(file, new TypeReference<>() {
            });

        } catch (IOException e) {
            System.out.println("Ups, error al leer el archivo json con los datos o el archivo estaba vacío");
        }
        return null;
    }

    /**
     * Metodo para registrar los mensajes en el JSON
     * @param path el path del archivo
     * @param mensajes los mensajes a registrar
     */
    public void registrarMensajesJson(String path, String[] mensajes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(path);

            String nuevoMensaje = mensajes[0] + ": " + mensajes[1];

            String[] recordarmensajes = auxAgregarMensajesParaNoBorrar(file, nuevoMensaje);

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, recordarmensajes);
        } catch (IOException e) {
            System.out.println("Ups, error al escribir en el JSON de mensajes");
        }
    }

    /**
     * Metodo auxiliar para agregar mensajes al JSON y no sobreescribirlo
     * @param file el objeto file
     * @param nuevoMensaje el nuevo mensaje a agregar
     * @return un array de String con el nuevo mensaje agregado
     */
    private String [] auxAgregarMensajesParaNoBorrar(File file, String nuevoMensaje) {

            String [] mensaje = readMensajesMaps(file.getAbsolutePath());
            if(mensaje != null){
                String [] arrayDeMensajes = new String[mensaje.length +  1];
                System.arraycopy(mensaje, 0, arrayDeMensajes, 0, mensaje.length);
                arrayDeMensajes[mensaje.length] = nuevoMensaje;
                return arrayDeMensajes;
            }
            else{
                return new String[]{nuevoMensaje};
            }

    }

    /**
     * Metodo para regitrar el tiempo recibido de la APi en el JSON
     * @param path el path del archivo
     * @param mensajeTiempo el mensaje del tiempo
     */
    public void registrarTiempoJson(String path, String mensajeTiempo) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);

        if(file.exists()){
            try{
                auxWriteTiempo(mensajeTiempo, mapper, file);
            }catch(IOException e){
                System.out.println("Ups, error al escribir en el JSON de tiempo");
            }
        }
        else{
            try {
                if(file.createNewFile() && file.isFile()){
                    auxWriteTiempo(mensajeTiempo, mapper, file);
                }
            } catch (IOException e) {
                System.out.println("Ups, error al crear el archivo");
            }
        }
    }

    /**
     * Metodo para leer el JSON del tiempo y mandarlo al cliente
     * @param path el path del archivo
     * @return el string con la información del tiempo
     */
    public String readJsonTiempo(String path) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        try{
            JsonNode root = mapper.readTree(file);

            String ciudad = root.get("name").asText();
            double tempMin = root.get("main").get("temp_min").asDouble();
            double tempMax = root.get("main").get("temp_max").asDouble();
            double humedad = root.get("main").get("humidity").asDouble();
            String windSpeed = root.get("wind").get("speed").asText();
            String descripcion = root.get("weather").get(0).get("description").asText();

            return "Clima en la ciudad de " + ciudad +
            "\nTemperatura mínima: " + tempMin + " Cº" +
            "\nTemperatura máxima: " + tempMax + " Cº" +
            "\nHumedad: " + humedad +
            "\nVelocidad del viento: " + windSpeed + " Km/h" +
            "\nDescripcion general: " + descripcion;

        } catch (IOException e) {
            System.out.println("Ups, error al leer el archivo JSON de tiempo");
        }
        return null;
    }

    /**
     * Metodo auxiliar para escribir el tiempo
     * @param mensajeTiempo el mensaje del tiempo
     * @param mapper el objeto ObjectMapper
     * @param file el objeto file
     * @throws IOException la excepcion a lanzar
     */
    private static void auxWriteTiempo(String mensajeTiempo, ObjectMapper mapper, File file) throws IOException {
        JsonNode jsonNode = mapper.readTree(mensajeTiempo);

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonNode);
    }
}



