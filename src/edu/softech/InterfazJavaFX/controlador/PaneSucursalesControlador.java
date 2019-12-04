/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import com.google.gson.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.softech.InterfazJavaFX.api.*;
import edu.softech.InterfazJavaFX.gui.WindowMain;
import edu.softech.MySpa.modelo.Sucursal;
import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author pollo
 */
public class PaneSucursalesControlador implements Initializable {

    @FXML
    TextField txtNombre;
    @FXML
    TextField txtDomicilio;
    @FXML
    TextField txtLatitud;
    @FXML
    TextField txtBuscar;
    @FXML
    TextField txtLongitud;
    @FXML
    JFXButton btnEditar;
    @FXML
    JFXButton btnGuardar;
    @FXML
    JFXButton btnEliminar;
    @FXML
    JFXButton btnNuevo;
    @FXML
    JFXButton btnCancelar;

    @FXML
    TableView<Sucursal> tblSucursales;
    @FXML
    TableColumn<Sucursal, String> colEstatus;
    @FXML
    TableColumn<Sucursal, String> colID;
    @FXML
    TableColumn<Sucursal, String> colNombre;
    @FXML
    TableColumn<Sucursal, String> colDomicilio;
    @FXML
    TableColumn<Sucursal, String> colLatitud;
    @FXML
    TableColumn<Sucursal, String> colLongitud;
    @FXML
    JFXButton btnLocalizacion;
    @FXML
    JFXCheckBox chbVerInactivos;

    WindowMain windowmain = new WindowMain();
    Api api = new Api();
    int nuevo = 0;
    Gson gson = new Gson();

    private final String UNC_EDITAR = "-jfx-unfocus-color: f68a1f;";
    private final String UNC_DEFAULT = "-jfx-unfocus-color: #4d4d4d;";
    private final String UNC_NUEVO = "-jfx-unfocus-color: #00C851;";
    private final String UNC_ELIMINAR = "-jfx-unfocus-color: #ff4444;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            txtNombre.setEditable(false);
            txtDomicilio.setEditable(false);
            txtLatitud.setEditable(false);
            txtLongitud.setEditable(false);
            inicializarTabla(true);

