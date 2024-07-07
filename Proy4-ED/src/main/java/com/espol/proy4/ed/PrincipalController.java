/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proy4.ed;

import Objects.Vehiculos;
import TDAS.DoublyLinkedList;
import TDAS.DoublyNodeList;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class PrincipalController implements Initializable {
    @FXML
    private AnchorPane configuracion;
    @FXML
    private ImageView imagen;
    @FXML
    private ImageView buttonconfig;
    @FXML
    private ImageView bttnadelante;
    @FXML
    private ImageView bttnatras;
    @FXML
    private Label precio;
    @FXML
    private Label year;
    @FXML
    private Label ubicacion;
    @FXML
    private Label MarcaYModelo;
    
    @FXML
    private Label msgwelcome;
    
    
    DoublyLinkedList<Vehiculos> catalogo;
    public static DoublyNodeList<Vehiculos> vehiculoUsar;
    private static String rutaImagen = "src/main/resources/imagenes/" ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ToolTips
        
        Tooltip tbuttonconfig = new Tooltip("Configuración");
        Tooltip tbuttonnext = new Tooltip("Adelante");
        Tooltip tbuttonback = new Tooltip("Atrás");
        Tooltip.install(buttonconfig, tbuttonconfig);
        Tooltip.install(bttnadelante, tbuttonnext);
        Tooltip.install(bttnatras, tbuttonback);
        
        // TODO
        String msg = String.format("Hola, %s !", App.userlogged.getName());
        msgwelcome.setText(msg);
        
        catalogo = App.catalogo.getVehiculos();
        
        if(!catalogo.isEmpty()) {
            vehiculoUsar = catalogo.getHeader();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            year.setText(vehiculo.getAnio()+"");
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
    private void cerrar_configuracion() throws IOException {
        configuracion.setVisible(false);
    }
    
    @FXML
    private void abrir_configuracion() throws IOException {
        configuracion.setVisible(true);
    }
    
    @FXML
    private void cerrar_Sesion() throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText("Confirmación requerida");
        alert.setContentText("¿Estás seguro que deseas salir?");
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(botonSi, botonNo);
        String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(css);
        alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.isPresent()&& resultado.get() == botonSi){
            App.userlogged = null; // usuario logeado sera null al cerrar sesion
            alert.close();
            App.setRoot("iniciarSesion");
        } else{
            alert.close();
        }
    }
    
    @FXML
    private void mostrar_perfil() throws IOException {
        App.setRoot("miperfil");
    }
    
    @FXML
    private void mostrar_favoritos() throws IOException {
        App.setRoot("favoritos");
    }
        
    @FXML
    private void catalogo() throws IOException {
        App.setRoot("catalogo");
    }
    
    @FXML
    private void crearVenta() throws IOException {
        App.setRoot("crearVenta");
    }
    
    @FXML
    private void misVehiculos() throws IOException {
        App.setRoot("misVehiculos");
    }
    
    @FXML
    private void masInformacion() throws IOException {
        App.setRoot("InformacionVehiculo");
    }
    
    @FXML
    private void siguienteVehiculo() throws IOException {
        if(vehiculoUsar.getNext()!=null){
            vehiculoUsar = vehiculoUsar.getNext();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            year.setText(vehiculo.getAnio()+"");
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
}
