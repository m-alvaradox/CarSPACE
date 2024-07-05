/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proy4.ed;

import Objects.*;
import TDAS.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MisVehiculosController implements Initializable {
    
    @FXML
    private BorderPane sinVehiculo; // muestra esta escena si no hay vehiculos
    @FXML
    private VBox paneHistorial;
    @FXML
    private VBox paneAtributos; 
    @FXML
    private Label marca; 
    @FXML
    private Label modelo;
    @FXML
    private Label motor;
    @FXML
    private Label ubicacion;
    @FXML
    private Label precio;
    @FXML
    private Label kilometraje;
    @FXML
    private Label year;
    @FXML
    private Label peso;
    @FXML
    private Label transmision;
    @FXML
    private ImageView imagen;
    @FXML
    private BorderPane vehiculoEditar;
    @FXML
    private BorderPane vehiculoMostrado;
    
    // Todos estos campos son para editar el vehículo
    @FXML
    private VBox paneHistorial1;
    @FXML
    private VBox paneAtributos1; 
    @FXML
    private TextField marca1; 
    @FXML
    private TextField modelo1;
    @FXML
    private TextField motor1;
    @FXML
    private TextField ubicacion1;
    @FXML
    private TextField precio1;
    @FXML
    private TextField kilometraje1;
    @FXML
    private TextField year1;
    @FXML
    private TextField peso1;
    @FXML
    private TextField transmision1;
    @FXML
    private ImageView imagen1;
    @FXML
    private ComboBox<EstadoD> estadoVehiculo;
    
    User usuario;
    private DoublyLinkedList<Vehiculos> listaVehiculo;// Aquí se inicia el DoublyCircularLinkedList
    private CircularDoublyList<String> imagenes; // Imagenes que usa el vehiculo
    private DoublyNodeList<String> rutaImagen; // Nodo imagen 
    private DoublyNodeList<Vehiculos> vehiculoUsar; // Aquí se almacena el vehiculo que usa en pantalla
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estadoVehiculo.getItems().addAll(EstadoD.values());
        usuario = App.userlogged;
        
        listaVehiculo = usuario.getMisVehiculos(); 
        if(listaVehiculo!=null){
            vehiculoUsar = listaVehiculo.getHeader(); 
            Vehiculos vehiculo = vehiculoUsar.getContent();
            estadoVehiculo.getSelectionModel().select(vehiculo.getEstado());
            marca.setText(vehiculo.getMarca());
            modelo.setText(vehiculo.getModelo());
            motor.setText(vehiculo.getMotor());
            ubicacion.setText(vehiculo.getUbicacion());
            kilometraje.setText(vehiculo.getKilometraje()+"");
            precio.setText(vehiculo.getPrecio()+"");
            year.setText(vehiculo.getAnio()+"");
            peso.setText(vehiculo.getPeso()+"");
            transmision.setText(vehiculo.getTransmision());

            // Aquí se debe mostrar todos los datos

            ArrayList<AtributoAdicional> listaAtributos = vehiculo.getAtributoAdicional();
            ArrayList<Historial> listaHistorial = vehiculo.gethistorial();

            imagenes= vehiculo.getFotos();// Doubly linked list para mostrar imagenes
            rutaImagen = imagenes.getHeader();

            Path projectDir = Paths.get("").toAbsolutePath();
            Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
            File archivoImagen = rutaAbsoluta.toFile();
            if (!archivoImagen.exists()) {
                System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
                return;
            }
            // Carga la nueva imagen
            Image image1 = new Image(archivoImagen.toURI().toString());
            imagen.setImage(image1);
            for(int i=0; i<listaAtributos.size(); i++){        // Aquí se llenan los Atributos adicionales
                AtributoAdicional a= listaAtributos.get(i);
                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setSpacing(5);
                ImageView image = new ImageView(new Image("/imagenes/punto.png"));
                image.setFitWidth(11);
                image.setFitHeight(8);
                Label title = new Label();
                title.setText(a.getTitle());
                Label descripcion = new Label();
                descripcion.setText(a.getDescripcion());
                title.getStyleClass().add("text-atributos");
                descripcion.getStyleClass().add("texto_login");
                hb.getChildren().addAll(image, title, descripcion);
                paneAtributos.getChildren().add(hb);
            }

            for(int i=0; i<listaHistorial.size(); i++){
                Historial h = listaHistorial.get(i);
                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setSpacing(5);
                ImageView image = new ImageView(new Image("/imagenes/punto.png"));
                image.setFitWidth(11);
                image.setFitHeight(8);
                Label tipo = new Label();
                tipo.setText(h.getTipo().toString());
                Label fecha = new Label();
                fecha.setText(h.getFecha());
                Label descripcion = new Label();
                descripcion.setText(h.getDescripcion());
                tipo.getStyleClass().add("text-atributos");
                descripcion.getStyleClass().add("texto_login");
                fecha.getStyleClass().add("texto_login");
                hb.getChildren().addAll(image,tipo, fecha, descripcion);
                paneHistorial.getChildren().add(hb);
            }
        } else {
            vehiculoMostrado.setVisible(false);
            sinVehiculo.setVisible(true);
        }     
    }  
    
    @FXML
    private void principal() throws IOException{
        App.setRoot("principal");
    }
   
   @FXML
   private void siguienteVehiculo() throws IOException{
       
        if(vehiculoUsar.getNext()!=null){
            paneAtributos.getChildren().clear();
            paneHistorial.getChildren().clear();
            vehiculoUsar = vehiculoUsar.getNext();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            estadoVehiculo.getSelectionModel().select(vehiculo.getEstado());
            marca.setText(vehiculo.getMarca());
            modelo.setText(vehiculo.getModelo());
            motor.setText(vehiculo.getMotor());
            ubicacion.setText(vehiculo.getUbicacion());
            kilometraje.setText(vehiculo.getKilometraje()+"");
            precio.setText(vehiculo.getPrecio()+"");
            year.setText(vehiculo.getAnio()+"");
            peso.setText(vehiculo.getPeso()+"");
            transmision.setText(vehiculo.getTransmision());

            // Aquí se debe mostrar todos los datos

            ArrayList<AtributoAdicional> listaAtributos = vehiculo.getAtributoAdicional();
            ArrayList<Historial> listaHistorial = vehiculo.gethistorial();

            imagenes= vehiculo.getFotos();// Doubly linked list para mostrar imagenes
            rutaImagen = imagenes.getHeader();
            Path projectDir = Paths.get("").toAbsolutePath();
            Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
            File archivoImagen = rutaAbsoluta.toFile();
            if (!archivoImagen.exists()) {
                System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
                return;
            }

            // Carga la nueva imagen
            Image image1 = new Image(archivoImagen.toURI().toString());
            imagen.setImage(image1);
            for(int i=0; i<listaAtributos.size(); i++){        // Aquí se llenan los Atributos adicionales
                AtributoAdicional a= listaAtributos.get(i);
                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setSpacing(5);
                ImageView image = new ImageView(new Image("/imagenes/punto.png"));
                image.setFitWidth(11);
                image.setFitHeight(8);
                Label title = new Label();
                title.setText(a.getTitle());
                Label descripcion = new Label();
                descripcion.setText(a.getDescripcion());
                title.getStyleClass().add("text-atributos");
                descripcion.getStyleClass().add("texto_login");
                hb.getChildren().addAll(image, title, descripcion);
                paneAtributos.getChildren().add(hb);
            }

            for(int i=0; i<listaHistorial.size(); i++){
                Historial h = listaHistorial.get(i);
                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setSpacing(5);
                ImageView image = new ImageView(new Image("/imagenes/punto.png"));
                image.setFitWidth(11);
                image.setFitHeight(8);
                Label tipo = new Label();
                tipo.setText(h.getTipo().toString());
                Label fecha = new Label();
                fecha.setText(h.getFecha());
                Label descripcion = new Label();
                descripcion.setText(h.getDescripcion());
                tipo.getStyleClass().add("text-atributos");
                descripcion.getStyleClass().add("texto_login");
                fecha.getStyleClass().add("texto_login");
                hb.getChildren().addAll(image,tipo, fecha, descripcion);
                paneHistorial.getChildren().add(hb);
            }
        } else {
                
            //Mostrar alerta que ya no existen Vehiculos;
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Siguiente Vehiculo");
            alert.setTitle("No existen más datos");
            alert.setContentText("Ya no existen más vehículos agregados");
            String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
            alert.getDialogPane().getStylesheets().add(css);
            alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
            alert.showAndWait();
       }
        
    }
   
   @FXML
   private void atrasVehiculo() throws IOException {
       
       if(vehiculoUsar.getPrevious()!=null){
            paneAtributos.getChildren().clear();
            paneHistorial.getChildren().clear();
            vehiculoUsar = vehiculoUsar.getPrevious();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            estadoVehiculo.getSelectionModel().select(vehiculo.getEstado());
            marca.setText(vehiculo.getMarca());
            modelo.setText(vehiculo.getModelo());
            motor.setText(vehiculo.getMotor());
            ubicacion.setText(vehiculo.getUbicacion());
            kilometraje.setText(vehiculo.getKilometraje()+"");
            precio.setText(vehiculo.getPrecio()+"");
            year.setText(vehiculo.getAnio()+"");
            peso.setText(vehiculo.getPeso()+"");
            transmision.setText(vehiculo.getTransmision());

            // Aquí se debe mostrar todos los datos

            ArrayList<AtributoAdicional> listaAtributos = vehiculo.getAtributoAdicional();
            ArrayList<Historial> listaHistorial = vehiculo.gethistorial();

            imagenes= vehiculo.getFotos();// Doubly linked list para mostrar imagenes
            rutaImagen = imagenes.getHeader();
            Path projectDir = Paths.get("").toAbsolutePath();
            Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
            //imagen.setImage(new Image(getClass().getResourceAsStream("/imagenesCarros/" + rutaImagen.getContent())));
            File archivoImagen = rutaAbsoluta.toFile();
            if (!archivoImagen.exists()) {
                System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
                return;
            }

            // Carga la nueva imagen
            Image image1 = new Image(archivoImagen.toURI().toString());
            imagen.setImage(image1);
            for(int i=0; i<listaAtributos.size(); i++){        // Aquí se llenan los Atributos adicionales
                AtributoAdicional a= listaAtributos.get(i);
                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setSpacing(5);
                ImageView image = new ImageView(new Image("/imagenes/punto.png"));
                image.setFitWidth(11);
                image.setFitHeight(8);
                Label title = new Label();
                title.setText(a.getTitle());
                Label descripcion = new Label();
                descripcion.setText(a.getDescripcion());
                title.getStyleClass().add("text-atributos");
                descripcion.getStyleClass().add("texto_login");
                hb.getChildren().addAll(image, title, descripcion);
                paneAtributos.getChildren().add(hb);
            }

            for(int i=0; i<listaHistorial.size(); i++){
                Historial h = listaHistorial.get(i);
                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setSpacing(5);
                ImageView image = new ImageView(new Image("/imagenes/punto.png"));
                image.setFitWidth(11);
                image.setFitHeight(8);
                Label tipo = new Label();
                tipo.setText(h.getTipo().toString());
                Label fecha = new Label();
                fecha.setText(h.getFecha());
                Label descripcion = new Label();
                descripcion.setText(h.getDescripcion());
                tipo.getStyleClass().add("text-atributos");
                descripcion.getStyleClass().add("texto_login");
                fecha.getStyleClass().add("texto_login");
                hb.getChildren().addAll(image,tipo, fecha, descripcion);
                paneHistorial.getChildren().add(hb);
            }
       } else {
           
           // Mostrar alerta que ya no existen más vehiculos a mostrar;
           Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Siguiente Vehiculo");
            alert.setTitle("No existen más datos");
            alert.setContentText("Este es el primer vehículo.");
            String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
            alert.getDialogPane().getStylesheets().add(css);
            alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
            alert.showAndWait();
       }
       
   }
   
   @FXML
   private void siguienteImagen() throws IOException{ 
       rutaImagen = rutaImagen.getNext();
       Path projectDir = Paths.get("").toAbsolutePath();
       Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
       File archivoImagen = rutaAbsoluta.toFile();
       if (!archivoImagen.exists()) {
            System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
            return;
        }

        // Carga la nueva imagen
        Image image1 = new Image(archivoImagen.toURI().toString());
        imagen.setImage(image1);
   }
   
   @FXML
   private void atrasImagen() throws IOException{
       rutaImagen = rutaImagen.getPrevious();
       Path projectDir = Paths.get("").toAbsolutePath();
       Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
       File archivoImagen = rutaAbsoluta.toFile();
        if (!archivoImagen.exists()) {
            System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
            return;
        }
            // Carga la nueva imagen
            Image image1 = new Image(archivoImagen.toURI().toString());
            imagen.setImage(image1);
   }
   
   @FXML
   private void editarVehiculo() throws IOException{
        vehiculoMostrado.setVisible(false);
        vehiculoEditar.setVisible(true);
        paneAtributos.getChildren().clear();
        paneHistorial.getChildren().clear();
        Vehiculos vehiculo = vehiculoUsar.getContent();
        marca1.setText(vehiculo.getMarca());
        modelo1.setText(vehiculo.getModelo());
        motor1.setText(vehiculo.getMotor());
        ubicacion1.setText(vehiculo.getUbicacion());
        kilometraje1.setText(vehiculo.getKilometraje()+"");
        precio1.setText(vehiculo.getPrecio()+"");
        year1.setText(vehiculo.getAnio()+"");
        peso1.setText(vehiculo.getPeso()+"");
        transmision1.setText(vehiculo.getTransmision());
        
        // Aquí se debe mostrar todos los datos
        
        ArrayList<AtributoAdicional> listaAtributos = vehiculo.getAtributoAdicional();
        ArrayList<Historial> listaHistorial = vehiculo.gethistorial();
        Path projectDir = Paths.get("").toAbsolutePath();
        Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
        File archivoImagen = rutaAbsoluta.toFile();
        if (!archivoImagen.exists()) {
            System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
            return;
        }
        // Carga la nueva imagen
        Image image1 = new Image(archivoImagen.toURI().toString());
        imagen1.setImage(image1);
        for(int i=0; i<listaAtributos.size(); i++){        // Aquí se llenan los Atributos adicionales
            AtributoAdicional a= listaAtributos.get(i);
            HBox hb = new HBox();
            hb.setAlignment(Pos.CENTER_LEFT);
            hb.setSpacing(5);
            ImageView image = new ImageView(new Image("/imagenes/cerrar.png"));
            image.setFitWidth(17);
            image.setFitHeight(14);
            TextField title = new TextField();
            title.setText(a.getTitle());
            title.setPrefHeight(21);
            title.setPrefWidth(76);
            TextField descripcion = new TextField();
            descripcion.setText(a.getDescripcion());
            descripcion.setPrefWidth(105);
            descripcion.setPrefHeight(21);
            title.getStyleClass().add("text-field");
            descripcion.getStyleClass().add("text-field");
            hb.getChildren().addAll( title, descripcion, image);
            paneAtributos.getChildren().add(hb);
        }
        
        for(int i=0; i<listaHistorial.size(); i++){ // Aquí ses llena todo el historial
            Historial h = listaHistorial.get(i);
            HBox hb = new HBox();
            hb.setAlignment(Pos.CENTER_LEFT);
            hb.setSpacing(5);
            ImageView image = new ImageView(new Image("/imagenes/cerrar.png"));
            image.setFitWidth(17);
            image.setFitHeight(14);
            ComboBox<tipoHistorial> tipo = new ComboBox<>();
            tipo.getItems().addAll(tipoHistorial.values());
            tipo.getSelectionModel().select(h.getTipo());
            DatePicker fecha = new DatePicker();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(h.getFecha(), formatter);
            fecha.setValue(localDate);
            fecha.setPrefSize(97, 27);
            TextField descripcion = new TextField();
            descripcion.setText(h.getDescripcion());
            descripcion.setPrefSize(112, 27);
            tipo.getStyleClass().add("text-field");
            descripcion.getStyleClass().add("text-field");
            fecha.getStyleClass().add("text-field");
            hb.getChildren().addAll(tipo, fecha, descripcion, image);
            paneHistorial.getChildren().add(hb);
        }
       

        // Se debe mostrar los datos del vehiculo en textd field para que los pueda actualizar;
       
   }
   
   @FXML
   private void eliminarImagen() throws IOException {
       DoublyNodeList<String> nueva= rutaImagen.getPrevious();  // va a regresar al anterior;
       imagenes.removeNode(rutaImagen); // Eliminar el nodo de la imagen;
       rutaImagen = nueva; // Empezar en el anterior;
       Path projectDir = Paths.get("").toAbsolutePath();
       Path rutaDestino =  projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
       try {
            Files.delete(rutaDestino);
       } catch (IOException ex) {
             ex.printStackTrace();
       }
       siguienteImagenEditar();
   }
   
   @FXML
   private void siguienteImagenEditar() throws IOException {
       rutaImagen = rutaImagen.getNext();
       Path projectDir = Paths.get("").toAbsolutePath();
       Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
       File archivoImagen = rutaAbsoluta.toFile();
        if (!archivoImagen.exists()) {
            System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
            return;
        }
            // Carga la nueva imagen
            Image image1 = new Image(archivoImagen.toURI().toString());
            imagen1.setImage(image1);
   }
   
   @FXML
   private void atrasImagenEditar() throws IOException {
       rutaImagen = rutaImagen.getPrevious();
       Path projectDir = Paths.get("").toAbsolutePath();
       Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
       File archivoImagen = rutaAbsoluta.toFile();
        if (!archivoImagen.exists()) {
            System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
            return;
        }
            // Carga la nueva imagen
            Image image1 = new Image(archivoImagen.toURI().toString());
            imagen1.setImage(image1);
   }
   
   @FXML
   private void guardarVehiculo() throws IOException {
       EstadoD estado = estadoVehiculo.getSelectionModel().getSelectedItem();
       if(estado== EstadoD.NoDisponible){
           App.catalogo.eliminarVehiculo(vehiculoUsar.getContent());
           App.ActualizarListaCars();
           
       }
       
       // Aquí se debe actualizar todos los datos;
       
       App.ActualizarListaUsuarios();
       vehiculoMostrado.setVisible(true);
       vehiculoEditar.setVisible(false);
       
       // Se debe mostrar un mensaje que los cambios fueron actualizados y actualizar datos;
       
       
   }
}
