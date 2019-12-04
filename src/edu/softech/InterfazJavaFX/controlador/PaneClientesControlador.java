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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import edu.softech.InterfazJavaFX.api.Api;
import edu.softech.InterfazJavaFX.gui.WindowMain;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Usuario;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Villegas
 */
public class PaneClientesControlador implements Initializable {

    @FXML
    private JFXCheckBox chbVerInactivos;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colApellidoPaterno;

    @FXML
    private TableColumn<Cliente, String> colApellidoMaterno;

    @FXML
    private TableColumn<Cliente, String> colGenero;

    @FXML
    private TableColumn<Cliente, String> colDomicilio;

    @FXML
    private TableColumn<Cliente, String> coltelefono;

    @FXML
    private TableColumn<Cliente, String> colRfc;

    @FXML
    private TableColumn<Cliente, String> colNumeroUnico;

    @FXML
    private TableColumn<Cliente, String> colCorreo;

    @FXML
    private TableColumn<Cliente, Integer> colEstatus;

    @FXML
    private TableColumn<Cliente, String> colNombreUsuario;

    @FXML
    private TableColumn<Cliente, String> colContrasenia;

    @FXML
    private JFXTextField txtBuscar;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtApellidoPaterno;

    @FXML
    private JFXTextField txtApellidoMaterno;

    @FXML
    private JFXComboBox cmbGenero;

    @FXML
    private JFXTextField txtRfc;

    @FXML
    private JFXTextArea txtDomicilio;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXTextField txtCorreoElectronico;

    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private JFXTextField txtContrasenia;

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

