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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Esau
 */
public class PaneSalaControlador implements Initializable
{    
    
    @FXML
    private AnchorPane apDerecha;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtDescripcion;

    @FXML
    private ImageView imgFotografia;

    @FXML
    private JFXComboBox<?> cmbEstatus;

    @FXML
    private JFXComboBox<?> cmbSucursal;

    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnElminar;
    
    @Override
    public void initialize(URL location,
                           ResourceBundle resources)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public AnchorPane getApDerecha()
    {
        return apDerecha;
    }

    public JFXTextField getTxtNombre()
    {
        return txtNombre;
    }

    public JFXTextField getTxtDescripcion()
    {
        return txtDescripcion;
    }

    public ImageView getImgFotografia()
    {
        return imgFotografia;
    }

    public JFXComboBox<?> getCmbEstatus()
    {
        return cmbEstatus;
    }

    public JFXComboBox<?> getCmbSucursal()
    {
        return cmbSucursal;
    }

    public JFXButton getBtnNuevo()
    {
        return btnNuevo;
    }

    public JFXButton getBtnGuardar()
    {
        return btnGuardar;
    }

    public JFXButton getBtnEditar()
    {
        return btnEditar;
    }

    public JFXButton getBtnElminar()
    {
        return btnElminar;
    }
    
}
