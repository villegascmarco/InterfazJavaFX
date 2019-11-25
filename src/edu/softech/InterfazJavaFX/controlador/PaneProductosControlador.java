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

    public AnchorPane getWindowProductos() {
        return windowProductos;
    }

    public void setWindowProductos(AnchorPane windowProductos) {
        this.windowProductos = windowProductos;
    }

    public TableView<?> getTbProductos() {
        return tbProductos;
    }

    public void setTbProductos(TableView<?> tbProductos) {
        this.tbProductos = tbProductos;
    }

    public TableColumn<?, ?> getColIdProducto() {
        return colIdProducto;
    }

    public void setColIdProducto(TableColumn<?, ?> colIdProducto) {
        this.colIdProducto = colIdProducto;
    }

    public TableColumn<?, ?> getColNombre() {
        return colNombre;
    }

    public void setColNombre(TableColumn<?, ?> colNombre) {
        this.colNombre = colNombre;
    }

    public TableColumn<?, ?> getColMarca() {
        return colMarca;
    }

    public void setColMarca(TableColumn<?, ?> colMarca) {
        this.colMarca = colMarca;
    }

    public TableColumn<?, ?> getColEstatus() {
        return colEstatus;
    }

    public void setColEstatus(TableColumn<?, ?> colEstatus) {
        this.colEstatus = colEstatus;
    }

    public TableColumn<?, ?> getColPrecioUso() {
        return colPrecioUso;
    }

    public void setColPrecioUso(TableColumn<?, ?> colPrecioUso) {
        this.colPrecioUso = colPrecioUso;
    }

    public TableColumn<?, ?> getColSucursal() {
        return colSucursal;
    }

    public void setColSucursal(TableColumn<?, ?> colSucursal) {
        this.colSucursal = colSucursal;
    }

    public JFXTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JFXTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JFXTextField getTxtPrecioUso() {
        return txtPrecioUso;
    }

    public void setTxtPrecioUso(JFXTextField txtPrecioUso) {
        this.txtPrecioUso = txtPrecioUso;
    }

    public JFXTextField getTxtCantidad() {
        return txtCantidad;
    }

    public void setTxtCantidad(JFXTextField txtCantidad) {
        this.txtCantidad = txtCantidad;
    }

    public JFXTextField getTxtSucursal() {
        return txtSucursal;
    }

    public void setTxtSucursal(JFXTextField txtSucursal) {
        this.txtSucursal = txtSucursal;
    }

    public JFXComboBox<?> getCmbEstatus() {
        return cmbEstatus;
    }

    public void setCmbEstatus(JFXComboBox<?> cmbEstatus) {
        this.cmbEstatus = cmbEstatus;
    }

    public JFXButton getBtnNuevo() {
        return btnNuevo;
    }

    public void setBtnNuevo(JFXButton btnNuevo) {
        this.btnNuevo = btnNuevo;
    }

    public JFXButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JFXButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JFXButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(JFXButton btnEditar) {
        this.btnEditar = btnEditar;
    }

    public JFXButton getBtnElminar() {
        return btnElminar;
    }

    public void setBtnElminar(JFXButton btnElminar) {
        this.btnElminar = btnElminar;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
