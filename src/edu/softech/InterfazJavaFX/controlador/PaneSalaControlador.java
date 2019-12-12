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
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.softech.InterfazJavaFX.api.Api;
import edu.softech.InterfazJavaFX.api.MySpaCommons;
import edu.softech.InterfazJavaFX.gui.WindowMain;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Sala;
import edu.softech.MySpa.modelo.Sucursal;
import edu.softech.MySpa.modelo.Usuario;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import tray.notification.NotificationType;

/**
 *
 * @author Esau
 */
public class PaneSalaControlador implements Initializable
{

    @FXML
    private JFXTextField txtBuscar;

    @FXML
    private JFXCheckBox chbInactivos;

    @FXML
    private AnchorPane apDerecha;

    @FXML
    private ImageView imgFotografia;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtDescripcion;

    @FXML
    private JFXComboBox cmbSucursal;

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
    private TableView<Sala> tblSalas;

    @FXML
    private TableColumn<Sala, String> colNombre;

    @FXML
    private TableColumn<Sala, String> colDescripcion;

    @FXML
    private TableColumn<Sala, Integer> colEstatus;

    @FXML
    private TableColumn<Sala, String> colSucursal;

    @FXML
    private JFXButton btnImgEscoger;

    private Api api = new Api();

    private Gson gson = new Gson();

    private WindowMain windowMain = new WindowMain();

    private String opcion = null;//Indica la opcion seleccionada por el cliente

    /**
     * Gama de colores según las opciones
     */
    private final String UNC_EDITAR = "-jfx-unfocus-color: f68a1f;";
    private final String UNC_DEFAULT = "-jfx-unfocus-color: #4d4d4d;";
    private final String UNC_NUEVO = "-jfx-unfocus-color: #00C851;";
    private final String UNC_ELIMINAR = "-jfx-unfocus-color: #ff4444;";

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

    private void inicializarTabla(boolean clientesActivos)
            throws IOException
    {
        //System.out.println("Activos "+clientesActivos);
        tblSalas.getColumns().clear();
        limpiarCampos();
        tblSalas.setItems(obtenerDatos(clientesActivos));
        inicializarColumnas();
    }

    private void inicializarColumnas()
    {
        tblSalas.getColumns().clear();

        colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colDescripcion = new TableColumn<>("Descripcion");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>(
                "descripcion"));

