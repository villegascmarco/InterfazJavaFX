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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.softech.InterfazJavaFX.api.Api;
import edu.softech.InterfazJavaFX.gui.WindowMain;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Usuario;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Villegas
 */
public class PaneClientesControlador implements Initializable {

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableColumn<Cliente, Integer> colIdPersona;

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
    private TableColumn<Cliente, Integer> colIdCliente;

    @FXML
    private TableColumn<Cliente, String> colNumeroUnico;

    @FXML
    private TableColumn<Cliente, String> colCorreo;

    @FXML
    private TableColumn<Cliente, Integer> colEstatus;

    @FXML
    private TableColumn<Cliente, Integer> colIdUsuario;

    @FXML
    private TableColumn<Cliente, String> colNombreUsuario;

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

    private Api api = new Api();

    private Gson gson = new Gson();

    private WindowMain windowMain = new WindowMain();

    private String opcion = null;

    private final String UNC_EDITAR = "-jfx-unfocus-color: f68a1f;";
    private final String UNC_DEFAULT = "-jfx-unfocus-color: #4d4d4d;";
    private final String UNC_NUEVO = "-jfx-unfocus-color: #00C851;";
    private final String UNC_ELIMINAR = "-jfx-unfocus-color: #ff4444;";

    public TableView<Cliente> getTblClientes() {
        return tblClientes;
    }

    public TableColumn<Cliente, Integer> getColIdPersona() {
        return colIdPersona;
    }

    public TableColumn<Cliente, String> getColNombre() {
        return colNombre;
    }

    public TableColumn<Cliente, String> getColApellidoPaterno() {
        return colApellidoPaterno;
    }

    public TableColumn<Cliente, String> getColApellidoMaterno() {
        return colApellidoMaterno;
    }

    public TableColumn<Cliente, String> getColGenero() {
        return colGenero;
    }

    public TableColumn<Cliente, String> getColDomicilio() {
        return colDomicilio;
    }

    public TableColumn<Cliente, String> getColtelefono() {
        return coltelefono;
    }

    public TableColumn<Cliente, String> getColRfc() {
        return colRfc;
    }

    public TableColumn<Cliente, Integer> getColIdCliente() {
        return colIdCliente;
    }

    public TableColumn<Cliente, String> getColNumeroUnico() {
        return colNumeroUnico;
    }

    public TableColumn<Cliente, String> getColCorreo() {
        return colCorreo;
    }

    public TableColumn<Cliente, Integer> getColEstatus() {
        return colEstatus;
    }

    public TableColumn<Cliente, Integer> getColIdUsuario() {
        return colIdUsuario;
    }

    public TableColumn<Cliente, String> getColNombreUsuario() {
        return colNombreUsuario;
    }

    public JFXTextField getTxtNombre() {
        return txtNombre;
    }

    public JFXTextField getTxtApellidoPaterno() {
        return txtApellidoPaterno;
    }

    public JFXTextField getTxtApellidoMaterno() {
        return txtApellidoMaterno;
    }

    public JFXComboBox getCmbGenero() {
        return cmbGenero;
    }

    public JFXTextField getTxtRfc() {
        return txtRfc;
    }

    public JFXTextArea getTxtDomicilio() {
        return txtDomicilio;
    }

    public JFXTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JFXTextField getTxtCorreoElectronico() {
        return txtCorreoElectronico;
    }

    public JFXTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JFXTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JFXButton getBtnNuevo() {
        return btnNuevo;
    }

    public JFXButton getBtnGuardar() {
        return btnGuardar;
    }

    public JFXButton getBtnEditar() {
        return btnEditar;
    }

    public JFXButton getBtnElminar() {
        return btnElminar;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inicializarTabla();
            llenarComboBoxes();
            inicializarOyentes();

        } catch (IOException ex) {
            Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void inicializarTabla() throws IOException {
        tblClientes.getColumns().clear();
        limpiarCampos();

        tblClientes.setItems(obtenerDatos());
        tblClientes.autosize();
        tblClientes.setDisable(false);

        //idPersona
        colIdPersona = new TableColumn<>("idPersona");
        colIdPersona.setCellValueFactory(
                new PropertyValueFactory<>("idPersona"));

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

        //idCliente
        colIdCliente = new TableColumn<>("idCliente");
        colIdCliente.setCellValueFactory(
                new PropertyValueFactory<>("idCliente"));

        //idPersona
        colIdPersona = new TableColumn<>("idPersona");
        colIdPersona.setCellValueFactory(
                new PropertyValueFactory<>("idPersona"));

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

        //idUsuario
        colIdUsuario = new TableColumn<>("idUsuario");
        colIdUsuario.setCellValueFactory(new Callback<CellDataFeatures<Cliente, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<Cliente, Integer> param) {
                return new SimpleIntegerProperty(param.getValue().
                        getUsuario().getIdUsuario()).asObject();
            }
        });

