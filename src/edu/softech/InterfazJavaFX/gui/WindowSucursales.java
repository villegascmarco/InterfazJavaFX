/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author pollo
 */
public class WindowSucursales extends Application {

    private static final Image IMG_SUCURSAL = new Image(System.class.getResource("/res/yoga.png").toString());
    private static final Image IMG_LOCALIZACION = new Image(System.class.getResource("/res/localizacion.png").toString());

    FXMLLoader fxmll;
    Stage window;
    Scene scene;

    @FXML
    ImageView imgvSucursal;
    @FXML
    ImageView imgSucursal;
    @FXML
    ImageView imgvLocalizacion;
   

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmll.load();
        scene = new Scene(fxmll.getRoot());
        window = primaryStage;
        window.setScene(scene);
        window.setTitle("MySpa");
        window.setMaximized(true);
        window.setResizable(true);
        
        window.show();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds);
        
        imgvSucursal.setImage(IMG_SUCURSAL);
        imgSucursal.setImage(IMG_SUCURSAL);
        imgvLocalizacion.setImage(IMG_LOCALIZACION);

    }

}
