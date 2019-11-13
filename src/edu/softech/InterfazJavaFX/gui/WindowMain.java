package edu.softech.InterfazJavaFX.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WindowMain extends Application {

    private static final Image IMG_LOGO = new Image(System.class.getResource("/res/lotus.png").toString());

    FXMLLoader fxmll;

    Stage window;

    Scene scene;

    public WindowMain() {
        fxmll = new FXMLLoader(System.class.getResource("/edu/softech/"
                + "InterfazJavaFX/gui/fxml/window_main.fxml"));
        //  fxmll.setController(this); Quitamos esta linea para crear un controlador para cada seccion
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmll.load();
        scene = new Scene(fxmll.getRoot());
        window = primaryStage;
        window.setScene(scene);

        window.setMinHeight(768);
        window.setMinWidth(1366);

        window.setTitle("MySpa");
        window.getIcons().add(IMG_LOGO);
        window.show();

    }

}
