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
public class PaneEmpleadoControlador implements Initializable
{
    
    @FXML
    private AnchorPane windowEmpleado;

    @FXML
    private AnchorPane apIzquierda;

    @FXML
    private AnchorPane apDerecha;

    @FXML
    private ImageView imgFotografia;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtApellidoPaterno;

    @FXML
    private JFXTextField txtApellidoMaterno;

    @FXML
    private JFXComboBox<?> cmbGenero;

    @FXML
    private JFXButton btnAgregarGenero;

    @FXML
    private JFXTextField txtRfc;

    @FXML
    private JFXTextField txtDomicilio;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXTextField txtCorreoElectronico;

    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private JFXTextField txtContrasenia;

    @FXML
    private JFXComboBox<?> cmbPuesto;

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
    
    public AnchorPane getWindowEmpleado()
    {
        return windowEmpleado;
    }

    public AnchorPane getApIzquierda()
    {
        return apIzquierda;
    }

    public AnchorPane getApDerecha()
    {
        return apDerecha;
    }

    public ImageView getImgFotografia()
    {
        return imgFotografia;
    }

    public JFXTextField getTxtNombre()
    {
        return txtNombre;
    }

    public JFXTextField getTxtApellidoPaterno()
    {
        return txtApellidoPaterno;
    }

    public JFXTextField getTxtApellidoMaterno()
    {
        return txtApellidoMaterno;
    }

    public JFXComboBox<?> getCmbGenero()
    {
        return cmbGenero;
    }

    public JFXButton getBtnAgregarGenero()
    {
        return btnAgregarGenero;
    }

    public JFXTextField getTxtRfc()
    {
        return txtRfc;
    }

    public JFXTextField getTxtDomicilio()
    {
        return txtDomicilio;
    }

    public JFXTextField getTxtTelefono()
    {
        return txtTelefono;
    }

    public JFXTextField getTxtCorreoElectronico()
    {
        return txtCorreoElectronico;
    }

    public JFXTextField getTxtUsuario()
    {
        return txtUsuario;
    }

    public JFXTextField getTxtContrasenia()
    {
        return txtContrasenia;
    }

    public JFXComboBox<?> getCmbPuesto()
    {
        return cmbPuesto;
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
