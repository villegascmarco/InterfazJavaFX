/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author pollo
 */
public class WindowSucursales extends Application {

    private static final Image IMG_LOGO = new Image(System.class.getResource("/res/lotus.png").toString());
    private static final Image IMG_SUCURSAL = new Image(System.class.getResource("/res/yoga.png").toString());
    private static final Image IMG_LOCALIZACION = new Image(System.class.getResource("/res/localizacion.png").toString());

    FXMLLoader fxmll;
    Stage window;
    Scene scene;
    @FXML
    ImageView imgvLogo;
    @FXML
    Button btnSucursales;
    @FXML
    Button btnEmpleados;
    @FXML
    Button btnClientes;
    @FXML
    Button btnProductos;
    @FXML
    Button btnServicios;
    @FXML
    Button btnReservaciones;
    @FXML
    Button btnSalas;
    @FXML
    Button btnTratamientos;
    @FXML
    Button btnCtrlSala;
    @FXML
    ImageView imgvSucursal;
    @FXML
    ImageView imgSucursal;
    @FXML
    ImageView imgvLocalizacion;
    @FXML
    ComboBox cmbEstatus;
    @FXML
    ComboBox cmbEstatusMod;

    public WindowSucursales() {
        fxmll = new FXMLLoader(System.class.getResource("/edu/dsm401/examen/gui/fxml/window_sucursales.fxml"));
        fxmll.setController(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmll.load();
        scene = new Scene(fxmll.getRoot());
        window = primaryStage;
        window.setScene(scene);
        window.setTitle("MySpa");
        window.getIcons().add(IMG_LOGO);
        window.show();


        imgvSucursal.setImage(IMG_SUCURSAL);
        imgSucursal.setImage(IMG_SUCURSAL);
        imgvLocalizacion.setImage(IMG_LOCALIZACION);
        llenarComboBox();
    }

    private void llenarComboBox() {
        cmbEstatus.getItems().addAll("Activa", "Inactiva");
        cmbEstatusMod.getItems().addAll("Activa", "Inactiva");

    }
}
