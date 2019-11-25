/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author logic
 */
public class PaneProductosControlador implements Initializable {

    @FXML
    private AnchorPane windowProductos;
    @FXML
    private TableView<?> tbProductos;
    @FXML
    private TableColumn<?, ?> colIdProducto;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colMarca;
    @FXML
    private TableColumn<?, ?> colEstatus;
    @FXML
    private TableColumn<?, ?> colPrecioUso;
    @FXML
    private TableColumn<?, ?> colSucursal;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtPrecioUso;
    @FXML
    private JFXTextField txtCantidad;
    @FXML
    private JFXTextField txtSucursal;
    @FXML
    private JFXComboBox<?> cmbEstatus;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnElminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
