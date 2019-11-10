/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
}