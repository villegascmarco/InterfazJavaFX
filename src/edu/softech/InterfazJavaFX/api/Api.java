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
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.net.www.http.HttpClient;

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

    private String contenidoRespuesta = null;

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

        url = new URL(ruta);

        HttpURLConnection connHttp
                = (HttpURLConnection) url.openConnection();

        connHttp.setRequestMethod("GET");
        connHttp.setRequestProperty("datatype", "json");

        respuestaServidor = connHttp.getResponseCode();

        if (respuestaServidor == HttpURLConnection.HTTP_OK) {
            isr = new InputStreamReader(connHttp.getInputStream());
            br = new BufferedReader(isr);

            contenidoRespuesta = "";

            while ((lineaActual = br.readLine()) != null) {
                contenidoRespuesta += lineaActual;
            }

            br.close();

            connHttp.disconnect();

            json = parser.parse(contenidoRespuesta).getAsJsonArray();
        }

        return json;
    }

    public String quitarEspacios(String enlance) {
        String respuesta = "";

        enlance = enlance.trim();

        //Remplazamos los espacios por %20 para que no haya errores
        for (int i = 0; i < enlance.length(); i++) {
            if (enlance.charAt(i) == ' ') {
                respuesta += "%20";
            } else {
                respuesta += enlance.charAt(i);
            }
        }

        return respuesta;
    }

    public JsonElement modificarCliente(Cliente c) {
        Usuario u = c.getUsuario();
        JsonElement json = null;

        String enlance = RUTA
                + "cliente?"
                + "nombre=" + c.getNombre()
                + "&apellidoPaterno=" + c.getApellidoPaterno()
                + "&apellidoMaterno=" + c.getApellidoMaterno()
                + "&genero=" + c.getGenero()
                + "&telefono=" + c.getTelefono()
                + "&rfc=" + c.getRfc()
                + "&nombreUsuario=" + u.getNombreUsuario()
                + "&correo=" + c.getCorreo()
                + "&contrasenia=" + u.getContrasenia()
                + "&domicilio=" + c.getDomicilio()
                + "&numeroUnicoCliente=" + c.getNumeroUnico();
        try {

            enlance = quitarEspacios(enlance);

            url = new URL(enlance);
            HttpURLConnection connHttp
                    = (HttpURLConnection) url.openConnection();

            connHttp.setRequestMethod("PUT");
            connHttp.setRequestProperty("datatype", "json");

            respuestaServidor = connHttp.getResponseCode();

            if (respuestaServidor == HttpURLConnection.HTTP_OK) {
                isr = new InputStreamReader(connHttp.getInputStream());
                br = new BufferedReader(isr);

                contenidoRespuesta = "";

                while ((lineaActual = br.readLine()) != null) {
                    contenidoRespuesta += lineaActual;
                }

                br.close();

                connHttp.disconnect();
                json = parser.parse(contenidoRespuesta);
                return json;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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

            contenidoRespuesta = "";

            while ((lineaActual = br.readLine()) != null) {
                contenidoRespuesta += lineaActual;
            }

            br.close();

            connHttp.disconnect();

            json = parser.parse(contenidoRespuesta).getAsJsonObject();

        }

        return json;

    }

    public void metodoPost() throws MalformedURLException, UnsupportedEncodingException, IOException {

        Cliente c = new Cliente();

        for (Field f : c.getClass().getFields()) {
            System.out.println(f.getName());
        }
    }
}