        colEstatus = new TableColumn<>("Estatus");
        colEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        colSucursal = new TableColumn<>("Sucursal");
        colSucursal.setCellValueFactory(
                new Callback<CellDataFeatures<Sala, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(
                    CellDataFeatures<Sala, String> param)
            {
                return new SimpleStringProperty(
                        param.getValue().getSucursal().getNombre());
            }
        });

        // agrega las columnas a la tabla
        tblSalas.getColumns().setAll(
                colNombre,
                colDescripcion,
                colEstatus,
                colSucursal);
    }

    private void llenarComboBoxes()
    {
        Platform.runLater(() ->
        {

            try
            {
                JsonArray jsonArray = api.consultarListado("sucursal");
                if (jsonArray == null)
                    return;

                Sucursal sucursalAux;

                for (JsonElement jsonElement : jsonArray)
                {
                    sucursalAux = gson.fromJson(jsonElement, Sucursal.class);

                    if (sucursalAux.getEstatus() == 1)
                        cmbSucursal.getItems().add(
                                sucursalAux.getIdSucursal() + " " + sucursalAux.getNombre());
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(PaneSalaControlador.class.getName()).log(
                        Level.SEVERE,
                        null, ex);
            }

        });
    }

    private void inicializarOyentes()
    {

        tblSalas.setOnMouseClicked((MouseEvent x) ->
        {
            llenarCampos();
        });
        tblSalas.setOnKeyReleased(x ->
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
            try
            {

                if (opcion == null)
                    return;
                cambiarCampos(UNC_DEFAULT, false);

                Sala sala = prepararDatos();
                ejecutarPeticion(sala);

            }
            catch (IOException ex)
            {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
            catch (URISyntaxException ex)
            {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
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

                if (tblSalas.getSelectionModel().getSelectedItem() == null)
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
                {
                    Sala salaEliminar = prepararDatos();
                    manejarSala(salaEliminar, opcion);
                }
                else
                    System.out.println("Chale");
                cambiarCampos(UNC_DEFAULT, false);

                if (!chbInactivos.selectedProperty().get())
                    inicializarTabla(true);
                else
                    inicializarTabla(false);

                opcion = null;

            }
            catch (IOException ex)
            {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
            catch (URISyntaxException ex)
            {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        });

        chbInactivos.setOnAction(x ->
        {
            try
            {
                if (!chbInactivos.selectedProperty().get())
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

        btnImgEscoger.setOnAction(evt ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(null);

            Image image = new Image(file.toURI().toString());

            try
            {
                imgFotografia.setImage(ImgController.decode(
                        ImgController.encode(image)));
            }
            catch (IOException ex)
            {
                Logger.getLogger(PaneSalaControlador.class.getName()).log(
                        Level.SEVERE,
                        null, ex);
            }
        });
    }

    private void limpiarCampos()
    {
        tblSalas.getSelectionModel().clearSelection();

        imgFotografia.setImage(null);
        txtNombre.clear();
        txtDescripcion.clear();
        cmbSucursal.getSelectionModel().select(null);
        txtBuscar.clear();

    }

    private void cambiarCampos(String estilo,
                               boolean editable)
    {
        if (opcion == null)
        {
            //No hacemos nada, opcion cancelar
        }
        else if (tblSalas.getSelectionModel().getSelectedItem() == null && (opcion.equals(
                "DELETE") || opcion.equals("PUT")))
        {
            opcion = null;
            cambiarCampos(UNC_DEFAULT, false);
            return;
        }
        txtNombre.setStyle(estilo);
        txtNombre.setEditable(editable);

        txtDescripcion.setStyle(estilo);
        txtDescripcion.setEditable(editable);

        cmbSucursal.setStyle(estilo);
        cmbSucursal.setDisable(!editable);
    }

    private Sala prepararDatos()
            throws IOException, URISyntaxException
    {
        Sala sala = new Sala();
        Sucursal sucursal = new Sucursal();

        if (tblSalas.getSelectionModel().getSelectedItem() != null)
        {
            sala = tblSalas.getSelectionModel().getSelectedItem();
            sucursal = sala.getSucursal();
        }
        if (opcion.equals("POST") || opcion.equals("PUT"))
        {

            sala.setNombre(txtNombre.getText());
            sala.setDescripcion(txtDescripcion.getText());

            // String genero = cmbSucursal.getSelectionModel().getSelectedItem().toString();
            // asignar a sucursal
            /*
             * FALTA
             */
            String sucursalIdString = cmbSucursal.getSelectionModel().getSelectedItem().toString().split(
                    " ")[0];

            // COMBO E IMAGEN
            try
            {
                sala.getSucursal().setIdSucursal(Integer.valueOf(
                        sucursalIdString));
                
                sala.setFoto(ImgController.encode(imgFotografia.getImage()));
            }
            catch (Exception e)
            {
            }
        }

        return sala;
    }

    private void ejecutarPeticion(Sala sala)
    {
        Platform.runLater(() ->
        {
            Sucursal sucursal = sala.getSucursal();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Consultando servidor... ");
            alerta.setContentText("Consultando datos del servidor...");

            try
            {
                alerta.show();

                if (manejarSala(sala, opcion) != null)
                    windowMain.mostrarNotificacion("Agregado con"
                            + " éxito", "La sala "
                            + sala.getNombre() + " fue agregado "
                            + "con exito", NotificationType.SUCCESS);
                else
                    windowMain.mostrarNotificacion("Error",
                            "Ocurrio un problema al insertar la sala",
                            NotificationType.ERROR);

                opcion = null;

                if (!chbInactivos.selectedProperty().get())
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

    private ObservableList<Sala> obtenerDatos(boolean activos)
            throws IOException
    {
        ObservableList<Sala> salas
                = FXCollections.observableArrayList();

        Platform.runLater(() ->
        {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Consultando servidor... ");
            alerta.setContentText("Consultando datos del servidor...");

            try
            {
                alerta.show();

                JsonArray jsonArray = api.consultarListado("sala");
                if (jsonArray == null)
                    return;

                Sala salaAux;

                for (JsonElement jsonElement : jsonArray)
                {

                    //System.out.println(jsonArray.toString());
                    salaAux = gson.fromJson(jsonElement, Sala.class);

                    if (activos && salaAux.getEstatus() == 1)
                        salas.add(salaAux);
                    else if (!activos && salaAux.getEstatus() == 0)
                        salas.add(salaAux);
                }
                filtrarDatos(salas);
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

        return salas;
    }

    private void llenarCampos()
    {
        if (tblSalas.getSelectionModel().getSelectedItem() != null)
        {
            Sala sala = tblSalas.getSelectionModel().getSelectedItem();
            Sucursal sucursal = sala.getSucursal();

            Platform.runLater(() ->
            {
                try
                {
                    imgFotografia.setImage(ImgController.decode(sala.getFoto()));
                }
                catch (Exception ex)
                {
                    imgFotografia.setImage(null);
                }
            });

            txtNombre.setText(sala.getNombre());
            txtDescripcion.setText(sala.getDescripcion());

            /*
             * asignar sucursal
             */
            cmbSucursal.getSelectionModel().select(
                    sucursal.getIdSucursal() + " " + sucursal.getNombre());
        }
        cambiarCampos(UNC_DEFAULT, false);
        opcion = null;
    }

    private void filtrarDatos(ObservableList<Sala> dato)
    {

        FilteredList<Sala> filteredData
                = new FilteredList<>(dato, b -> true);

        txtBuscar.textProperty().addListener(
                (observable, oldValue, newValue) ->
        {

            filteredData.setPredicate(sala ->
            {
                if (newValue == null || newValue.isEmpty())
                    return true;//Mostrar todos

                String busqueda = newValue.toLowerCase();

                if (sala.getNombre().toLowerCase().contains(busqueda))
                    return true;
                else if (sala.getDescripcion().toLowerCase().contains(busqueda))
                    return true;
                return false;

            });

        });

        SortedList<Sala> sortedList = new SortedList<>(filteredData);

        tblSalas.getColumns().clear();
        limpiarCampos();

        sortedList.comparatorProperty().bind(tblSalas.comparatorProperty());
        tblSalas.setItems(sortedList);
        inicializarColumnas();

    }

    private JsonElement manejarSala(Sala sala,
                                    String opcion)
    {
        System.out.println(opcion);
        //System.out.println(sala.toString());
        String enlace = MySpaCommons.URL_SERVER + "sala?";
        if (!opcion.equals("DELETE")) {
            enlace += "nombre=" + URLEncoder.encode(sala.getNombre())
                    + "&descripcion=" + URLEncoder.encode(sala.getDescripcion())
                    + "&idSucursal=" + URLEncoder.encode("" + 1);
            
            try
            {
                //enlace += "&foto=" + URLEncoder.encode(sala.getFoto());
                enlace += "&foto=" +URLEncoder.encode("");
                //System.out.println(URLEncoder.encode(sala.getFoto()));
                System.out.println(
                        ImgController.encode(imgFotografia.getImage()).length()
                );
            }
            catch (Exception e)
            {
                enlace += "&foto=" +URLEncoder.encode("");
            }
        }
        else 
            enlace += "idSala=" + URLEncoder.encode("" + sala.getIdSala());

        if (opcion.equals("PUT"))
            enlace += "&idSala=" + URLEncoder.encode("" + sala.getIdSala());
        try
        {
             // System.out.println(enlace);
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