            //LISTENER PARA QUE CAMBIEN LOS DATOS CADA QUE SE MUEVA CON LAS FLECHAS EN LA TABLA
            tblSucursales.setOnKeyReleased(x -> {
                try {
                    llenarDatos();
                } catch (Exception ex) {
                    Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            //BOTON PARA HABILITAR LOS CAMPOS CUANDO EDITE UNA SUCURSAL
            btnEditar.setOnAction(evt -> {
                cambiarCampos(UNC_EDITAR, true);
                editarSucursal();
                nuevo = 2;
            });

            //BOTON PARA ELIMINAR UNA SUCURSAL
            btnEliminar.setOnAction(evt -> {
                if (tblSucursales.getSelectionModel().getSelectedItem() != null) {
                    try {
                        cambiarCampos(UNC_ELIMINAR, false);
                        eliminarSucursal();
                    } catch (Exception ex) {
                        Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            //BOTON PARA GUARDAR LOS CAMBIOS DE UNA ACTUALIZACION O AGREGAR UNA SUCURSAL
            btnGuardar.setOnAction(evt -> {
                try {
                    cambiarCampos(UNC_DEFAULT, false);
                    guardarCambios();
//                    nuevo = 2;
                } catch (Exception ex) {
                    Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            //BOTON PARA AGREGAR UNA SUCURSAL 
            btnNuevo.setOnAction(evt -> {
                try {
                    cambiarCampos(UNC_NUEVO, true);
                    agregarSucursal();
                    nuevo = 1;
                    //Dans l'universe
                } catch (Exception ex) {

                }
            });

            //BOTON DE CANCELAR
            btnCancelar.setOnAction(x -> {
                limpiarCampos();
                cambiarCampos(UNC_DEFAULT, false);
            });

            //BOTON PARA MOSTRAR LOCALIZACION DE SUCURSAL
            btnLocalizacion.setOnAction(evt -> {
                if (txtNombre.getText() != null) {
                    try {
                        obtenerLocalizacion();
                    } catch (Exception e) {

                    }
                }
            });

            //CHECKBOX PARA MOSTRAR SUCURSALES INACTIVOS
            chbVerInactivos.setOnAction(evt -> {
                try {
                    if (!chbVerInactivos.selectedProperty().get()) {
                        inicializarTabla(true);
                    } else {
                        inicializarTabla(false);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (Exception ex) {
            Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //METODO PARA CONSULTAR SUCURSALES INACTIVAS
    //METODO PARA CANCELAR CUALQUIER ACCION
    private void limpiarCampos() {
        tblSucursales.getSelectionModel().clearSelection();

        txtNombre.clear();
        txtDomicilio.clear();
        txtLatitud.clear();
        txtLongitud.clear();

    }

    //METODO PARA AGREGAR NUEVA SUCURSAL
    public void agregarSucursal() throws Exception {
        tblSucursales.getSelectionModel().clearSelection();
        txtNombre.setText("");
        txtDomicilio.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
        txtNombre.setEditable(true);
        txtDomicilio.setEditable(true);
        txtLatitud.setEditable(true);
        txtLongitud.setEditable(true);
        nuevo = 1;
    }

    //METODO PARA EDITAR UNA SUCURSAL
    public void editarSucursal() {
        txtNombre.setEditable(true);
        txtDomicilio.setEditable(true);
        txtLatitud.setEditable(true);
        txtLongitud.setEditable(true);

    }

    //METODO PARA ELIMINAR(LÓGICA) UNA SUCURSAL
    public void eliminarSucursal() throws UnsupportedEncodingException, Exception {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/res/lotus.png").toString()));

        a.setTitle("Aviso");
        a.setHeaderText(null);
        a.setContentText("¿Está seguro que desea eliminar " + txtNombre.getText() + "?");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            Sucursal su = new Sucursal();
            su = tblSucursales.getSelectionModel().getSelectedItem();

            String id = URLEncoder.encode(String.valueOf(su.getIdSucursal()), "UTF-8");

            String objeto = "sucursal?idSucursal=" + id;

            JsonObject json = api.eliminarSucursal(objeto);

            windowmain.mostrarNotificacion("Alerta", "Sucursal " + su.getNombre() + " eliminada", NotificationType.WARNING);
            limpiarCampos();
            cambiarCampos(UNC_DEFAULT, false);

            Platform.runLater(() -> {
                try {
                    tblSucursales.setItems(obtenerDatos(true));
                } catch (IOException ex) {
                    Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else {

        }
    }

    //METODO PARA GUARDAR LOS CAMBIOS EN UNA SUCURSAL
    public void guardarCambios() throws Exception {
        if (nuevo > 0) {
            //ACTUALIZA SUCURSAL
            switch (nuevo) {
                case 2: {
                    txtNombre.setEditable(false);
                    txtDomicilio.setEditable(false);
                    txtLatitud.setEditable(false);
                    txtLongitud.setEditable(false);

                    Sucursal su = new Sucursal();
                    su = tblSucursales.getSelectionModel().getSelectedItem();
                    String id = URLEncoder.encode(String.valueOf(su.getIdSucursal()), "UTF-8");
                    String nom = URLEncoder.encode(txtNombre.getText(), "UTF-8");
                    String dom = URLEncoder.encode(txtDomicilio.getText(), "UTF-8");
                    String lat = URLEncoder.encode(txtLatitud.getText(), "UTF-8");
                    String lon = URLEncoder.encode(txtLongitud.getText(), "UTF-8");
                    String objeto = "sucursal?idSucursal=" + id + "&nombre=" + nom + "&domicilio=" + dom + "&latitud=" + lat + "&longitud=" + lon;
                    JsonObject json = api.actualizarSucursal(objeto);

                    windowmain.mostrarNotificacion("Aviso", "¡Actualización exitósa!", NotificationType.SUCCESS);
                    Platform.runLater(() -> {
                        try {
                            tblSucursales.setItems(obtenerDatos(true));
                        } catch (IOException ex) {
                            Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    limpiarCampos();
                    break;
                }
                case 1: {
                    //INSERTA SUCURSAL
                    Sucursal su = new Sucursal();
                    String nom = URLEncoder.encode(txtNombre.getText(), "UTF-8");
                    String dom = URLEncoder.encode(txtDomicilio.getText(), "UTF-8");
                    String lat = URLEncoder.encode(txtLatitud.getText(), "UTF-8");
                    String lon = URLEncoder.encode(txtLongitud.getText(), "UTF-8");
                    su.setNombre(nom);
                    su.setDomicilio(dom);
                    su.setLatitud(Double.parseDouble(lat));
                    su.setLongitud(Double.parseDouble(lon));
                    String objeto = "sucursal?" + "&nombre=" + nom + "&domicilio=" + dom + "&latitud=" + lat + "&longitud=" + lon;
                    JsonObject json = api.insertarSucursal(objeto);
                    Platform.runLater(() -> {
                        try {
                            tblSucursales.setItems(obtenerDatos(true));
                        } catch (IOException ex) {
                            Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    limpiarCampos();
                    break;
                }
                case 0:
                    break;
                default:
                    break;
            }
        }
    }

    //METODO PARA LLENAR LA TABLA CON SUCURSALES ACTIVAS
    private void inicializarTabla(boolean sucursalesActivas) throws IOException {
        tblSucursales.getColumns().clear();

        Platform.runLater(() -> {
            try {
                tblSucursales.setItems(obtenerDatos(sucursalesActivas));
                inicializarColumnas();
            } catch (IOException ex) {
                Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tblSucursales.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                try {
                    llenarDatos();
                } catch (Exception ex) {

                }
            }
        });

    }

    //METODO QUE INGRESA LOS DATOS EN LA TABLA
    public void llenarDatos() throws Exception {
        if (tblSucursales.getSelectionModel().getSelectedItem() != null) {
            Sucursal sucursalSeleccionada = tblSucursales.getSelectionModel().getSelectedItem();
            txtNombre.setText(sucursalSeleccionada.getNombre());
            txtDomicilio.setText(sucursalSeleccionada.getDomicilio());
            txtLatitud.setText(String.valueOf(sucursalSeleccionada.getLatitud()));
            txtLongitud.setText(String.valueOf(sucursalSeleccionada.getLongitud()));

        }
    }

    //METODO PARA ABRIR NUEVA VENTANA DE NAVEGADOR CON LOCALIZACION DE SUCURSAL
    public void obtenerLocalizacion() throws Exception {
        String nom = URLEncoder.encode(txtNombre.getText(), "UTF-8");
        String dom = (txtDomicilio.getText());
        String lat = URLEncoder.encode(txtLatitud.getText(), "UTF-8");
        String lon = URLEncoder.encode(txtLongitud.getText(), "UTF-8");
        String url = "http://localhost:8080/MySpa/mapa.html?nombre=" + nom + "&domicilio=" + dom + "&lati=" + lat + "&long=" + lon;
        String acum = "";
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == ' ') {
                acum += "%20";
            } else {
                acum += url.charAt(i);
            }
        }
        Desktop.getDesktop().browse(new URI(acum));
    }

    //METODO PARA TRAER TODAS LAS SUCURSALES
    private ObservableList<Sucursal> obtenerDatos(boolean sucursalesActivas) throws IOException {
        ObservableList<Sucursal> sucursales
                = FXCollections.observableArrayList();

        Platform.runLater(() -> {

            JsonArray jsonArray;
            try {
                jsonArray = api.consultarListado("sucursal");

            if (jsonArray == null) {
                return;
            }
            Sucursal s;

            for (JsonElement jsonElement : jsonArray) {
                s = gson.fromJson(jsonElement, Sucursal.class);
                if (sucursalesActivas) {
                    if (s.getEstatus() == 1) {
                        sucursales.add(s);

                    }
                } else {
                    if (s.getEstatus() == 0) {
                        sucursales.add(s);

                    }

                }
            }
            filtrarDatos(sucursales);
            } catch (IOException ex) {
                Logger.getLogger(PaneSucursalesControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return sucursales;
    }

    //METODO PARA CAMBIAR DE COLOR LOS TEXTFIELDS
    private void cambiarCampos(String estilo, boolean editable) {
        txtNombre.setStyle(estilo);
        txtNombre.setEditable(editable);

        txtDomicilio.setStyle(estilo);
        txtDomicilio.setEditable(editable);

        txtLatitud.setStyle(estilo);
        txtLatitud.setEditable(editable);

        txtLongitud.setStyle(estilo);
        txtLongitud.setEditable(editable);

    }

    private void filtrarDatos(ObservableList<Sucursal> dato) {

        FilteredList<Sucursal> filteredData
                = new FilteredList<>(dato, b -> true);

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(sucursal -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;//Mostrar todos
                }
                try {

                    String busqueda = newValue.toLowerCase();

                    if (sucursal.getNombre().toLowerCase().contains(busqueda)) {
                        return true;
                    } else if (sucursal.getDomicilio().toLowerCase().contains(busqueda)) {
                        return true;
                    } else if (("" + sucursal.getLatitud()).contains(busqueda)) {
                        return true;
                    } else if (("" + sucursal.getLongitud()).contains(busqueda)) {
                        return true;
                    }

                    return false;
                } catch (Exception e) {
                    return false;
                }

            });

        });

        SortedList<Sucursal> sortedList = new SortedList<>(filteredData);

        tblSucursales.getColumns().clear();

        limpiarCampos();

        sortedList.comparatorProperty().bind(tblSucursales.comparatorProperty());
        tblSucursales.setItems(sortedList);
        inicializarColumnas();

    }

    public void inicializarColumnas() {
        //Estatus
        colEstatus = new TableColumn<>("Estatus");
        colEstatus.setCellValueFactory(
                new PropertyValueFactory<>("estatus"));

        //Nombre
        colNombre = new TableColumn<>("Nombre");
        colNombre.setMinWidth(50);
        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));

        //Domicilio       
        colDomicilio = new TableColumn<>("Domicilio");
        colDomicilio.setMinWidth(120);
        colDomicilio.setCellValueFactory(
                new PropertyValueFactory<>("domicilio"));

        //Latitud
        colLatitud = new TableColumn<>("Latitud");
        colLatitud.setCellValueFactory(
                new PropertyValueFactory<>("latitud"));

        //Longitud
        colLongitud = new TableColumn<>("Longitud");
        colLongitud.setCellValueFactory(
                new PropertyValueFactory<>("longitud"));

        tblSucursales.getColumns().addAll(
                colEstatus, colNombre, colDomicilio,
                colLatitud, colLongitud
        );
    }

}
