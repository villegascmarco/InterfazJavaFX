/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.softech.InterfazJavaFX.api.Api;
import edu.softech.InterfazJavaFX.gui.WindowMain;
import edu.softech.MySpa.modelo.Tratamiento;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author logic
 */
public class PaneTratamientosControlador implements Initializable {

    @FXML
    private AnchorPane windowTratamientos;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private TableView<Tratamiento> tblTratamientos;
    @FXML
    private TableColumn<Tratamiento, String> colNombre;
    @FXML
    private TableColumn<Tratamiento, String> colDescripcion;
    @FXML
    private TableColumn<Tratamiento, Float> colCosto;
    @FXML
    private TableColumn<Tratamiento, Integer> colEstatus;
    @FXML
    private JFXCheckBox chbVerInactivos;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private JFXTextField txtCosto;
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


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inicializarTabla(true);
            inicializarOyentes();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void inicializarTabla(boolean tratamientosActivos) throws IOException {
        tblTratamientos.getColumns().clear();
        limpiarCampos();
        tblTratamientos.setItems(obtenerDatos(tratamientosActivos));
        inicializarColumnas();
    }

    private void inicializarColumnas() {
        tblTratamientos.autosize();
        tblTratamientos.setDisable(false);

//        try {
//            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
//
//        } catch (NoSuchMethodException | SecurityException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }
        
        // Columna Nombre
        colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(
            new PropertyValueFactory<>("nombre"));
        
        // Columna Descripcion
        colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(
            new PropertyValueFactory<>("descripcion"));
        
        // Columna costo
        colCosto = new TableColumn<>("Costo");
        colCosto.setCellValueFactory(
            new PropertyValueFactory<>("costo"));
        
        // Columna Estatus
        colEstatus = new TableColumn<>("Estatus");
        colEstatus.setCellValueFactory(
            new PropertyValueFactory<>("estatus"));
        
        tblTratamientos.getColumns().addAll(colNombre,
                colDescripcion, colCosto, colEstatus);
   
    }
    
