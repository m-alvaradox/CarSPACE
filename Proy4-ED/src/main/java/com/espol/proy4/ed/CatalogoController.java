/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proy4.ed;
import java.io.IOException;
import TDAS.*;
import Objects.*;
import java.net.URL;
import java.util.ResourceBundle;

import Objects.Marca;
import Objects.Modelo;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CatalogoController implements Initializable {
    
    @FXML
    private ComboBox<Marca> marcas;
    @FXML
    private ComboBox<Modelo> modelos;
    @FXML
    private ComboBox rango;
    @FXML
    private Label MarcaYModelo;
    @FXML
    private Label year;
    @FXML
    private Label kilometraje;  
    @FXML
    private Label ubicacion;
    @FXML
    private Label precio;
    @FXML
    private ImageView imagen;
            
    
    DoublyLinkedList<Vehiculos> catalogo;
    public static DoublyNodeList<Vehiculos> vehiculoUsar;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogo = App.catalogo.getVehiculos();
        marcas.getItems().setAll(Marca.values());
        modelos.getItems().setAll(Modelo.values());
        rango.getItems().setAll("Kilometraje","Precio");
        if(!catalogo.isEmpty()){
            vehiculoUsar = catalogo.getHeader();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            year.setText(vehiculo.getAnio()+"");
            kilometraje.setText(vehiculo.getKilometraje()+"");
            ubicacion.setText(vehiculo.getUbicacion());
            precio.setText(vehiculo.getPrecio()+"");
            DoublyNodeList<String> rutaImagen = vehiculo.getFotos().getHeader();
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
        }  
    } 
    
    @FXML
    private void siguienteVehiculo() throws IOException {
        if(vehiculoUsar.getNext()!=null){
            vehiculoUsar = vehiculoUsar.getNext();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            year.setText(vehiculo.getAnio()+"");
            kilometraje.setText(vehiculo.getKilometraje()+"");
            ubicacion.setText(vehiculo.getUbicacion());
            precio.setText(vehiculo.getPrecio()+"");
            DoublyNodeList<String> rutaImagen = vehiculo.getFotos().getHeader();
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
        }else {
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
            vehiculoUsar = vehiculoUsar.getPrevious();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            year.setText(vehiculo.getAnio()+"");
            kilometraje.setText(vehiculo.getKilometraje()+"");
            ubicacion.setText(vehiculo.getUbicacion());
            precio.setText(vehiculo.getPrecio()+"");
            DoublyNodeList<String> rutaImagen = vehiculo.getFotos().getHeader();
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
        }else {
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
    
    private void opcionElegidaMarca(Marca option){
        // Aquí realizará la acción cuando selección alguna opción
    }
    @FXML
    private void principal() throws IOException {
        App.setRoot("principal");
    }
    
    @FXML
    private void masInformacion() throws IOException{
        App.setRoot("InformacionVehiculo");
    }
    
}
