package edu.softech.InterfazJavaFX.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class WindowMain extends Application {

    private static final Image IMG_LOGO = new Image(System.class.getResource("/res/lotus.png").toString());

    FXMLLoader fxmll;

    Stage window;

    Scene scene;

    TrayNotification tray;

    public WindowMain() {
        fxmll = new FXMLLoader(System.class.getResource("/edu/softech/"
                + "InterfazJavaFX/gui/fxml/WindowMain.fxml"));
        //  fxmll.setController(this); Quitamos esta linea para crear un controlador para cada seccion
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmll.load();
        scene = new Scene(fxmll.getRoot());
        window = primaryStage;
        window.setScene(scene);

        window.setMinHeight(768);
        window.setMinWidth(1100);
//        window.setMinWidth(1366);

        window.setTitle("MySpa");
        window.getIcons().add(IMG_LOGO);
        window.show();

    }

    public void mostrarNotificacion(String titulo, String mensaje, NotificationType tipo) {
        tray = new TrayNotification();

        tray.setTitle(titulo);
        tray.setMessage(mensaje);
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(tipo);
        tray.showAndDismiss(Duration.seconds(5));
    }

    public TrayNotification mostrarAlerts(String titulo, String mensaje, NotificationType tipo) {
        tray = new TrayNotification();

        tray.setTitle(titulo);
        tray.setMessage(mensaje);
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(tipo);
        return tray;
    }

}