    private void inicializarOyentes() {
        
        tblTratamientos.setOnMouseClicked((MouseEvent x) -> {
            llenarCampos();
        });
        
        tblTratamientos.setOnKeyReleased(x -> {
            llenarCampos();
        });
        
        btnEditar.setOnAction(x -> {
            opcion = "PUT";
            cambiarCampos(UNC_EDITAR, true);
        });
        
        btnGuardar.setOnAction(x -> {
            try {

                if (opcion == null) {
                    return;
                }
                cambiarCampos(UNC_DEFAULT, false);

                ejecutarPeticion(prepararDatos());

            } catch (IOException | URISyntaxException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        btnNuevo.setOnAction(x -> {
            opcion = "POST";
            cambiarCampos(UNC_NUEVO, true);
            chbVerInactivos.setSelected(false);
            limpiarCampos();
        });
        
        btnCancelar.setOnAction(x -> {
            opcion = null;
            limpiarCampos();
            cambiarCampos(UNC_DEFAULT, false);
        });
        
        btnElminar.setOnAction(x -> {
            try {
                opcion = "DELETE";

                if (tblTratamientos.getSelectionModel().getSelectedItem() == null) {
                    opcion = null;
                    cambiarCampos(UNC_DEFAULT, false);
                    return;
                }

                cambiarCampos(UNC_ELIMINAR, false);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("¿Esta seguro de eliminar el tratamiento " + txtNombre.getText() + "?");

                Optional<ButtonType> resultado = alert.showAndWait();
                if (resultado.get() == ButtonType.OK) {
                    api.manejarTratamiento(prepararDatos(), opcion);
                } else {
                    System.out.println("Cancelado");
                }
                cambiarCampos(UNC_DEFAULT, false);
                chbVerInactivos.setSelected(false);
                inicializarTabla(true);
                opcion = null;

            } catch (IOException | URISyntaxException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        chbVerInactivos.setOnAction(x -> {
            try {
                if (!chbVerInactivos.selectedProperty().get()) {
                    inicializarTabla(true);
                } else {
                    inicializarTabla(false);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
    
    private void limpiarCampos() {
        txtBuscar.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtCosto.clear();
    }
    
    private void cambiarCampos(String estilo, boolean editable) {
        if (opcion == null) {
            return;
        } else if (tblTratamientos.getSelectionModel().getSelectedItem() == null 
                && (opcion.equals("DELETE") || opcion.equals("PUT"))) {
            opcion = null;
            cambiarCampos(UNC_DEFAULT, false);
            return;
        }
        txtNombre.setStyle(estilo);
        txtNombre.setEditable(editable);
        
        txtDescripcion.setStyle(estilo);
        txtDescripcion.setEditable(editable);
        
        txtCosto.setStyle(estilo);
        txtCosto.setEditable(editable);
        
    }
    
    private Tratamiento prepararDatos() throws IOException, URISyntaxException {
        Tratamiento tratamiento = new Tratamiento();
        
        if (tblTratamientos.getSelectionModel().getSelectedItem() != null) {
            tratamiento = tblTratamientos.getSelectionModel().getSelectedItem();
        }
        
        if (opcion != null) {
            tratamiento.setNombre(txtNombre.getText());
            tratamiento.setDescripcion(txtDescripcion.getText());
            tratamiento.setCosto(Float.parseFloat(txtCosto.getText()));
        }
        
        return tratamiento;
    }
    
    private void ejecutarPeticion(Tratamiento tratamiento) {
        Platform.runLater(() -> {
            
            TrayNotification alerta = windowMain.mostrarAlerts("Consultando servidor... ",
                    "Consultando datos del servidor...", NotificationType.NOTICE);
            
            try {
                alerta.showAndWait();
                
                if(api.manejarTratamiento(tratamiento, opcion) != null) {
                    windowMain.mostrarNotificacion("Exito", "El movimiento con el tratamiento"
                            + tratamiento.getNombre() + "fue todo un exito", NotificationType.SUCCESS);
                } else {
                    windowMain.mostrarNotificacion("Error",
                            "Ocurrio un problema al realizar el movimiento",
                            NotificationType.ERROR);
                }
                alerta.showAndDismiss(Duration.ZERO);
                
                opcion = null;
                inicializarTabla(true);
                
            } catch (java.net.UnknownHostException uhe) {
                alerta.showAndDismiss(Duration.ZERO);

                TrayNotification a = windowMain.mostrarAlerts("Sin conexión",
                        "Intentelo más tarde", NotificationType.ERROR);
                a.showAndDismiss(Duration.seconds(5));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } catch (NullPointerException e) {
                TrayNotification a = windowMain.mostrarAlerts("Error", "Debe insertar todo los campos", NotificationType.ERROR);
                a.showAndDismiss(Duration.seconds(5));
            }
        });
    }
    
    private ObservableList<Tratamiento> obtenerDatos(boolean tratamientosActivos) throws IOException {
        ObservableList<Tratamiento> tratamientos
                = FXCollections.observableArrayList();
        
        Platform.runLater(() -> {
            TrayNotification alerta = windowMain.mostrarAlerts("Consultando servidor... ",
                    "Consultando servidor... ", NotificationType.NOTICE);
            
            try {
                alerta.showAndWait();
                
                JsonArray jsonArray = api.consultarListado("tratamiento");
                if (jsonArray == null) {
                    return;
                }
                Tratamiento tratamiento;
                
                for (JsonElement jsonElement : jsonArray) {
                    tratamiento = gson.fromJson(jsonElement, Tratamiento.class);
                    
                    if(tratamientosActivos) {
                        if(tratamiento.getEstatus() == 1) {
                            tratamientos.add(tratamiento);
                        }
                    } else {
                        if(tratamiento.getEstatus() == 0) {
                            tratamientos.add(tratamiento);
                        }
                    }
                }
                filtrarDatos(tratamientos);
                alerta.showAndDismiss(Duration.ZERO);
                
            } catch (java.net.UnknownHostException | ConnectException ce) {
                alerta.showAndDismiss(Duration.ZERO);
                windowMain.mostrarNotificacion("Sin servicio",
                        "No se pudo conectar con el servicio", NotificationType.ERROR);
                ce.printStackTrace();
            } catch (Exception ex) {
                windowMain.mostrarNotificacion("Error"
                        , "Cheque la consola para más información", NotificationType.ERROR);
                ex.printStackTrace();
            }
        });
        
        return tratamientos;
    }
    
    private void llenarCampos() {
        if(tblTratamientos.getSelectionModel().getSelectedItem() != null) {
            Tratamiento tratamiento = tblTratamientos.getSelectionModel().getSelectedItem();
            
            txtNombre.setText(tratamiento.getNombre());
            txtDescripcion.setText(tratamiento.getDescripcion());
            txtCosto.setText(Float.toString(tratamiento.getCosto()));
            
        }
        cambiarCampos(UNC_DEFAULT, false);
        opcion = null;
    }
    
    private void filtrarDatos(ObservableList<Tratamiento> tratamientos) {
        
        FilteredList<Tratamiento> filteredData
                = new FilteredList<>(tratamientos, b -> true);
        
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            
                filteredData.setPredicate(tratamiento -> {
                    if (newValue == null || newValue.isEmpty()) {
                    return true;
                    }
                    
                    String busqueda = newValue.toLowerCase();
                    
                    if(tratamiento.getNombre().toLowerCase().contains(busqueda)){
                        return true;
                    } else if(tratamiento.getDescripcion().toLowerCase().contains(busqueda)) {
                        return true;
                    } else if(Float.toString(tratamiento.getCosto()).toLowerCase().contains(busqueda)){
                        return true;
                    }
                    return false;
                });
        });
        
        SortedList<Tratamiento> sortedList = new SortedList<>(filteredData);
        tblTratamientos.getColumns().clear();
        limpiarCampos();
        sortedList.comparatorProperty().bind(tblTratamientos.comparatorProperty());
        tblTratamientos.setItems(sortedList);
        inicializarColumnas();
    }

}
