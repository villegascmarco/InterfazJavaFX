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
import edu.softech.InterfazJavaFX.gui.WindowMain;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Producto_Sucursal;
import edu.softech.MySpa.modelo.Tratamiento;
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
import tray.notification.NotificationType;

/**
 *
 * @author Villegas
 */
public final class Api
{

    private static final String RUTA = "http://localhost:18835/my-spa/api/";

    int respuestaServidor = 0;

    private InputStreamReader isr = null;

    private BufferedReader br = null;

    private String lineaActual = null;

    private JsonParser parser = null;

    private URL url;

    private WindowMain windowMain = new WindowMain();

    /**
     * Al ser una consulta de un listado, este metodo regresa un arreglo de json
     *
     * @param objeto
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public JsonArray consultarListado(String objeto)
            throws
            MalformedURLException, IOException
    {
        String ruta = RUTA + objeto + "/listado";//http://localhost:8084/MySpa/api/cliente/listado

        parser = new JsonParser();

        JsonArray json = new JsonArray();

        try
        {

            json = parser.parse(hacerPeticion(ruta, "GET")).getAsJsonArray();

        }
        catch (Exception ex)
        {

            windowMain.mostrarNotificacion("Error de conexión",
                    "El servidor no está activo", NotificationType.ERROR);
            ex.printStackTrace();

            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);

        }

        return json;
    }

    public JsonElement manejarCliente(Cliente c,
                                      String opcion)
    {
        String enlace = RUTA + "cliente?";
        if (!opcion.equals("DELETE"))
            enlace += "nombre=" + URLEncoder.encode(c.getNombre())
                    + "&apellidoPaterno=" + URLEncoder.encode(
                            c.getApellidoPaterno())
                    + "&apellidoMaterno=" + URLEncoder.encode(
                            c.getApellidoMaterno())
                    + "&genero=" + URLEncoder.encode(c.getGenero())
                    + "&telefono=" + URLEncoder.encode(c.getTelefono())
                    + "&rfc=" + URLEncoder.encode(c.getRfc())
                    + "&nombreUsuario=" + c.getUsuario().getNombreUsuario()
                    + "&correo=" + URLEncoder.encode(c.getCorreo())
                    + "&contrasenia=" + c.getUsuario().getContrasenia()
                    + "&domicilio=" + URLEncoder.encode(c.getDomicilio());
        else
            enlace += "numeroUnicoCliente=" + URLEncoder.encode(
                    c.getNumeroUnico())
                    + "&nombreUsuario=" + URLEncoder.encode(
                            c.getUsuario().getNombreUsuario());

        if (opcion.equals("PUT"))
            enlace += "&numeroUnicoCliente=" + URLEncoder.encode(
                    c.getNumeroUnico());
        try
        {
            JsonElement json = parser.parse(hacerPeticion(enlace, opcion));

            return json;

        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JsonElement manejarProducto(Producto_Sucursal p_s,
                                       String opcion)
    {
        String enlace = RUTA + "producto?";
        switch (opcion)
        {
            case "DELETE":
                enlace += "idProducto=" + URLEncoder.encode(Integer.toString(
                        p_s.getProducto().getIdProducto()));
                break;
            case "POST":
                enlace += "nombre=" + URLEncoder.encode(
                        p_s.getProducto().getNombre())
                        + "&marca=" + URLEncoder.encode(
                                p_s.getProducto().getMarca())
                        + "&precioUso=" + URLEncoder.encode(Float.toString(
                                p_s.getProducto().getPrecioUso()))
                        + "&idSucursal=" + URLEncoder.encode(Integer.toString(
                                p_s.getSucursal().getIdSucursal()))
                        + "&stock=" + URLEncoder.encode(Integer.toString(
                                p_s.getStock()));
                break;
            case "PUT":
                enlace += "idProducto=" + URLEncoder.encode(Integer.toString(
                        p_s.getProducto().getIdProducto()))
                        + "&nombre=" + URLEncoder.encode(
                                p_s.getProducto().getNombre())
                        + "&marca=" + URLEncoder.encode(
                                p_s.getProducto().getMarca())
                        + "&precioUso=" + URLEncoder.encode(Float.toString(
                                p_s.getProducto().getPrecioUso()))
                        + "&idSucursal=" + URLEncoder.encode(Integer.toString(
                                p_s.getSucursal().getIdSucursal()))
                        + "&stock=" + URLEncoder.encode(Integer.toString(
                                p_s.getStock()));
        }
        try
        {
            System.out.println(enlace);
            JsonElement json = parser.parse(hacerPeticion(enlace, opcion));

            if (json.isJsonNull())
                return null;

            return json;

        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException e)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        catch (Exception ex)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JsonElement manejarTratamiento(Tratamiento t,
                                          String opcion)
    {
        try
        {
            String enlace = RUTA + "tratamiento?";

            switch (opcion)
            {
                case "DELETE":
                    enlace += "idTratamiento=" + URLEncoder.encode(
                            Integer.toString(t.getIdTratamiento()));
                    break;
                case "POST":
                    enlace += "nombre=" + URLEncoder.encode(t.getNombre())
                            + "&descripcion=" + URLEncoder.encode(
                                    t.getDescripcion())
                            + "&costo=" + URLEncoder.encode(Float.toString(
                                    t.getCosto()));
                    break;
                case "PUT":
                    enlace += "idTratamiento=" + URLEncoder.encode(
                            Integer.toString(t.getIdTratamiento()))
                            + "&nombre=" + URLEncoder.encode(t.getNombre())
                            + "&descripcion=" + URLEncoder.encode(
                                    t.getDescripcion())
                            + "&costo=" + URLEncoder.encode(Float.toString(
                                    t.getCosto()));
                    break;
            }

            JsonElement json = parser.parse(hacerPeticion(enlace, opcion));

            return json;

        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException e)
        {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        catch (Exception ex)
        {
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
    private JsonObject consultarObjeto(String objeto)
            throws Exception
    {
        System.out.println("---------------Metodo incompleto---------------");
        parser = new JsonParser();

        JsonObject json = null;

        String ruta = RUTA + objeto;//http://localhost:8084/MySpa/api/cliente/

        URL url = new URL(ruta);

        HttpURLConnection connHttp = (HttpURLConnection) url.openConnection();

        connHttp.setRequestMethod("GET");
        connHttp.setRequestProperty("datatype", "json");

        respuestaServidor = connHttp.getResponseCode();

        if (respuestaServidor == HttpURLConnection.HTTP_OK)
        {

            isr = new InputStreamReader(connHttp.getInputStream());
            br = new BufferedReader(isr);

            String contenidoRespuesta = "";

            while ((lineaActual = br.readLine()) != null)
                contenidoRespuesta += lineaActual;

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
    public String hacerPeticion(String enlace,
                                String metodo)
            throws Exception
    {

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

        while ((lineaActual = br.readLine()) != null)
            contenidoRespuesta += lineaActual;

        isr.close();

        br.close();

        connHttp.disconnect();
        return contenidoRespuesta;

    }

    public JsonObject eliminarSucursal(String objeto)
            throws Exception
    {
        parser = new JsonParser();
        JsonObject json = null;
        String acum = "";

        for (int i = 0; i < objeto.length(); i++)
            if (objeto.charAt(i) == ' ')
                acum += "%20";
            else
                acum += objeto.charAt(i);

        String ruta = RUTA + acum;//http://localhost:8080/MySpa/api/sucursal?

        json = parser.parse(hacerPeticion(ruta, "DELETE")).getAsJsonObject();

        return json;
    }

    //METODO PARA INSERTAR UNA SUCURSAL MEDIANTE EL METODO POST
    public JsonObject insertarSucursal(String objeto)
            throws Exception
    {
        parser = new JsonParser();

        JsonObject json = null;
        String acum = "";
        for (int i = 0; i < objeto.length(); i++)
            if (objeto.charAt(i) == ' ')
                acum += "%20";
            else
                acum += objeto.charAt(i);

        String ruta = RUTA + acum;//http://localhost:8080/MySpa/api/sucursal?

        json = parser.parse(hacerPeticion(ruta, "POST")).getAsJsonObject();

        return json;

    }

    //METODO PARA ACTUALIZAR UNA SUCURSAL MEDIANTE METODO PUT
    public JsonObject actualizarSucursal(String objeto)
            throws Exception
    {
        parser = new JsonParser();

        JsonObject json = null;
        String acum = "";
        for (int i = 0; i < objeto.length(); i++)
            if (objeto.charAt(i) == ' ')
                acum += "%20";
            else
                acum += objeto.charAt(i);

        String ruta = RUTA + acum;//http://localhost:8080/MySpa/api/sucursal?

        json = parser.parse(hacerPeticion(ruta, "PUT")).getAsJsonObject();

        return json;

    }

}
