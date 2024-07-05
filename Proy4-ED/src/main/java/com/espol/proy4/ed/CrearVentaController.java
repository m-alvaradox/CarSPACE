/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proy4.ed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Objects.AtributoAdicional;
import Objects.EstadoD;
import Objects.Historial;
import Objects.User;
import Objects.Vehiculos;
import Objects.tipoHistorial;
import TDAS.ArrayList;
import TDAS.CircularDoublyList;
import TDAS.DoublyLinkedList;
import java.nio.file.StandardCopyOption;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CrearVentaController implements Initializable {
    
    @FXML
    private ComboBox<tipoHistorial> tipoServicio;
    @FXML
    private VBox paneHistorial;
    @FXML
    private HBox imagenesPane;
    @FXML
    private Button btonSeleccionarImagen;
    @FXML
    private VBox paneAtributos;
    @FXML
    private TextField marca;
    @FXML
    private TextField motor;
    @FXML
    private TextField modelo;
    @FXML
    private TextField kilometraje;
    @FXML
    private TextField year;
    @FXML
    private TextField peso;
    @FXML
    private TextField ubicacion;
    @FXML
    private TextField transmision;
    @FXML
    private TextField precio;
    
    
    /**
     * Initializes the controller class.
     * 
     */

    User usuario = App.userlogged;
    DoublyLinkedList<Vehiculos> L_Vehiculos = usuario.getMisVehiculos();
    ArrayList<File> rutaImagenes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rutaImagenes = new ArrayList<>();
        // TODO
    }    
    
    @FXML
    private void nuevoServicio() throws IOException{
        HBox hb = new HBox();
        ComboBox<tipoHistorial> tipo = new ComboBox();
        ImageView cerrar = new ImageView(new Image("/imagenes/cerrar.png"));
        cerrar.setFitWidth(16);
        cerrar.setFitHeight(16);
        cerrar.setOnMouseClicked((event)-> {
            paneHistorial.getChildren().remove(hb);
        });
        tipo.getItems().setAll(tipoHistorial.values());
        TextField textField = new TextField();
        textField.setPrefWidth(128);
        DatePicker fecha = new DatePicker();
        fecha.setPromptText("Date");
        fecha.getStyleClass().add("text-field");
        fecha.setPrefWidth(69);
        fecha.setPrefHeight(29);
        hb.setSpacing(7);
        hb.setAlignment(Pos.CENTER_LEFT);
        tipo.getStyleClass().add("comboBox_filtros");
        textField.getStyleClass().add("text-field");
        hb.getChildren().addAll(tipo,fecha, textField, cerrar);
        paneHistorial.getChildren().addAll(hb);
    }
    
    @FXML
    private void addAtributos() throws IOException{
        HBox hb= new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER_LEFT);
        TextField title= new TextField();
        title.setId("title");
        title.setPromptText("Título");
        TextField descripcion= new TextField();
        descripcion.setPromptText("Descripción");
        descripcion.setId("descripcion");
        ImageView cerrar = new ImageView(new Image("/imagenes/cerrar.png"));
        cerrar.setFitWidth(16);
        cerrar.setFitHeight(16);
        title.setPrefWidth(62);
        title.setPrefHeight(27);
        descripcion.setPrefWidth(115);
        descripcion.setPrefHeight(27);
        title.getStyleClass().add("text-field");
        descripcion.getStyleClass().add("text-field");
        cerrar.setOnMouseClicked((event)-> {
            paneAtributos.getChildren().remove(hb);
        });
        hb.getChildren().addAll(title, descripcion, cerrar);
        paneAtributos.getChildren().addAll(hb);
    }
    @FXML
    private void addImage() throws IOException{
        HBox hb= new HBox();    
        hb.setPadding(new javafx.geometry.Insets(0,5,0,5));
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER_LEFT);
        Label rutaImagen = new Label();
        ImageView imageLogo = new ImageView(new Image("/imagenes/imagen.png"));
        imageLogo.setFitWidth(14);
        imageLogo.setFitHeight(12);
        ImageView cerrar = new ImageView(new Image("/imagenes/cerrar.png"));
        hb.getChildren().addAll(imageLogo, rutaImagen, cerrar);
        hb.setMargin(cerrar, new javafx.geometry.Insets(-7, 3,0,3));
        hb.getStyleClass().add("caja_imagen");
        cerrar.setFitWidth(12);
        cerrar.setFitHeight(12);
        File imageSelected;
        FileChooser file = new FileChooser();
        file.setTitle("Seleccionar imagen");
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png","*.jpg", "*.jpeg"));
        Stage stage = (Stage) btonSeleccionarImagen.getScene().getWindow();
        imageSelected = file.showOpenDialog(stage);
        if(imageSelected!=null){
            try{
                Image image = new Image(new FileInputStream(imageSelected));
                String ruta= imageSelected.getName(); // ruta para guardar la imagen;
                rutaImagenes.addLast(imageSelected);
                rutaImagen.setText(imageSelected.getName());
                imagenesPane.getChildren().add(hb);
                cerrar.setOnMouseClicked((event)-> {
                    rutaImagenes.remove(imageSelected); 
                    imagenesPane.getChildren().remove(hb);
                });
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }    
        }
    }
    @FXML
    private void principal() throws IOException{
        App.setRoot("principal");
    }
    

    @FXML
    private void crearVehiculo() throws IOException{
        
        // Aquí se declaran las listas para imagenes, historial, y atributosAdicionales
        ArrayList<Historial> listaHistorial = new ArrayList<>();
        ArrayList<AtributoAdicional> listaAtributosAdicionales = new ArrayList<>();
        CircularDoublyList<String> listaImagenes = new CircularDoublyList<>();
        
        
        for(Node caja: paneHistorial.getChildren()){ // Recorre cada VBox para el historial de Servicios y Accidentes
            HBox fila= (HBox) caja; 
            ComboBox<tipoHistorial> tipo = new ComboBox(); // Almacena el tipo de Servicio 
            TextField cajaDescripcion = new TextField(); // Almacena su descripcion
            DatePicker cajaFecha = new DatePicker();
            for(Node elements: fila.getChildren()){ // recorre todos los elementos que hay dentro de un VBox
                if(elements instanceof ComboBox){  // 
                    tipo = (ComboBox) elements;
                }if (elements instanceof TextField){
                    cajaDescripcion = (TextField) elements;
                }if (elements instanceof DatePicker){
                    cajaFecha = (DatePicker) elements;
                }
            }
            tipoHistorial tipoSeleccionado = tipo.getSelectionModel().getSelectedItem();
            String descripcion = cajaDescripcion.getText();
            if(cajaFecha!=null && tipoSeleccionado!=null && descripcion!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fecha = cajaFecha.getValue().format(formatter);
                Historial h1 = new Historial(tipoSeleccionado, descripcion, fecha);
                listaHistorial.addLast(h1);
            } 
        }
        
        for( Node caja: paneAtributos.getChildren()){
            HBox fila = (HBox) caja;
            TextField cajaTitle = new TextField();
            TextField cajaDescripcion = new TextField();
            for (Node elements: fila.getChildren()){
                if(elements instanceof TextField && "title".equals(elements.getId())){
                    cajaTitle = (TextField) elements;
                } if(elements instanceof TextField && "descripcion".equals(elements.getId())){
                    cajaDescripcion = (TextField) elements;
                }
            }
            String title = cajaTitle.getText();
            String descripcion = cajaDescripcion.getText();
            if(title!=null && descripcion!=null){
                AtributoAdicional a1 = new AtributoAdicional(title, descripcion);
                listaAtributosAdicionales.addLast(a1);
            }
        }
        if(!rutaImagenes.isEmpty()){
            for(int i=0; i<rutaImagenes.size(); i++){
                File e = rutaImagenes.get(i);
                Path projectDir = Paths.get("").toAbsolutePath();
                String ruta = e.getName();
                Path rutaDestino =  projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", ruta));
                Files.copy(e.toPath(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
                listaImagenes.addLast(ruta);
            }
        }
    
    // Aquí se debe verificar que los campos estén llenos, que la lista de imagenes no esté vacía
        if(marca!=null && modelo!=null && year!=null && kilometraje!=null && motor!=null && ubicacion!=null && peso!=null && transmision!=null && precio!=null){
            String marca1 = marca.getText();
            String modelo1 = modelo.getText();
            int year1 = Integer.parseInt(year.getText());
            int kilometraje1 = Integer.parseInt(kilometraje.getText());
            String motor1 = motor.getText();
            String ubicacion1 = ubicacion.getText();
            double peso1 = Double.parseDouble(peso.getText());
            String transmision1 = transmision.getText();
            double precio1 = Double.parseDouble(precio.getText());
            
            if(!listaImagenes.isEmpty()){ // verifica que la lista de imagenes no esté vacía
                // Por predeterminado se pone el Vehiculo en venta
                Vehiculos v1 =  new Vehiculos(marca1, modelo1, year1, precio1, kilometraje1, motor1, transmision1, peso1, ubicacion1, EstadoD.Disponible, listaImagenes, listaHistorial, listaAtributosAdicionales);
                if(L_Vehiculos==null){
                    L_Vehiculos = new DoublyLinkedList<>();
                }
                L_Vehiculos.addLast(v1);
                App.userlogged.setMisVehiculos(L_Vehiculos);
                App.catalogo.agregarVehiculo(v1); // Se agrega a la lista de vehículos en venta
                App.ActualizarListaCars();
                App.ActualizarListaUsuarios();
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Creación de Venta");
                alert.setTitle("Creacion exitosa");
                alert.setContentText("Su vehiculo fue creado correctamente");
                String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
                alert.getDialogPane().getStylesheets().add(css);
                alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
                alert.showAndWait();
                App.setRoot("principal");; 
                
            }else{
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Creación de cuenta");
                alert.setTitle("Error de pedido");
                alert.setContentText("Debe agregar por lo menos una imagen");
                alert.showAndWait();
                String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
                alert.getDialogPane().getStylesheets().add(css);
                alert.getDialogPane().getStyleClass().add("dialog-paneError");
                alert.showAndWait();
            }   
        }else{
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Creación de Vehículo");
            alert.setTitle("Error de pedido");
            alert.setContentText("Debe completar todos los campos para crear la cuenta");
            alert.showAndWait();
            String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
            alert.getDialogPane().getStylesheets().add(css);
            alert.getDialogPane().getStyleClass().add("dialog-paneError");
            alert.showAndWait();
        }
    }


}
