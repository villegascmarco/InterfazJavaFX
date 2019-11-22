/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.notificacion;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Villegas
 */
public class Notificacion {

    private Notifications notificacion;

    public void agregado(String objeto, String titulo, String texto) {
        notificacion = Notifications.create()
                .title(titulo)
                .text(texto)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_LEFT);
        notificacion.show();

    }
}
