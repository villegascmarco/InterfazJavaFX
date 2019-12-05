/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import edu.softech.InterfazJavaFX.api.Api;
import edu.softech.InterfazJavaFX.gui.WindowMain;
import edu.softech.MySpa.modelo.Producto;
import edu.softech.MySpa.modelo.Producto_Sucursal;
import edu.softech.MySpa.modelo.Sucursal;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author logic
 */
public class PaneProductosControlador implements Initializable {

    @FXML
    private JFXCheckBox chbVerInactivos;

    @FXML
    private AnchorPane windowProductos;

    @FXML
    private TableView<Producto_Sucursal> tblProductos;

    @FXML
    private TableColumn<Producto_Sucursal, String> colNombre;

    @FXML
    private TableColumn<Producto_Sucursal, String> colMarca;

    @FXML
    private TableColumn<Producto_Sucursal, Integer> colEstatus;

    @FXML
    private TableColumn<Producto_Sucursal, Float> colPrecioUso;

    @FXML
    private TableColumn<Producto_Sucursal, Integer> colSucursal;

    @FXML
    private TableColumn<Producto_Sucursal, Integer> colStock;

    @FXML
    private JFXTextField txtBuscar;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtMarca;

    @FXML
    private JFXTextField txtPrecioUso;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private JFXTextField txtSucursal;

    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnElminar;

    @FXML
    private JFXButton btnCancelar;

    private Api api = new Api();

    private Gson gson = new Gson();

    private WindowMain windowMain = new WindowMain();

    private String opcion = null;

    private final String UNC_EDITAR = "-jfx-unfocus-color: f68a1f;";
    private final String UNC_DEFAULT = "-jfx-unfocus-color: #4d4d4d;";
    private final String UNC_NUEVO = "-jfx-unfocus-color: #00C851;";
    private final String UNC_ELIMINAR = "-jfx-unfocus-color: #ff4444;";
    
//    private static Method columnToFitMethod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inicializarTabla(true);
            inicializarOyentes();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void inicializarTabla(boolean productosActivos) throws IOException {
        tblProductos.getColumns().clear();
        limpiarCampos();
        tblProductos.setItems(obtenerDatos(productosActivos));
        inicializarColumnas();
    }

    private void inicializarColumnas() {
        tblProductos.autosize();
        tblProductos.setDisable(false);
        
//        try {
//            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
//
//        } catch (NoSuchMethodException | SecurityException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }

        // Columna Nombre
        colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory((CellDataFeatures<Producto_Sucursal, String> param)
                -> new SimpleStringProperty(param.getValue().getProducto().getNombre()));

        // Columna Marca
        colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory((CellDataFeatures<Producto_Sucursal, String> param)
                -> new SimpleStringProperty(param.getValue().getProducto().getMarca()));

        // Columna Precio Uso
        colPrecioUso = new TableColumn<>("Precio Uso");
        colPrecioUso.setCellValueFactory((CellDataFeatures<Producto_Sucursal, Float> param)
                -> new SimpleFloatProperty(param.getValue().getProducto().getPrecioUso()).asObject());

        // Columna Sucursal
        colSucursal = new TableColumn<>("Sucursal");
        colSucursal.setCellValueFactory((CellDataFeatures<Producto_Sucursal, Integer> param)
                -> new SimpleIntegerProperty(param.getValue().getSucursal().getIdSucursal()).asObject());

        // Columna Stock
        colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(
                new PropertyValueFactory<>("stock"));
        
        // Columna Estatus
        colEstatus = new TableColumn<>("Estatus");
        colEstatus.setCellValueFactory((CellDataFeatures<Producto_Sucursal, Integer> param)
                -> new SimpleIntegerProperty(param.getValue().getProducto().getEstatus()).asObject());

        tblProductos.getColumns().addAll(
                colNombre,
                colMarca,
                colPrecioUso,
                colSucursal,
                colStock,
                colEstatus);
        
//        tblProductos.getItems().addListener(new ListChangeListener<Producto_Sucursal>() {
//            @Override
//            public void onChanged(ListChangeListener.Change<? extends Producto_Sucursal> ps) {
//                for(Object column : tblProductos.getColumns()) {
//                    try {
//                        columnToFitMethod.invoke(tblProductos.getSkin(), column, -1);
//                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
//                        System.out.println(ex.getMessage());
//                        ex.printStackTrace();
//                    }
//                }
//            }
//         
//        });
    }