        //Nombre Usuario
        colNombreUsuario = new TableColumn<>("Nombre Usuario");
        colNombreUsuario.setCellValueFactory(new Callback<CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
                return new SimpleStringProperty(param.getValue().getUsuario().getNombreUsuario());
            }

        });

//        //Contraseña
//        colContrasenia = new TableColumn<>("Contraseña");
//        colContrasenia.setCellValueFactory(new Callback<CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
//                return new SimpleStringProperty(param.getValue().getUsuario().getContrasenia());
//            }
//
//        });
        tblClientes.getColumns().addAll(
                colIdPersona, colNombre, colApellidoPaterno,
                colApellidoMaterno, colGenero, colDomicilio,
                coltelefono, colRfc,
                colIdCliente, colNumeroUnico, colCorreo, colEstatus,
                colIdUsuario, colNombreUsuario
        );
    }

    private void llenarComboBoxes() {
        cmbGenero.getItems().addAll("F", "M", "O");
    }

    private void inicializarOyentes() {

        tblClientes.setOnMouseClicked((MouseEvent x) -> {
            llenarCampos();
        });
        tblClientes.setOnKeyReleased(x -> {
            llenarCampos();
        });

        btnEditar.setOnAction(x -> {
            cambiarCampos(UNC_EDITAR, true);
            opcion = "PUT";
        });

        btnGuardar.setOnAction(x -> {
            try {
                cambiarCampos(UNC_DEFAULT, false);

                ejecutarPeticion(prepararDatos());

            } catch (IOException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnNuevo.setOnAction(x -> {
            cambiarCampos(UNC_NUEVO, true);
            limpiarCampos();
            opcion = "POST";
        });

        btnElminar.setOnAction(x -> {
            try {
                cambiarCampos(UNC_ELIMINAR, false);
                opcion = "DELETE";

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
                inicializarTabla();
                opcion = null;

            } catch (IOException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
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

    }

    private void cambiarCampos(String estilo, boolean editable) {
        txtNombre.setStyle(estilo);
        txtNombre.setEditable(editable);

        txtApellidoPaterno.setStyle(estilo);
        txtApellidoPaterno.setEditable(editable);

        txtApellidoMaterno.setStyle(estilo);
        txtApellidoMaterno.setEditable(editable);

//        cmbGenero.setStyle(estilo);
//        cmbGenero.setEditable(editable);
//
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
            c.setGenero(cmbGenero.getSelectionModel().getSelectedItem().toString());
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
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Consultando servidor... ");
            alerta.setContentText("Consultando datos del servidor...");

            try {
                alerta.show();

                if (api.manejarCliente(c, opcion) != null) {
                    windowMain.mostrarNotificacion("Agregado con"
                            + " éxito", "El usuario "
                            + u.getNombreUsuario() + " fue agregado "
                            + "con exito", NotificationType.SUCCESS);
                } else {
                    windowMain.mostrarNotificacion("Error",
                            "Ocurrio un problema al insertar al usuario",
                            NotificationType.ERROR);
                }

                opcion = null;
                inicializarTabla();

                alerta.hide();
            } catch (java.net.UnknownHostException uhe) {
                Alert a = new Alert(Alert.AlertType.ERROR);

                alerta.hide();

                a.setTitle("Sin servicio a internet");
                a.setContentText("No se pudo conectar con el servicio de divisas");
                a.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private ObservableList<Cliente> obtenerDatos() throws IOException {
        ObservableList<Cliente> clientes
                = FXCollections.observableArrayList();
        Platform.runLater(() -> {

            double precio = 0;
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Consultando servidor... ");
            alerta.setContentText("Consultando datos del servidor...");

            try {
                alerta.show();

                JsonArray jsonArray = api.consultarListado("cliente");
                if (jsonArray == null) {
                    return;
                }
                Cliente c;

                for (JsonElement jsonElement : jsonArray) {
                    c = gson.fromJson(jsonElement, Cliente.class
                    );
                    clientes.add(c);
                }

                alerta.hide();
            } catch (java.net.UnknownHostException uhe) {
                Alert a = new Alert(Alert.AlertType.ERROR);

                alerta.hide();

                a.setTitle("Sin servicio a internet");
                a.setContentText("No se pudo conectar con el servicio");
                a.showAndWait();
            } catch (ConnectException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);

                alerta.hide();

                a.setTitle("Servidor no disponible");
                a.setContentText("No se pudo conectar con el servicio");
                a.showAndWait();

            } catch (Exception ex) {
                Logger.getLogger(PaneClientesControlador.class
                        .getName()).log(Level.SEVERE, null, ex);
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

            cmbGenero.getSelectionModel().select(c.getGenero());

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

}
