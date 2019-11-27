/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Villegas
 */
public final class Api {

    private static final String RUTA = "http://localhost:8084/MySpa/api/";

    int respuestaServidor = 0;

    private InputStreamReader isr = null;

    private BufferedReader br = null;

    private String lineaActual = null;

    private JsonParser parser = null;

    private URL url;

    /**
     * Al ser una consulta de un listado, este metodo regresa un arreglo de json
     *
     * @param objeto
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public JsonArray consultarListado(String objeto) throws
            MalformedURLException, IOException {
        String ruta = RUTA + objeto + "/listado";//http://localhost:8084/MySpa/api/cliente/listado

        parser = new JsonParser();

        JsonArray json = null;

        try {

            json = parser.parse(hacerPeticion(ruta, "GET")).getAsJsonArray();

        } catch (Exception ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }

        return json;
    }

    public JsonElement manejarCliente(Cliente c, String opcion) {
        String enlace = RUTA + "cliente?";
        if (!opcion.equals("DELETE")) {
            enlace += "nombre=" + URLEncoder.encode(c.getNombre())
                    + "&apellidoPaterno=" + URLEncoder.encode(c.getApellidoPaterno())
                    + "&apellidoMaterno=" + URLEncoder.encode(c.getApellidoMaterno())
                    + "&genero=" + URLEncoder.encode(c.getGenero())
                    + "&telefono=" + URLEncoder.encode(c.getTelefono())
                    + "&rfc=" + URLEncoder.encode(c.getRfc())
                    + "&nombreUsuario=" + c.getUsuario().getNombreUsuario()
                    + "&correo=" + URLEncoder.encode(c.getCorreo())
                    + "&contrasenia=" + c.getUsuario().getContrasenia()
                    + "&domicilio=" + URLEncoder.encode(c.getDomicilio());
        } else {
            enlace += "numeroUnicoCliente=" + URLEncoder.encode(c.getNumeroUnico())
                    + "&nombreUsuario=" + URLEncoder.encode(c.getUsuario().getNombreUsuario());
        }

        if (opcion.equals("PUT")) {
            enlace += "&numeroUnicoCliente=" + URLEncoder.encode(c.getNumeroUnico());
        }
        try {
            JsonElement json = parser.parse(hacerPeticion(enlace, opcion));
            System.out.println(json);
            return json;

        } catch (MalformedURLException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Este metodo regresa sólo un objeto de tipo Json
     *
     * @param objeto
     * @return
     * @throws Exception
     */
    private JsonObject consultarObjeto(String objeto) throws Exception {
        System.out.println("---------------Metodo incompleto---------------");
        parser = new JsonParser();

        JsonObject json = null;

        String ruta = RUTA + objeto;//http://localhost:8084/MySpa/api/cliente/

        URL url = new URL(ruta);

        HttpURLConnection connHttp = (HttpURLConnection) url.openConnection();

        connHttp.setRequestMethod("GET");
        connHttp.setRequestProperty("datatype", "json");

        respuestaServidor = connHttp.getResponseCode();

        if (respuestaServidor == HttpURLConnection.HTTP_OK) {

            isr = new InputStreamReader(connHttp.getInputStream());
            br = new BufferedReader(isr);

            String contenidoRespuesta = "";

            while ((lineaActual = br.readLine()) != null) {
                contenidoRespuesta += lineaActual;
            }

            br.close();

            connHttp.disconnect();

            json = parser.parse(contenidoRespuesta).getAsJsonObject();

        }

        return json;

    }

    /**
     * Este metodo sirve para hacer cualquier petición al servidor
     *
     * EJEMPLO
     *
     * metodo="PUT"
     *
     * @param enlace
     * @param metodo
     * @return
     * @throws Exception
     */
    public String hacerPeticion(String enlace, String metodo) throws Exception {

        URL url = new URL(enlace);
        HttpURLConnection connHttp
                = (HttpURLConnection) url.openConnection();

        connHttp.setRequestMethod(metodo);
        connHttp.setRequestProperty("datatype", "json");

        // crea la linea de comunicacioin
        isr = new InputStreamReader(connHttp.getInputStream(), "UTF-8");

        // crea el empudo que podra ver el contenido que estará en la linea de comunicación
        br = new BufferedReader(isr);

        String contenidoRespuesta = "";
        lineaActual = null;

        while ((lineaActual = br.readLine()) != null) {
            contenidoRespuesta += lineaActual;
        }

        isr.close();

        br.close();

        connHttp.disconnect();
        return contenidoRespuesta;

    }

}
