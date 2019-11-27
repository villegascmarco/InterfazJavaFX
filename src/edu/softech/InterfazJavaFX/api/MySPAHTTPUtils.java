/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 *
 * @author Esau
 */
public class MySPAHTTPUtils {

    /**
     * Este metodo nos sirve para leer datos desde una conexión HTPP previamente
     * establecida.
     *
     * @param httpConn
     * @return
     * @throws Exception
     */
    public static String read(HttpURLConnection httpConn)
            throws Exception {
        // crea la linea de comunicacioin
        InputStreamReader isr
                = new InputStreamReader(httpConn.getInputStream(), "UTF-8");

        // crea el empudo que podra ver el contenido que estará en la linea de comunicación
        BufferedReader br = new BufferedReader(isr);

        String contenido = "";
        String lineaActual = null;

        while ((lineaActual = br.readLine()) != null) {
            contenido += lineaActual;
        }

        return contenido;
    }

    /**
     * Este metodo nos permite enviar datos a un servidor a través de una
     * conexión HTTP previamente establecida
     *
     * @param httpConn
     * @param contenido
     * @throws IOException
     */
    public static void write(HttpURLConnection httpConn,
            String contenido)
            throws IOException {
        DataOutputStream dos;
        byte[] bytes;

        bytes = contenido.getBytes();

        dos = new DataOutputStream(httpConn.getOutputStream());

        dos.write(bytes);

        dos.close();
    }

    /**
     *
     */
}
