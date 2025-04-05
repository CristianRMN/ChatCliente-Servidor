package servidor.comandos.api;

import ficheros.LecturaEscrituraJSON;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase de conecion para la consulta del tiempo
 * @author cristian
 * @version 1.0
 */
public class ApiComandoConnection {

    //atributos de clase
    private static final String PATH_TIEMPO_JSON = "apiTiempo.json";
    private static final String API_KEY = "e384cf41743f58b610502ce31365dfa5";
    private static final String urlBase = """
            https://api.openweathermap.org/data/2.5/weather""";

    /**
     * Metodo para comprobar si la ciudad introducida por el usuario, existe o no
     * @param city la ciudad introducida por el usuario
     * @param path el path del archivo .JSON
     * @param lefJson el metodo de lectura de ficheros JSON
     * @return True o False si actualizó la información
     */
    public boolean getTiempoComando(String city, String path, LecturaEscrituraJSON lefJson){
        String busqueda = urlBase + "?q=" + city + "&appid=" + API_KEY + "&units=metric";
        HttpClient client = HttpClient.newHttpClient();
        try{
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(busqueda))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                lefJson.registrarTiempoJson(path, response.body());
                return true;
            }
        }catch (IOException | InterruptedException e){
            System.out.println("Ups, error al comunicarse con la API del tiempo");
        }catch (Exception e){
            System.out.println("Ups, error catastrofico");
        }
        return false;
    }

    /**
     * Metodo que devuelve el path del JSON
     * @return el path del json
     */
    public String getPathTiempoJson(){
        return PATH_TIEMPO_JSON;
    }


}



