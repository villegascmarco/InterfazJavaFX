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
public class Window_clientesController implements Initializable {

    /**
     * Los controladores de esta seccion se generan automaticamente al dar doble
     * click sobre los FXML que no tiene controlador. Los mismo se
     * refactorizaron en esta carpeta y se modifico el fxml vista que los
     * referencia.
     */
    @FXML
    private AnchorPane windowClientes;

    @FXML
    private AnchorPane apDerecha;

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
    private AnchorPane apIzquierda;

    @FXML
    private TableView<?> tbClientes;

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

    @FXML
    private void mostrarMensaje(ActionEvent event) {
        System.out.println("Hola");
    }

}
