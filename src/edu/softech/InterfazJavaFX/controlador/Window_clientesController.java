/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void mostrarMensaje(ActionEvent event) {
        System.out.println("Hola");
    }

}