    private static Method columnToFitMethod;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inicializarTabla(true);
            llenarComboBoxes();
            inicializarOyentes();

        } catch (IOException ex) {
            Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void inicializarTabla(boolean clientesActivos) throws IOException {
        tblClientes.getColumns().clear();

        limpiarCampos();
        tblClientes.setItems(obtenerDatos(clientesActivos));
        inicializarColumnas();
    }

    private void inicializarColumnas() {
        tblClientes.autosize();
        tblClientes.setDisable(false);
        try {
            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);

        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        coltelefono = new TableColumn<>("Telefono");
        coltelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono"));

        //Rfc
        colRfc = new TableColumn<>("Rfc");
        colRfc.setCellValueFactory(
                new PropertyValueFactory<>("rfc"));

        //Numero Unico de Cliente
        colNumeroUnico = new TableColumn<>("NUC");
        colNumeroUnico.setCellValueFactory(
                new PropertyValueFactory<>("numeroUnico"));

        //Correo electronico
        colCorreo = new TableColumn<>("Correo Electronico");
        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo"));

        //Estatus
        colEstatus = new TableColumn<>("Estatus");
        colEstatus.setCellValueFactory(
                new PropertyValueFactory<>("estatus"));

        //Nombre Usuario
        colNombreUsuario = new TableColumn<>("Nombre Usuario");
        colNombreUsuario.setCellValueFactory(new Callback<CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
                return new SimpleStringProperty(param.getValue().getUsuario().getNombreUsuario());
            }

        });

        //Contraseña
        colContrasenia = new TableColumn<>("Contraseña");
        colContrasenia.setCellValueFactory(new Callback<CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
                return new SimpleStringProperty(param.getValue().getUsuario().getContrasenia());
            }

        });
        tblClientes.getColumns().addAll(
                colNumeroUnico, colNombre, colApellidoPaterno,
                colApellidoMaterno, colGenero, colDomicilio,
                coltelefono, colRfc, colCorreo, colEstatus,
                colNombreUsuario, colContrasenia
        );

        tblClientes.getItems().addListener(new ListChangeListener<Cliente>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Cliente> c) {
                for (Object column : tblClientes.getColumns()) {
                    try {
                        columnToFitMethod.invoke(tblClientes.getSkin(), column, -1);
                    } catch (IllegalAccessException ex) {
//                        Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
//                        Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
//                        Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });

    }

    private void llenarComboBoxes() {
        cmbGenero.getItems().addAll("Hombre", "Mujer", "Otro");
    }

    private void inicializarOyentes() {

        tblClientes.setOnMouseClicked((MouseEvent x) -> {
            llenarCampos();
        });
        tblClientes.setOnKeyReleased(x -> {
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

            } catch (IOException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
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

                if (tblClientes.getSelectionModel().getSelectedItem() == null) {
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
                if (resultado.get() == ButtonType.OK) {
                    api.manejarCliente(prepararDatos(), opcion);
                } else {
                    System.out.println("Chale");
                }
                cambiarCampos(UNC_DEFAULT, false);
                chbVerInactivos.setSelected(false);
                inicializarTabla(true);
                opcion = null;

            } catch (IOException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    private void limpiarCampos() {
        tblClientes.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtApellidoPaterno.clear();
        txtApellidoMaterno.clear();
        txtContrasenia.clear();
        txtCorreoElectronico.clear();
        txtDomicilio.clear();
        txtRfc.clear();
        txtTelefono.clear();
        txtUsuario.clear();
        cmbGenero.getSelectionModel().select(null);
        txtBuscar.clear();

    }

    private void cambiarCampos(String estilo, boolean editable) {
        if (opcion == null) {
            //No hacemos nada, opcion cancelar
        } else if (tblClientes.getSelectionModel().getSelectedItem() == null && (opcion.equals("DELETE") || opcion.equals("PUT"))) {
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

        txtCorreoElectronico.setStyle(estilo);
        txtCorreoElectronico.setEditable(editable);

        txtUsuario.setStyle(estilo);
        txtUsuario.setEditable(editable);

        txtContrasenia.setStyle(estilo);
        txtContrasenia.setEditable(editable);
    }

    private Cliente prepararDatos() throws IOException, URISyntaxException {
        Cliente c = new Cliente();
        Usuario u = new Usuario();

        if (tblClientes.getSelectionModel().getSelectedItem() != null) {
            c = tblClientes.getSelectionModel().getSelectedItem();
            u = c.getUsuario();
        }
        if (opcion != null && cmbGenero.getSelectionModel().getSelectedItem() != null) {

            c.setNombre(txtNombre.getText());
            c.setApellidoPaterno(txtApellidoPaterno.getText());
            c.setApellidoMaterno(txtApellidoMaterno.getText());

            String genero = cmbGenero.getSelectionModel().getSelectedItem().toString();

            if (genero.equals("Hombre")) {
                c.setGenero("M");
            } else if (genero.equals("Mujer")) {
                c.setGenero("F");
            } else if (genero.equals("Otro")) {
                c.setGenero("O");
            }

            c.setTelefono(txtTelefono.getText());
            c.setRfc(txtRfc.getText());
            u.setNombreUsuario(txtUsuario.getText());
            c.setCorreo(txtCorreoElectronico.getText());
            u.setContrasenia(txtContrasenia.getText());
            c.setDomicilio(txtDomicilio.getText());

            c.setUsuario(u);
        }

        return c;
    }

    private void ejecutarPeticion(Cliente c) {
        Platform.runLater(() -> {
            Usuario u = c.getUsuario();

            double precio = 0;

            TrayNotification alerta = windowMain.mostrarAlerts("Consultando servidor... ", "Consultando datos del servidor...", NotificationType.NOTICE);

            try {
                alerta.showAndWait();

                if (api.manejarCliente(c, opcion) != null) {
                    windowMain.mostrarNotificacion("Éxitoo", "El movimiento con el usuario "
                            + u.getNombreUsuario() + " fue todo un éFxito", NotificationType.SUCCESS);
                } else {
                    windowMain.mostrarNotificacion("Error",
                            "Ocurrio un problema al insertar al usuario",
                            NotificationType.ERROR);
                }
                alerta.showAndDismiss(Duration.ZERO);

                opcion = null;
                inicializarTabla(true);

            } catch (java.net.UnknownHostException uhe) {
                alerta.showAndDismiss(Duration.ZERO);

                TrayNotification a = windowMain.mostrarAlerts("Sin "
                        + "servicio a internet", "Intentelo más tarde", NotificationType.ERROR);

                a.showAndDismiss(Duration.seconds(5));
            } catch (IOException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {
//                alerta.showAndDismiss(Duration.ZERO);

                TrayNotification a = windowMain.mostrarAlerts("Error", "Inserte todo los campos", NotificationType.ERROR);

                a.showAndDismiss(Duration.seconds(5));

            }
        });
    }

    private ObservableList<Cliente> obtenerDatos(boolean clientesActivos) throws IOException {
        ObservableList<Cliente> clientes
                = FXCollections.observableArrayList();

        Platform.runLater(() -> {

            double precio = 0;

            TrayNotification alerta = windowMain.mostrarAlerts("Consultando servidor... ", "Consultando servidor... ", NotificationType.NOTICE);

            try {
                alerta.showAndWait();

                JsonArray jsonArray = api.consultarListado("cliente");
                if (jsonArray == null) {
                    return;
                }
                Cliente c;

                for (JsonElement jsonElement : jsonArray) {
                    c = gson.fromJson(jsonElement, Cliente.class
                    );
                    if (clientesActivos) {
                        if (c.getEstatus() == 1) {
                            clientes.add(c);

                        }
                    } else {
                        if (c.getEstatus() == 0) {
                            clientes.add(c);

                        }

                    }
                }
                filtrarDatos(clientes);
                alerta.showAndDismiss(Duration.ZERO);
            } catch (java.net.UnknownHostException uhe) {
                alerta.showAndDismiss(Duration.ZERO);

                windowMain.mostrarNotificacion("Sin servicio a internet",
                        "No se pudo conectar con el servicio", NotificationType.ERROR);

            } catch (ConnectException ex) {
                alerta.showAndDismiss(Duration.ZERO);

                windowMain.mostrarNotificacion("Servidor no disponible",
                        "No se pudo conectar con el servicio", NotificationType.ERROR);

            } catch (Exception ex) {
                alerta.showAndDismiss(Duration.ZERO);

                windowMain.mostrarNotificacion("Error", "Aquí", NotificationType.ERROR);

            }
        });

        return clientes;
    }

    private void llenarCampos() {
        if (tblClientes.getSelectionModel().getSelectedItem() != null) {
            Cliente c = tblClientes.getSelectionModel().getSelectedItem();
            Usuario u = c.getUsuario();

            txtNombre.setText(c.getNombre());
            txtApellidoPaterno.setText(c.getApellidoPaterno());
            txtApellidoMaterno.setText(c.getApellidoMaterno());

            if (c.getGenero().equals("M")) {
                cmbGenero.getSelectionModel().select(0);
            } else if (c.getGenero().equals("F")) {
                cmbGenero.getSelectionModel().select(1);
            } else if (c.getGenero().equals("O")) {
                cmbGenero.getSelectionModel().select(2);
            }

            txtRfc.setText(c.getRfc());
            txtDomicilio.setText(c.getDomicilio());
            txtTelefono.setText(c.getTelefono());
            txtCorreoElectronico.setText(c.getCorreo());

            txtUsuario.setText(u.getNombreUsuario());
            txtContrasenia.setText(u.getContrasenia());
        }
        cambiarCampos(UNC_DEFAULT, false);
        opcion = null;
    }

    private void filtrarDatos(ObservableList<Cliente> dato) {

        FilteredList<Cliente> filteredData
                = new FilteredList<>(dato, b -> true);

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;//Mostrar todos
                }

                String busqueda = newValue.toLowerCase();

                if (cliente.getNombre().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getApellidoPaterno().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getApellidoMaterno().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getCorreo().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getDomicilio().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getGenero().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getNumeroUnico().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getRfc().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getTelefono().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (cliente.getUsuario().getNombreUsuario().toLowerCase().contains(busqueda)) {
                    return true;
                }
                return false;

            });

        });

        SortedList<Cliente> sortedList = new SortedList<>(filteredData);

        tblClientes.getColumns().clear();

        limpiarCampos();

        sortedList.comparatorProperty().bind(tblClientes.comparatorProperty());
        tblClientes.setItems(sortedList);
        inicializarColumnas();

    }

}
