/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.softech.InterfazJavaFX.api.Api;
import edu.softech.InterfazJavaFX.api.MySpaCommons;
import edu.softech.InterfazJavaFX.gui.WindowMain;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Empleado;
import edu.softech.MySpa.modelo.Usuario;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import tray.notification.NotificationType;

/**
 *
 * @author Esau
 */
public class PaneEmpleadoControlador implements Initializable
{

    @FXML
    private AnchorPane windowEmpleado;

    @FXML
    private AnchorPane apDerecha;

    @FXML
    private ImageView imgFotografia;

    @FXML
    private JFXTextField txtNumeroEmpleado;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtApellidoPaterno;

    @FXML
    private JFXTextField txtApellidoMaterno;

    @FXML
    private JFXComboBox cmbGenero;

    @FXML
    private JFXButton btnAgregarGenero;

    @FXML
    private JFXTextField txtDomicilio;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXTextField txtRfc;

    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private JFXTextField txtContrasenia;

    @FXML
    private JFXComboBox cmbPuesto;

    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnElminar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private AnchorPane apIzquierda;

    @FXML
    private JFXTextField txtBuscar;

    @FXML
    private JFXCheckBox chkInactivos;

    @FXML
    private TableView<Empleado> tblEmpleados;

    private TableColumn<Empleado, String> colNombre;

    private TableColumn<Empleado, String> colApellidoPaterno;

    private TableColumn<Empleado, String> colApellidoMaterno;

    private TableColumn<Empleado, String> colGenero;

    private TableColumn<Empleado, String> colDomicilio;

    private TableColumn<Empleado, String> colTelefono;

    private TableColumn<Empleado, String> colRfc;

    private TableColumn<Empleado, String> colNumeroEmpleado;

    private TableColumn<Empleado, String> colPuesto;

    private TableColumn<Empleado, String> colEstatus;

    private WindowMain windowMain = new WindowMain();

    // opcion para verbo de acceso al REST
    String opcion = "";

    Api api = new Api();

    private Gson gson = new Gson();

    /**
     * Gama de colores según las opciones
     */
    private final String UNC_EDITAR = "-jfx-unfocus-color: f68a1f;";
    private final String UNC_DEFAULT = "-jfx-unfocus-color: #4d4d4d;";
    private final String UNC_NUEVO = "-jfx-unfocus-color: #00C851;";
    private final String UNC_ELIMINAR = "-jfx-unfocus-color: #ff4444;";

