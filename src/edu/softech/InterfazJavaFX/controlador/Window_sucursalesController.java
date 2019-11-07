/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author pollo
 */
public class Window_sucursalesController implements Initializable {

    @FXML
    private ImageView imgvSucursal;
    @FXML
    private ImageView imgSucursal;
    @FXML
    private JFXComboBox<?> cmbEstatus;
    @FXML
    private JFXComboBox<?> cmbEstatusMod;
    @FXML
    private ImageView imgvLocalizacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
