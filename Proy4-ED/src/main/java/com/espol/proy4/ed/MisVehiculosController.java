/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proy4.ed;

import Objects.*;
import TDAS.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private Label vehiculoEstado;
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
    @FXML
    private ImageView bttnhome;
    @FXML
    private ImageView bttnextcar;
    @FXML
    private ImageView bttnbackcar;
    @FXML
    private ImageView bttnextphoto;
    @FXML
    private ImageView bttnbackphoto;
    
    
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
    private ImageView bttndelete;
    @FXML
    private ImageView bttnedit;
    private ImageView btonSeleccionarImagen;
    @FXML
    private ComboBox<EstadoD> estadoVehiculo;
    
    
    User usuario;
    private DoublyLinkedList<Vehiculos> listaVehiculo;// Aquí se inicia el DoublyCircularLinkedList
    private CircularDoublyList<String> imagenes = new CircularDoublyList<>(); // Imagenes que usa el vehiculo
    private CircularDoublyList<String> imagenesTemporales;
    private DoublyNodeList<String> rutaTemporal;
    private DoublyNodeList<String> rutaImagen; // Nodo imagen 
    private DoublyNodeList<Vehiculos> vehiculoUsar; // Aquí se almacena el vehiculo que usa en pantalla
    private ArrayList<String> imagenesEliminar = new ArrayList<>();
    private ArrayList<String> imagenesAgregar = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Tooltip
        Tooltip tbuttonhome = new Tooltip("Página Principal");
        Tooltip tbuttonnextcar = new Tooltip("Siguiente publicación");
        Tooltip tbuttonbackcar = new Tooltip("Publicación anterior");
        Tooltip tbuttonnextphoto = new Tooltip("Siguiente foto");
        Tooltip tbuttonbackphoto = new Tooltip("Foto anterior");
        Tooltip tbuttondelete = new Tooltip("Eliminar");
        Tooltip tbuttonedit = new Tooltip("Editar");
        Tooltip.install(bttnhome, tbuttonhome);
        Tooltip.install(bttnextcar, tbuttonnextcar);
        Tooltip.install(bttnbackcar, tbuttonbackcar);
        Tooltip.install(bttnextphoto, tbuttonnextphoto);
        Tooltip.install(bttnbackphoto, tbuttonbackphoto);
        Tooltip.install(bttndelete, tbuttondelete);
        Tooltip.install(bttnedit, tbuttonedit);
        
        
        
        estadoVehiculo.getItems().addAll(EstadoD.values());
        usuario = App.userlogged;
        
        listaVehiculo = usuario.getMisVehiculos(); 
        if(!listaVehiculo.isEmpty()){
            vehiculoMostrado.setVisible(true);
            sinVehiculo.setVisible(false);
            vehiculoUsar = listaVehiculo.getHeader(); 
            Vehiculos vehiculo = vehiculoUsar.getContent();
            estadoVehiculo.getSelectionModel().select(vehiculo.getEstado());
            marca.setText(vehiculo.getMarca());
            modelo.setText(vehiculo.getModelo());
            motor.setText(vehiculo.getMotor());
            ubicacion.setText(vehiculo.getUbicacion());
            kilometraje.setText(vehiculo.getKilometraje()+"");
            precio.setText((int)vehiculo.getPrecio()+" USD");
            year.setText(vehiculo.getAnio()+"");
            peso.setText(vehiculo.getPeso()+"");
            transmision.setText(vehiculo.getTransmision());
            vehiculoEstado.setText(estadoVehiculo.getSelectionModel().getSelectedItem().toString());

            // Aquí se debe mostrar todos los datos

            ArrayList<AtributoAdicional> listaAtributos = vehiculo.getAtributoAdicional();
            ArrayList<Historial> listaHistorial = vehiculo.gethistorial();

            imagenes= vehiculo.getFotos();// Doubly linked list para mostrar imagenes
            imagenesTemporales = copiarImagenes();
            rutaImagen = imagenes.getHeader();
            rutaTemporal = imagenesTemporales.getHeader();

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
    
    private CircularDoublyList<String> copiarImagenes(){
        CircularDoublyList<String> copia = new CircularDoublyList<>();
        DoublyNodeList<String> current = imagenes.getHeader(); // Copia estas imagenes a temporal
        while(current!=imagenes.getLast()){
            copia.addLast(current.getContent());
            current = current.getNext();
        }
        copia.addLast(imagenes.getLast().getContent()); // copia el ultimo
        return copia;
    }   

    @FXML
    private void principal() throws IOException{
        App.setRoot("principal");
    }
   
    @FXML
    private void principal2() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edición vehículo");
        alert.setHeaderText("Confirmación requerida");
        alert.setContentText("¿Estás seguro que deseas salir? Los datos realizados no se guardarán");
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(botonSi, botonNo);
        String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(css);
        alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.isPresent()&& resultado.get() == botonSi){
            for(int i=0; i<imagenesAgregar.size();i++){ // Se elimina las imagenes
                String ruta = imagenesAgregar.get(i);
                Path projectDir = Paths.get("").toAbsolutePath();
                 Path rutaDestino =  projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", ruta));
                 try {
                      Files.delete(rutaDestino);
                 } catch (IOException ex) {
                       ex.printStackTrace();
                 } 
            }
            App.setRoot("principal");
        } else{
            alert.close();
        }
    }
    
   private void actualizarVentana() throws IOException{
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
            vehiculoEstado.setText(estadoVehiculo.getSelectionModel().getSelectedItem().toString());

            // Aquí se debe mostrar todos los datos

            ArrayList<AtributoAdicional> listaAtributos = vehiculo.getAtributoAdicional();
            ArrayList<Historial> listaHistorial = vehiculo.gethistorial();

            imagenes= vehiculo.getFotos();// Doubly linked list para mostrar imagenes
            imagenesTemporales = copiarImagenes();
            rutaImagen = imagenes.getHeader();
            rutaTemporal = imagenesTemporales.getHeader();

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
            precio.setText((int)vehiculo.getPrecio()+" USD");
            year.setText(vehiculo.getAnio()+"");
            peso.setText(vehiculo.getPeso()+"");
            transmision.setText(vehiculo.getTransmision());
            vehiculoEstado.setText(estadoVehiculo.getSelectionModel().getSelectedItem().toString());

            // Aquí se debe mostrar todos los datos

            ArrayList<AtributoAdicional> listaAtributos = vehiculo.getAtributoAdicional();
            ArrayList<Historial> listaHistorial = vehiculo.gethistorial();

            imagenes= vehiculo.getFotos();// Doubly linked list para mostrar imagenes
            imagenesTemporales = copiarImagenes();
            rutaImagen = imagenes.getHeader();
            rutaTemporal = imagenesTemporales.getHeader();
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
            precio.setText((int)vehiculo.getPrecio()+" USD");
            year.setText(vehiculo.getAnio()+"");
            peso.setText(vehiculo.getPeso()+"");
            transmision.setText(vehiculo.getTransmision());
            vehiculoEstado.setText(estadoVehiculo.getSelectionModel().getSelectedItem().toString());

            // Aquí se debe mostrar todos los datos

            ArrayList<AtributoAdicional> listaAtributos = vehiculo.getAtributoAdicional();
            ArrayList<Historial> listaHistorial = vehiculo.gethistorial();

            imagenes= vehiculo.getFotos();// Doubly linked list para mostrar imagenes
            imagenesTemporales = copiarImagenes();
            rutaImagen = imagenes.getHeader();
            rutaTemporal = imagenesTemporales.getHeader();
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
       rutaTemporal = rutaTemporal.getNext();
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
       rutaTemporal = rutaTemporal.getPrevious();
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
        paneAtributos1.getChildren().clear();
        paneHistorial1.getChildren().clear();
        imagenesEliminar.clear();
        imagenesAgregar.clear();
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
        Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaTemporal.getContent()));
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
            image.setOnMouseClicked((event)-> {
            paneAtributos1.getChildren().remove(hb);
            });
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
            title.setId("title");
            descripcion.setId("descripcion");
            hb.getChildren().addAll( title, descripcion, image);
            paneAtributos1.getChildren().add(hb);
        }
        
        for(int i=0; i<listaHistorial.size(); i++){ // Aquí ses llena todo el historial
            Historial h = listaHistorial.get(i);
            HBox hb = new HBox();
            hb.setAlignment(Pos.CENTER_LEFT);
            hb.setSpacing(5);
            ImageView image = new ImageView(new Image("/imagenes/cerrar.png"));
            image.setOnMouseClicked((event)-> {
                paneHistorial1.getChildren().remove(hb);
            });
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
            tipo.getStyleClass().add("comboBox_filtros");
            descripcion.getStyleClass().add("text-field");
            fecha.getStyleClass().add("text-field");
            hb.getChildren().addAll(tipo, fecha, descripcion, image);
            paneHistorial1.getChildren().add(hb);
        }
       

        // Se debe mostrar los datos del vehiculo en textd field para que los pueda actualizar;
       
   }
   
   @FXML
   private void eliminarImagen() throws IOException {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover imagen");
        alert.setHeaderText("Confirmación requerida");
        alert.setContentText("¿Estás seguro que deseas eliminar esta imagen?");
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(botonSi, botonNo);
        String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(css);
        alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.isPresent()&& resultado.get() == botonSi){
            DoublyNodeList<String> nueva= rutaTemporal.getPrevious();  // va a regresar al anterior;
            imagenesTemporales.removeNode(rutaTemporal); // Eliminar el nodo de la imagen;
            imagenesEliminar.addLast(rutaTemporal.getContent());
            rutaTemporal = nueva; // Empezar en el anterior;
            siguienteImagenEditar();
        } else{
            alert.close();
        }
       
   }
   
   @FXML
   private void addImagen() throws IOException {
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
                Path projectDir = Paths.get("").toAbsolutePath();
                Path rutaDestino =  projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", ruta));
                Files.copy(imageSelected.toPath(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
                imagenesAgregar.addLast(ruta);
                DoublyNodeList<String> siguiente= rutaTemporal.getNext(); // Obtener la imagen siguiente 
                DoublyNodeList<String> nuevaImagen = new DoublyNodeList<>(ruta);
                nuevaImagen.setPrevious(rutaTemporal);
                nuevaImagen.setNext(siguiente);
                rutaTemporal.setNext(nuevaImagen);
                siguiente.setPrevious(nuevaImagen);
                siguienteImagenEditar();
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }    
        }
   }
   
   @FXML
   private void siguienteImagenEditar() throws IOException {
       rutaTemporal = rutaTemporal.getNext();
       Path projectDir = Paths.get("").toAbsolutePath();
       Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaTemporal.getContent()));
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
       rutaTemporal = rutaTemporal.getPrevious();
       Path projectDir = Paths.get("").toAbsolutePath();
       Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaTemporal.getContent()));
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
   private void addAtributos() throws IOException {
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
        cerrar.setFitWidth(17);
        cerrar.setFitHeight(14);
        title.setPrefWidth(76);
        title.setPrefHeight(21);
        descripcion.setPrefWidth(105);
        descripcion.setPrefHeight(21);
        title.getStyleClass().add("text-field");
        descripcion.getStyleClass().add("text-field");
        cerrar.setOnMouseClicked((event)-> {
            paneAtributos1.getChildren().remove(hb);
        });
        hb.getChildren().addAll(title, descripcion, cerrar);
        paneAtributos1.getChildren().addAll(hb);
   }
   
   @FXML
   private void addHistorial() throws IOException {
        HBox hb = new HBox();
        ComboBox<tipoHistorial> tipo = new ComboBox();
        ImageView cerrar = new ImageView(new Image("/imagenes/cerrar.png"));
        cerrar.setFitWidth(17);
        cerrar.setFitHeight(14);
        cerrar.setOnMouseClicked((event)-> {
            paneHistorial1.getChildren().remove(hb);
        });
        tipo.getItems().setAll(tipoHistorial.values());
        TextField textField = new TextField();
        textField.setPrefWidth(112);
        DatePicker fecha = new DatePicker();
        fecha.setPromptText("Date");
        fecha.getStyleClass().add("text-field");
        fecha.setPrefWidth(97);
        fecha.setPrefHeight(27);
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER_LEFT);
        tipo.getStyleClass().add("comboBox_filtros");
        textField.getStyleClass().add("text-field");
        hb.getChildren().addAll(tipo,fecha, textField, cerrar);
        paneHistorial1.getChildren().addAll(hb);
   }
   
   @FXML
   private void removeVehiculo() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Vehiculo");
        alert.setHeaderText("Confirmación requerida");
        alert.setContentText("¿Estás seguro que deseas eliminar este vehiculo?");
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(botonSi, botonNo);
        String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(css);
        alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.isPresent()&& resultado.get() == botonSi){
            Vehiculos v = vehiculoUsar.getContent(); // Vehiculo a eliminar
            DoublyNodeList<Vehiculos> copia = vehiculoUsar;
            CircularDoublyList<String> rutaImagenes = v.getFotos();
            DoublyNodeList<String> ruta = rutaImagenes.getHeader();
            while(ruta!=rutaImagenes.getLast()){ // Se elimina las imagenes
                String rutaI = ruta.getContent();
                Path projectDir = Paths.get("").toAbsolutePath();
                 Path rutaDestino =  projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaI));
                 try {
                      Files.delete(rutaDestino);
                 } catch (IOException ex) {
                       ex.printStackTrace();
                 }
                 ruta = ruta.getNext();
            }
            if(ruta==rutaImagenes.getLast()){
                 Path projectDir = Paths.get("").toAbsolutePath();
                 Path rutaDestino =  projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", ruta.getContent()));
                 try {
                      Files.delete(rutaDestino);
                 } catch (IOException ex) {
                       ex.printStackTrace();
                 }
            }
            listaVehiculo.eliminar(v);
            App.catalogo.eliminarVehiculo(v);
            App.userlogged.setMisVehiculos(listaVehiculo);
            App.ActualizarListaCars();
            App.ActualizarListaUsuarios();
            if(copia.getNext()!=null){
                //vehiculoUsar = copia.getNext();
                siguienteVehiculo();
            } else if(vehiculoUsar.getPrevious()!=null){
                //vehiculoUsar = copia.getPrevious();
                atrasVehiculo();
            } else{
                // Aquí se debe mostrar que no hay mas vehiculos
                vehiculoMostrado.setVisible(false);
                sinVehiculo.setVisible(true);
            }
        } else{
            alert.close();
        }
   }
   
   @FXML
   private void guardarVehiculo() throws IOException {
       
       // Aquí se declaran las listas para historial, y atributosAdicionales
        ArrayList<Historial> listaHistorial = new ArrayList<>();
        ArrayList<AtributoAdicional> listaAtributosAdicionales = new ArrayList<>();
        
        
        for(Node caja: paneHistorial1.getChildren()){ // Recorre cada VBox para el historial de Servicios y Accidentes
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
        
        for( Node caja: paneAtributos1.getChildren()){
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
       EstadoD estado = estadoVehiculo.getSelectionModel().getSelectedItem();
      
       for(int i=0; i<imagenesEliminar.size();i++){ // Se elimina las imagenes
           String ruta = imagenesEliminar.get(i);
           Path projectDir = Paths.get("").toAbsolutePath();
            Path rutaDestino =  projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", ruta));
            try {
                 Files.delete(rutaDestino);
            } catch (IOException ex) {
                  ex.printStackTrace();
            }
       }
       EstadoD estadoAntiguo = EstadoD.valueOf(vehiculoEstado.getText());
       Vehiculos vehiculoAntiguo = vehiculoUsar.getContent();
       Vehiculos vehiculo = vehiculoUsar.getContent();
       estadoVehiculo.getSelectionModel().select(vehiculo.getEstado());
        // Aquí se debe actualizar todos los datos;
       if(marca1!=null && modelo1!=null && motor1!=null && ubicacion1!=null && kilometraje1!=null && precio1!=null && year1!=null && peso1!=null && transmision1!=null){
            vehiculo.setMarca(marca1.getText());
            vehiculo.setModelo(modelo1.getText());
            vehiculo.setMotor(motor1.getText());
            vehiculo.setUbicacion(ubicacion1.getText());
            vehiculo.setKilometraje(Integer.parseInt(kilometraje1.getText()));
            vehiculo.setPrecio(Double.parseDouble(precio1.getText()));
            vehiculo.setAnio(Integer.parseInt(year1.getText()));
            vehiculo.setPeso(Double.parseDouble(peso1.getText()));
            vehiculo.setTransmision(transmision1.getText());
            vehiculo.setAtributoAdicional(listaAtributosAdicionales);
            vehiculo.setHistorial(listaHistorial);
            vehiculo.setEstado(estado);
            vehiculo.setFotos(imagenesTemporales);
            App.catalogo.editarVehiculo(vehiculoAntiguo, vehiculo);
            vehiculoUsar.setContent(vehiculo);
            App.userlogged.setMisVehiculos(listaVehiculo);
            if(estado== EstadoD.NoDisponible){
                App.catalogo.eliminarVehiculo(vehiculoUsar.getContent());
            }
            if(estadoAntiguo== EstadoD.NoDisponible && estado==EstadoD.Disponible){
                App.catalogo.agregarVehiculo(vehiculo);
            }
            
            App.ActualizarListaCars();
            App.ActualizarListaUsuarios();
            actualizarVentana();
            vehiculoMostrado.setVisible(true);
            vehiculoEditar.setVisible(false);
            
       }
       
       
       // Se debe mostrar un mensaje que los cambios fueron actualizados y actualizar datos;
       
       
   }
}
