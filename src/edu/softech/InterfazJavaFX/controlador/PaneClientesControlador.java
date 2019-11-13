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
import edu.softech.InterfazJavaFX.modelo.Cliente;
import edu.softech.InterfazJavaFX.modelo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Villegas
 */
public class PaneClientesControlador implements Initializable {

    @FXML
    private AnchorPane windowClientes;

    @FXML
    private AnchorPane apIzquierda;

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
    private TableColumn<Cliente, String> colContrasenia;

    @FXML
    private TableColumn<Cliente, String> colRol;

    @FXML
    private AnchorPane apDerecha;

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

    Api api = new Api();

    Gson gson = new Gson();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inicializarTabla();
            inicializarControladores();
            llenarComboBoxes();
            api.metodoPost();

        } catch (IOException ex) {
            Logger.getLogger(PaneClientesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void inicializarTabla() throws IOException {
        tblClientes.setItems(obtenerDatos());
        tblClientes.autosize();
        tblClientes.setDisable(false);

        //idPersona
        colIdPersona = new TableColumn<>("idPersona");
//        colIdPersona.setMinWidth(50);
        colIdPersona.setCellValueFactory(
                new PropertyValueFactory<>("idPersona"));

        //Nombre
        colNombre = new TableColumn<>("Nombre");
//        colNombre.setMinWidth(50);
        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));

        //ApellidoPaterno
        colApellidoPaterno = new TableColumn<>("Apellido Paterno");
//        colApellidoPaterno.setMinWidth(50);
        colApellidoPaterno.setCellValueFactory(
                new PropertyValueFactory<>("apellidoPaterno"));

        //ApellidoMaterno
        colApellidoMaterno = new TableColumn<>("Apellido Materno");
//        colApellidoMaterno.setMinWidth(50);
        colApellidoMaterno.setCellValueFactory(
                new PropertyValueFactory<>("apellidoMaterno"));

        //Genero
        colGenero = new TableColumn<>("Genero");
//        colGenero.setMinWidth(50);
        colGenero.setCellValueFactory(
                new PropertyValueFactory<>("genero"));

        //Domicilio
        colDomicilio = new TableColumn<>("Domicilio");
//        colDomicilio.setMinWidth(50);
        colDomicilio.setCellValueFactory(
                new PropertyValueFactory<>("domicilio"));

        //Telefono
        coltelefono = new TableColumn<>("Telefono");
//        coltelefono.setMinWidth(50);
        coltelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono"));

        //Rfc
        colRfc = new TableColumn<>("Rfc");
//        colRfc.setMinWidth(50);
        colRfc.setCellValueFactory(
                new PropertyValueFactory<>("rfc"));

        //idCliente
        colIdCliente = new TableColumn<>("idCliente");
//        colIdCliente.setMinWidth(50);
        colIdCliente.setCellValueFactory(
                new PropertyValueFactory<>("idCliente"));

        //idPersona
        colIdPersona = new TableColumn<>("idPersona");
//        colIdPersona.setMinWidth(50);
        colIdPersona.setCellValueFactory(
                new PropertyValueFactory<>("idPersona"));

        //Numero Unico de Cliente
        colNumeroUnico = new TableColumn<>("NUC");
//        colNumeroUnico.setMinWidth(50);
        colNumeroUnico.setCellValueFactory(
                new PropertyValueFactory<>("numeroUnico"));

        //Correo electronico
        colCorreo = new TableColumn<>("Correo Electronico");
//        colCorreo.setMinWidth(50);
        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo"));

        //Estatus
        colEstatus = new TableColumn<>("Estatus");
//        colEstatus.setMinWidth(50);
        colEstatus.setCellValueFactory(
                new PropertyValueFactory<>("estatus"));

        //idUsuario
        colIdUsuario = new TableColumn<>("idUsuario");
//        colIdUsuario.setMinWidth(50);
        colIdUsuario.setCellValueFactory(new Callback<CellDataFeatures<Cliente, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<Cliente, Integer> param) {
                return new SimpleIntegerProperty(param.getValue().
                        getUsuario().getIdUsuario()).asObject();
            }
        });

        //Nombre Usuario
        colNombreUsuario = new TableColumn<>("Nombre Usuario");
//        colNombreUsuario.setMinWidth(50);
        colNombreUsuario.setCellValueFactory(new Callback<CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
                return new SimpleStringProperty(param.getValue().getUsuario().getNombreUsuario());
            }

        });

//        //Contraseña
//        colContrasenia = new TableColumn<>("Contraseña");
////        colContrasenia.setMinWidth(50);
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

    private ObservableList<Cliente> obtenerDatos() throws IOException {
        ObservableList<Cliente> clientes
                = FXCollections.observableArrayList();

        JsonArray jsonArray = api.consultarListado("cliente");

        Cliente c;
        Queue cola = new LinkedList();

        for (JsonElement jsonElement : jsonArray) {
            c = gson.fromJson(jsonElement, Cliente.class);
            clientes.add(c);
        }

        return clientes;
    }

    private void inicializarControladores() throws IOException {

        tblClientes.setOnMouseClicked((MouseEvent x) -> {
            llenarCampos();
        });
        tblClientes.setOnKeyReleased(x -> {
            llenarCampos();
        });

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
    }

}
