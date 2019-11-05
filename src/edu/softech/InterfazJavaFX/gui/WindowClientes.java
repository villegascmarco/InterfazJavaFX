/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Villegas
 */
public class WindowClientes extends Application{
    FXMLLoader fxmll;

    Stage window;

    Scene scene;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmll.load();
        scene = new Scene(fxmll.getRoot());
        window = primaryStage;
        window.setScene(scene);

        window.setTitle("MySpa2");
        
        window.show();

    }
}