    private void inicializarOyentes() {

        tblProductos.setOnMouseClicked((MouseEvent x) -> {
            llenarCampos();
        });

        tblProductos.setOnKeyReleased(x -> {
            llenarCampos();
        });

        btnEditar.setOnAction(x -> {
            opcion = "PUT";
            cambiarCampos(UNC_EDITAR, true);
        });

        btnGuardar.setOnAction(x -> {
            try {

                if (opcion == null) {
                    return;
                }
                cambiarCampos(UNC_DEFAULT, false);

                ejecutarPeticion(prepararDatos());

            } catch (IOException | URISyntaxException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });

        btnNuevo.setOnAction(x -> {
            opcion = "POST";
            cambiarCampos(UNC_NUEVO, true);
            chbVerInactivos.setSelected(false);
            limpiarCampos();
        });

        btnCancelar.setOnAction(x -> {
            opcion = null;
            limpiarCampos();
            cambiarCampos(UNC_DEFAULT, false);
        });

        btnElminar.setOnAction(x -> {
            try {
                opcion = "DELETE";

                if (tblProductos.getSelectionModel().getSelectedItem() == null) {
                    opcion = null;
                    cambiarCampos(UNC_DEFAULT, false);
                    return;
                }

                cambiarCampos(UNC_ELIMINAR, false);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("¿Esta seguro de eliminar el producto " + txtNombre.getText() + "?");

                Optional<ButtonType> resultado = alert.showAndWait();
                if (resultado.get() == ButtonType.OK) {
                    api.manejarProducto(prepararDatos(), opcion);
                } else {
                    System.out.println("Cancelado");
                }
                cambiarCampos(UNC_DEFAULT, false);
                chbVerInactivos.setSelected(false);
                inicializarTabla(true);
                opcion = null;

            } catch (IOException | URISyntaxException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });

        chbVerInactivos.setOnAction(x -> {
            try {
                if (!chbVerInactivos.selectedProperty().get()) {
                    inicializarTabla(true);
                } else {
                    inicializarTabla(false);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });

    }

    private void limpiarCampos() {
        
//        tblProductos.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtMarca.clear();
        txtPrecioUso.clear();
        txtStock.clear();
        txtSucursal.clear();
        txtBuscar.clear();
        
    }

    private void cambiarCampos(String estilo, boolean editable) {
        if (opcion == null) {
            
        } else if (tblProductos.getSelectionModel().getSelectedItem() == null 
                && (opcion.equals("DELETE") || opcion.equals("PUT"))) {
            opcion = null;
            cambiarCampos(UNC_DEFAULT, false);
            return;
        }
        txtNombre.setStyle(estilo);
        txtNombre.setEditable(editable);

        txtMarca.setStyle(estilo);
        txtMarca.setEditable(editable);

        txtPrecioUso.setStyle(estilo);
        txtPrecioUso.setEditable(editable);
        
        

        txtStock.setStyle(estilo);
        txtStock.setEditable(editable);

        txtSucursal.setStyle(estilo);
        txtSucursal.setEditable(editable);
    }

    private Producto_Sucursal prepararDatos() throws IOException, URISyntaxException {
        Producto producto = new Producto();
        Sucursal sucursal = new Sucursal();
        Producto_Sucursal producto_sucursal = new Producto_Sucursal();

        if (tblProductos.getSelectionModel().getSelectedItem() != null) {
            producto_sucursal = tblProductos.getSelectionModel().getSelectedItem();
            producto = producto_sucursal.getProducto();
            sucursal = producto_sucursal.getSucursal();
        }

        if (opcion != null ) {
            producto.setNombre(txtNombre.getText());
            producto.setMarca(txtMarca.getText());
            producto.setPrecioUso(Float.parseFloat(txtPrecioUso.getText()));
            producto.setEstatus(producto.getEstatus());
            sucursal.setIdSucursal(Integer.parseInt(txtSucursal.getText()));
            int stock = Integer.parseInt(txtStock.getText());

            producto_sucursal = new Producto_Sucursal(sucursal, producto, stock);
        }

        return producto_sucursal;
    }

    private void ejecutarPeticion(Producto_Sucursal producto_sucursal) {
        Platform.runLater(() -> {
            Producto producto = producto_sucursal.getProducto();

            TrayNotification alerta = windowMain.mostrarAlerts("Consultando servidor... ",
                    "Consultando datos del servidor...", NotificationType.NOTICE);

            try {
                alerta.showAndWait();

                if (api.manejarProducto(producto_sucursal, opcion) != null) {
                    windowMain.mostrarNotificacion("Éxito", "El movimiento con el producto"
                            + producto.getNombre() + "fue todo un éxito",
                            NotificationType.SUCCESS);
                } else {
                    windowMain.mostrarNotificacion("Error",
                            "Ocurrio un problema al realizar el movimiento",
                            NotificationType.ERROR);
                }
                alerta.showAndDismiss(Duration.ZERO);

                opcion = null;
                inicializarTabla(true);

            } catch (java.net.UnknownHostException uhe) {
                alerta.showAndDismiss(Duration.ZERO);

                TrayNotification a = windowMain.mostrarAlerts("Sin conexión",
                        "Intentelo más tarde", NotificationType.ERROR);
                a.showAndDismiss(Duration.seconds(5));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } catch (NullPointerException e) {
                TrayNotification a = windowMain.mostrarAlerts("Error", "Debe insertar todo los campos", NotificationType.ERROR);
                a.showAndDismiss(Duration.seconds(5));
            }

        });
    }

    private ObservableList<Producto_Sucursal> obtenerDatos(boolean productoActivos) throws IOException {
        ObservableList<Producto_Sucursal> productos
                = FXCollections.observableArrayList();

        Platform.runLater(() -> {
            TrayNotification alerta = windowMain.mostrarAlerts("Consultando servidor... ",
                    "Espere un momento, por favor. ", NotificationType.NOTICE);

            try {
                alerta.showAndWait();

                JsonArray jsonArray = api.consultarListado("producto");
                if (jsonArray == null) {
                    return;
                }
                Producto_Sucursal producto_sucursal;

                for (JsonElement jsonElement : jsonArray) {
                    producto_sucursal = gson.fromJson(jsonElement, Producto_Sucursal.class);

                    if (productoActivos) {
                        if (producto_sucursal.getProducto().getEstatus() == 1) {
                            productos.add(producto_sucursal);
                        }
                    } else {
                        if (producto_sucursal.getProducto().getEstatus() == 0) {
                            productos.add(producto_sucursal);
                        }
                    }
                }
                filtrarDatos(productos);
                alerta.showAndDismiss(Duration.ZERO);
            } catch (java.net.UnknownHostException | ConnectException ce) {
                alerta.showAndDismiss(Duration.ZERO);
                windowMain.mostrarNotificacion("Sin servicio",
                        "No se pudo conectar con el servicio", NotificationType.ERROR);
                ce.printStackTrace();
            } catch (Exception ex) {
                windowMain.mostrarNotificacion("Error"
                        , "Cheque la consola para más información", NotificationType.ERROR);
                ex.printStackTrace();
            }

        });
        return productos;
    }

    private void llenarCampos() {
        if (tblProductos.getSelectionModel().getSelectedItem() != null) {
            Producto_Sucursal producto_sucursal = tblProductos.getSelectionModel().getSelectedItem();
            Producto producto = producto_sucursal.getProducto();
            int sucursal = producto_sucursal.getSucursal().getIdSucursal();
            int stock = producto_sucursal.getStock();

            txtNombre.setText(producto.getNombre());
            txtMarca.setText(producto.getMarca());

            txtPrecioUso.setText(Float.toString(producto.getPrecioUso()));
            txtStock.setText(Integer.toString(stock));
            txtSucursal.setText(Integer.toString(sucursal));

        }
        cambiarCampos(UNC_DEFAULT, false);
        opcion = null;
    }

    private void filtrarDatos(ObservableList<Producto_Sucursal> productos) {

        FilteredList<Producto_Sucursal> filteredData
                = new FilteredList<>(productos, b -> true);

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(producto_sucursal -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String busqueda = newValue.toLowerCase();

                if (producto_sucursal.getProducto().getNombre().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (producto_sucursal.getProducto().getMarca().toLowerCase().contains(busqueda)) {
                    return true;
                } else if (Float.toString(producto_sucursal.getProducto().getPrecioUso()).contains(busqueda)) {
                    return true;
                } else if (Integer.toString(producto_sucursal.getSucursal().getIdSucursal()).contains(busqueda)) {
                    return true;
                } else if (Integer.toString(producto_sucursal.getStock()).contains(busqueda)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Producto_Sucursal> sortedList = new SortedList<>(filteredData);
        tblProductos.getColumns().clear();
        limpiarCampos();
        sortedList.comparatorProperty().bind(tblProductos.comparatorProperty());
        tblProductos.setItems(sortedList);
        inicializarColumnas();
    }

}