    /**
     * Iniciatilza los componentes del documento fxml
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location,
                           ResourceBundle resources)
    {
        try
        {
            inicializarTabla(true);
            llenarComboBoxes();
            inicializarOyentes();
        }
        catch (IOException ex)
        {
            Logger.getLogger(PaneClientesControlador.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    private void inicializarOyentes()
    {

        tblEmpleados.setOnMouseClicked((MouseEvent x) ->
        {
            llenarCampos();
        });
        tblEmpleados.setOnKeyReleased(x ->
        {
            llenarCampos();
        });

        btnEditar.setOnAction(x ->
        {
            opcion = "PUT";
            cambiarCampos(UNC_EDITAR, true);
        });

        btnGuardar.setOnAction(x ->
        {

            if (opcion == null)
                return;
            cambiarCampos(UNC_DEFAULT, false);

            ejecutarPeticion(prepararDatos());

        });

        btnNuevo.setOnAction(x ->
        {

            opcion = "POST";
            cambiarCampos(UNC_NUEVO, true);
            limpiarCampos();
        });

        btnCancelar.setOnAction(x ->
        {
            opcion = null;
            limpiarCampos();
            cambiarCampos(UNC_DEFAULT, false);
        });

        btnElminar.setOnAction(x ->
        {

            try
            {
                opcion = "DELETE";

                if (tblEmpleados.getSelectionModel().getSelectedItem() == null)
                {
                    opcion = null;
                    cambiarCampos(UNC_DEFAULT, false);
                    return;
                }

                cambiarCampos(UNC_ELIMINAR, false);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText("Está a punto de eliminar a un cliente");
                alert.setContentText("¿Está seguro?");

                Optional<ButtonType> resultado = alert.showAndWait();
                if (resultado.get() == ButtonType.OK)
                    manejarEmpleado(prepararDatos(), opcion);
                else
                    System.out.println("Chale");
                cambiarCampos(UNC_DEFAULT, false);
                
                if (!chkInactivos.selectedProperty().get())
                    inicializarTabla(true);
                else
                    inicializarTabla(false);
                
                opcion = null;
            }
            catch (IOException ex)
            {
                Logger.getLogger(PaneEmpleadoControlador.class.getName()).log(
                        Level.SEVERE,
                        null, ex);
            }

        });

        chkInactivos.setOnAction(x ->
        {
            try
            {
                if (!chkInactivos.selectedProperty().get())
                    inicializarTabla(true);
                else
                    inicializarTabla(false);
            }
            catch (IOException ex)
            {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(
                        Level.SEVERE, null, ex);
            }

        });

    }

    private Empleado prepararDatos()
    {
        Empleado e = new Empleado();
        Usuario u = new Usuario();

        if (tblEmpleados.getSelectionModel().getSelectedItem() != null)
        {
            e = tblEmpleados.getSelectionModel().getSelectedItem();
            u = e.getUsuario();
        }
        if (opcion.equals("POST") || opcion.equals("PUT"))
        {

            e.setNombre(txtNombre.getText());
            e.setApellidoPaterno(txtApellidoPaterno.getText());
            e.setApellidoMaterno(txtApellidoMaterno.getText());

            String genero = cmbGenero.getSelectionModel().getSelectedItem().toString();

            if (genero.equals("Hombre"))
                e.setGenero("M");
            else if (genero.equals("Mujer"))
                e.setGenero("F");
            else if (genero.equals("Otro"))
                e.setGenero("O");

            e.setDomicilio(txtDomicilio.getText());
            e.setTelefono(txtTelefono.getText());
            e.setRfc(txtRfc.getText());

            u.setNombreUsuario(txtUsuario.getText());
            u.setContrasenia(txtContrasenia.getText());

            String rol = cmbGenero.getSelectionModel().getSelectedItem().toString();
            u.setRol(rol);

            e.setUsuario(u);

            String puesto = cmbPuesto.getSelectionModel().getSelectedItem().toString();
            e.setPuesto(puesto);

            e.setEstatus(1);

            e.setFoto("");
            e.setRutaFOto("");
        }

        return e;
    }

    private void ejecutarPeticion(Empleado e)
    {
        Platform.runLater(() ->
        {
            Api api = new Api();
            Usuario u = e.getUsuario();

            double precio = 0;
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Consultando servidor... ");
            alerta.setContentText("Consultando datos del servidor...");

            try
            {
                alerta.show();

                if (manejarEmpleado(e, opcion) != null)
                    windowMain.mostrarNotificacion("Agregado con"
                            + " éxito", "El usuario "
                            + u.getNombreUsuario() + " fue agregado "
                            + "con exito", NotificationType.SUCCESS);
                else
                    windowMain.mostrarNotificacion("Error",
                            "Ocurrio un problema al insertar al usuario",
                            NotificationType.ERROR);

                opcion = null;
                
                if (!chkInactivos.selectedProperty().get())
                    inicializarTabla(true);
                else
                    inicializarTabla(false);

                alerta.hide();
            }
            catch (java.net.UnknownHostException uhe)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);

                alerta.hide();

                a.setTitle("Sin servicio a internet");
                a.setContentText(
                        "No se pudo conectar con el servicio de divisas");
                a.showAndWait();
            }
            catch (IOException ex)
            {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        });
    }

    private void llenarCampos()
    {
        if (tblEmpleados.getSelectionModel().getSelectedItem() != null)
        {
            Empleado e = tblEmpleados.getSelectionModel().getSelectedItem();
            Usuario u = e.getUsuario();

            txtNombre.setText(e.getNombre());
            txtApellidoPaterno.setText(e.getApellidoPaterno());
            txtApellidoMaterno.setText(e.getApellidoMaterno());

            if (e.getGenero().equals("M"))
                cmbGenero.getSelectionModel().select(0);
            else if (e.getGenero().equals("F"))
                cmbGenero.getSelectionModel().select(1);
            else if (e.getGenero().equals("O"))
                cmbGenero.getSelectionModel().select(2);

            txtRfc.setText(e.getRfc());
            txtDomicilio.setText(e.getDomicilio());
            txtTelefono.setText(e.getTelefono());

            txtUsuario.setText(u.getNombreUsuario());
            txtContrasenia.setText(u.getContrasenia());

            txtNumeroEmpleado.setText(e.getNumeroEmpleado());

            if (e.getPuesto().equalsIgnoreCase("gerente"))
                cmbPuesto.getSelectionModel().select(0);
            else if (e.getPuesto().equalsIgnoreCase("recepcionista"))
                cmbPuesto.getSelectionModel().select(1);
            else
                cmbPuesto.getSelectionModel().select(2);
        }
        cambiarCampos(UNC_DEFAULT, false);
        opcion = null;
    }

    private void cambiarCampos(String estilo,
                               boolean editable)
    {
        if (opcion == null)
        {
            //No hacemos nada, opcion cancelar
        }
        else if (tblEmpleados.getSelectionModel().getSelectedItem() == null && (opcion.equals(
                "DELETE") || opcion.equals("PUT")))
        {
            opcion = null;
            cambiarCampos(UNC_DEFAULT, false);
            return;
        }
        txtNombre.setStyle(estilo);
        txtNombre.setEditable(editable);

        txtApellidoPaterno.setStyle(estilo);
        txtApellidoPaterno.setEditable(editable);

        txtApellidoMaterno.setStyle(estilo);
        txtApellidoMaterno.setEditable(editable);

        cmbGenero.setStyle(estilo);
        cmbGenero.setDisable(!editable);

        txtRfc.setStyle(estilo);
        txtRfc.setEditable(editable);

        txtDomicilio.setStyle(estilo);
        txtDomicilio.setEditable(editable);

        txtTelefono.setStyle(estilo);
        txtTelefono.setEditable(editable);

        txtUsuario.setStyle(estilo);
        txtUsuario.setEditable(editable);

        txtContrasenia.setStyle(estilo);
        txtContrasenia.setEditable(editable);

        // cmb puesto y estatus
    }

    private void inicializarColumnas()
    {
        tblEmpleados.autosize();
        tblEmpleados.setDisable(false);

        //Nombre
        colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));

        //ApellidoPaterno
        colApellidoPaterno = new TableColumn<>("Apellido Paterno");
        colApellidoPaterno.setCellValueFactory(
                new PropertyValueFactory<>("apellidoPaterno"));

        //ApellidoMaterno
        colApellidoMaterno = new TableColumn<>("Apellido Materno");
        colApellidoMaterno.setCellValueFactory(
                new PropertyValueFactory<>("apellidoMaterno"));

        //Genero
        colGenero = new TableColumn<>("Genero");
        colGenero.setCellValueFactory(
                new PropertyValueFactory<>("genero"));

        //Domicilio
        colDomicilio = new TableColumn<>("Domicilio");
        colDomicilio.setCellValueFactory(
                new PropertyValueFactory<>("domicilio"));

        //Telefono
        colTelefono = new TableColumn<>("Telefono");
        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono"));

        //Rfc
        colRfc = new TableColumn<>("Rfc");
        colRfc.setCellValueFactory(
                new PropertyValueFactory<>("rfc"));

        //Numero Unico de Empleado
        colNumeroEmpleado = new TableColumn<>("NUE");
        colNumeroEmpleado.setCellValueFactory(
                new PropertyValueFactory<>("numeroEmpleado"));

        //Estatus
        colEstatus = new TableColumn<>("Estatus");
        colEstatus.setCellValueFactory(
                new PropertyValueFactory<>("estatus"));

        colPuesto = new TableColumn<>("Puesto");
        colPuesto.setCellValueFactory(
                new PropertyValueFactory<>("puesto"));

        tblEmpleados.getColumns().addAll(
                colNumeroEmpleado, colNombre, colApellidoPaterno,
                colApellidoMaterno, colGenero, colDomicilio,
                colTelefono, colRfc, colEstatus, colPuesto
        );

    }

    private void llenarComboBoxes()
    {
        cmbGenero.getItems().addAll("Hombre", "Mujer", "Otro");
        cmbPuesto.getItems().addAll("Gerente", "Recepcionista", "Tecnico");
    }

    private void inicializarTabla(boolean empleadosActivos)
            throws IOException
    {

        tblEmpleados.getColumns().clear();
        limpiarCampos();
        tblEmpleados.setItems(obtenerDatos(empleadosActivos));
        inicializarColumnas();

    }

    private void limpiarCampos()
    {
        tblEmpleados.getSelectionModel().clearSelection();

        txtNombre.clear();
        txtApellidoPaterno.clear();
        txtApellidoMaterno.clear();
        cmbGenero.getSelectionModel().select(null);
        txtRfc.clear();
        txtTelefono.clear();

        txtUsuario.clear();
        txtContrasenia.clear();
        txtDomicilio.clear();
        txtBuscar.clear();

        txtNumeroEmpleado.clear();
        cmbPuesto.getSelectionModel().select(null);

    }

    private ObservableList<Empleado> obtenerDatos(boolean empleadosActivos)
            throws IOException
    {
        ObservableList<Empleado> empleados
                = FXCollections.observableArrayList();

        Platform.runLater(() ->
        {
            double precio = 0;
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Consultando servidor... ");
            alerta.setContentText("Consultando datos del servidor...");

            try
            {
                alerta.show();

                JsonArray jsonArray = api.consultarListado("empleado");
                if (jsonArray == null)
                    return;
                Empleado e;

                for (JsonElement jsonElement : jsonArray)
                {
                    e = gson.fromJson(jsonElement, Empleado.class);

                    if (empleadosActivos)
                    {
                        if (e.getEstatus() == 1)
                            empleados.add(e);
                    }
                    else if (e.getEstatus() == 0)
                        empleados.add(e);
                }
                filtrarDatos(empleados);
                alerta.hide();
            }
            catch (java.net.UnknownHostException uhe)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);

                alerta.hide();

                a.setTitle("Sin servicio a internet");
                a.setContentText("No se pudo conectar con el servicio");
                a.showAndWait();
            }
            catch (ConnectException ex)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);

                alerta.hide();

                a.setTitle("Servidor no disponible");
                a.setContentText("No se pudo conectar con el servicio");
                a.showAndWait();

            }
            catch (Exception ex)
            {
                Logger.getLogger(PaneClientesControlador.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        return empleados;
    }

    private void filtrarDatos(ObservableList<Empleado> dato)
    {

        FilteredList<Empleado> filteredData
                = new FilteredList<>(dato, b -> true);

        txtBuscar.textProperty().addListener(
                (observable, oldValue, newValue) ->
        {

            filteredData.setPredicate(empleado ->
            {
                if (newValue == null || newValue.isEmpty())
                    return true;//Mostrar todos

                String busqueda = newValue.toLowerCase();

                if (empleado.getNombre().toLowerCase().contains(busqueda))
                    return true;
                else if (empleado.getApellidoPaterno().toLowerCase().contains(
                        busqueda))
                    return true;
                else if (empleado.getApellidoMaterno().toLowerCase().contains(
                        busqueda))
                    return true;
                else if (empleado.getDomicilio().toLowerCase().contains(busqueda))
                    return true;
                else if (empleado.getGenero().toLowerCase().contains(busqueda))
                    return true;
                else if (empleado.getRfc().toLowerCase().contains(busqueda))
                    return true;
                else if (empleado.getTelefono().toLowerCase().contains(busqueda))
                    return true;
                else if (empleado.getUsuario().getNombreUsuario().toLowerCase().contains(
                        busqueda))
                    return true;
                else if (empleado.getNumeroEmpleado().toLowerCase().contains(
                        busqueda))
                    return true;
                else if (empleado.getPuesto().toLowerCase().contains(busqueda))
                    return true;
                return false;

            });

        });

        SortedList<Empleado> sortedList = new SortedList<>(filteredData);

        tblEmpleados.getColumns().clear();
        limpiarCampos();

        sortedList.comparatorProperty().bind(tblEmpleados.comparatorProperty());
        tblEmpleados.setItems(sortedList);
        inicializarColumnas();

    }

    public JsonElement manejarEmpleado(Empleado e,
                                       String opcion)
    {
        System.out.println(opcion);
        System.out.println(e.toString());
        String enlace = MySpaCommons.URL_SERVER + "empleado?";

        if (!opcion.equals("DELETE"))
            enlace += "nombre=" + URLEncoder.encode(e.getNombre())
                    + "&apellidoPaterno=" + URLEncoder.encode(
                            e.getApellidoPaterno())
                    + "&apellidoMaterno=" + URLEncoder.encode(
                            e.getApellidoMaterno())
                    + "&genero=" + URLEncoder.encode(e.getGenero())
                    + "&domicilio=" + URLEncoder.encode(e.getDomicilio())
                    + "&telefono=" + URLEncoder.encode(e.getTelefono())
                    + "&rfc=" + URLEncoder.encode(e.getRfc())
                    + "&nombreUsuario="+URLEncoder.encode(e.getUsuario().getNombreUsuario())
                    + "&contrasenia=" + URLEncoder.encode(
                            e.getUsuario().getContrasenia())
                    + "&rol=" + URLEncoder.encode(e.getPuesto())
                    + "&puesto=" + URLEncoder.encode(e.getPuesto())
                    + "&estatus=" + URLEncoder.encode("" + e.getEstatus())
                    + "&foto=" + URLEncoder.encode("")
                    + "&rutaFoto=" + URLEncoder.encode("");
        else
            enlace += "numeroEmpleado=" + URLEncoder.encode(
                    e.getNumeroEmpleado());

        if (opcion.equals("PUT"))
            enlace += "&idPersona=" + URLEncoder.encode("" + e.getIdPersona())
                    + "&idUsuario=" + URLEncoder.encode(
                            "" + e.getUsuario().getIdUsuario())
                    + "numeroEmpleado=" + URLEncoder.encode(e.getNumeroEmpleado())
                    + "&nombreUsuario=" + URLEncoder.encode(
                        e.getUsuario().getNombreUsuario());

        try
        {
            System.out.println(enlace);
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(api.hacerPeticion(enlace, opcion));

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

}
