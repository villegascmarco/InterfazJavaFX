/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Villegas
 */
public class PaneClientesControlador implements Initializable {


    @FXML
    private AnchorPane apDerecha;


    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnElminar;

    @FXML
    private AnchorPane windowControlSala;

    @FXML
    private JFXTextField txtSucursal;
    @FXML
    private JFXTextField txtSala;
    @FXML
    private JFXTextField txtTemperatura;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        ScrollPane sp = new ScrollPane();
//        sp.setContent(tbClientes);
//        sp.setPrefSize(681, 689);
//        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private void mostrarMensaje(ActionEvent event) {
        System.out.println("Hola");
    }

}
