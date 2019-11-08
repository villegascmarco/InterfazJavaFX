/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Villegas
 */
public class WindowMainController implements Initializable {

    @FXML
    private BorderPane contenedorPrincipal;
    @FXML
    private ImageView imgvLogo;
    @FXML
    private JFXButton btnReservaciones;
    @FXML
    private JFXButton btnServicios;
    @FXML
    private JFXButton btnEmpleados;
    @FXML
    private JFXButton btnClientes;
    @FXML
    private JFXButton btnProductos;
    @FXML
    private JFXButton btnTratamientos;
    @FXML
    private JFXButton btnSalas;
    @FXML
    private JFXButton btnSucursales;
    @FXML
    private JFXButton btnCtrlSala;

    @FXML
    private AnchorPane pane;

    private String resource;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void generarResource() {
        try {
            pane = FXMLLoader.load(getClass().getResource("/edu/softech/"
                    + "InterfazJavaFX/gui/fxml/pane/Pane" + resource + ".fxml"));

            contenedorPrincipal.setCenter(pane);
        } catch (IOException ex) {
            Logger.getLogger(WindowMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Los siguientes métodos están en los "onAction" de la sección code de cada
     * botón en SceneBuilder
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void cargarMenuReservaciones(ActionEvent event) throws IOException {
        resource = "Clientes";

        generarResource();
    }

    @FXML
    public void cargarMenuServicios(ActionEvent event) throws IOException {
        resource = "Clientes";

        generarResource();
    }

    @FXML
    public void cargarMenuEmpleados(ActionEvent event) throws IOException {
        resource = "Clientes";

        generarResource();
    }

    @FXML
    public void cargarMenuClientes(ActionEvent event) throws IOException {
        resource = "Clientes";

        generarResource();
    }

    @FXML
    public void cargarMenuProductos(ActionEvent event) throws IOException {
        resource = "Clientes";

        generarResource();
    }

    @FXML
    public void cargarMenuTratamientos(ActionEvent event) throws IOException {
        resource = "Clientes";

        generarResource();
    }

    @FXML
    public void cargarMenuSalas(ActionEvent event) throws IOException {
        resource = "Clientes";

        generarResource();
    }

    @FXML
    public void cargarMenuSucursales(ActionEvent event) throws IOException {
        resource = "Sucursales";

        generarResource();
    }

    @FXML
    public void cargarMenuControlSala(ActionEvent event) throws IOException {
        resource = "ControlSala";

        generarResource();
    }

}
