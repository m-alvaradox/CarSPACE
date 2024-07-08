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
import java.util.Collection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private ComboBox<String> modelos;
    @FXML
    private TextField RangoInicio;
    @FXML
    private TextField RangoFinal;
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
        ObservableList<Marca> ComboMarcas = FXCollections.observableArrayList(Marca.values());
        marcas.setItems(ComboMarcas);
        modelos.getItems().setAll("Elija una marca");
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
        
        marcas.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue!=null){
                ObservableList<String> ComboModelos = FXCollections.observableArrayList(newValue.getModelos());
                modelos.setItems(ComboModelos);
            }else{
                modelos.getItems().clear();
            }
        });   
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
    
@FXML
private void Buscar() {
    Marca selectedMarca = marcas.getValue();
    String selectedModelo = (String) modelos.getValue();
    String selectedRango = (String) rango.getValue();
    if (selectedMarca != null && selectedModelo != null) {
        catalogo = App.catalogo.filtrarPorMarcaYModelo(selectedMarca.toString(), selectedModelo.toString()); 
    } else if("Kilometraje".equals(selectedRango)){
        String inicio = RangoInicio.getText();
        String limite = RangoFinal.getText();
        catalogo = App.catalogo.filtrarPorRangoDeKilometraje(Integer.parseInt(inicio), Integer.parseInt(limite));
    } else {
        Double inicio = Double.valueOf(RangoInicio.getText());
        Double limite = Double.valueOf(RangoFinal.getText());
        catalogo = App.catalogo.filtrarPorRangoDePrecio(inicio, limite);
    }
    limpiar();
    actualizarVista();
}

private void limpiar(){
   // Restablecer campos de texto
    RangoInicio.setText(null);
    RangoFinal.setText(null);

    // Limpiar ComboBoxes
    marcas.getSelectionModel().clearSelection();
    modelos.getSelectionModel().clearSelection();
    rango.getSelectionModel().clearSelection();
    // Añadir elementos de prompt a los ComboBoxes
    ObservableList<Marca> ComboMarcas = FXCollections.observableArrayList();
    ComboMarcas.add(null); // Añadir un elemento nulo como prompt
    ComboMarcas.addAll(Marca.values());
    marcas.setItems(ComboMarcas);

    ObservableList<String> ComboModelos = FXCollections.observableArrayList();
    ComboModelos.add("Modelo");
    modelos.setItems(ComboModelos);

    ObservableList<String> ComboRango = FXCollections.observableArrayList();
    ComboRango.add("Rango");
    ComboRango.addAll("Kilometraje", "Precio");
    rango.setItems(ComboRango);
    
    // Listener para actualizar modelos cuando se selecciona una marca
    marcas.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            ObservableList<String> modelosList = FXCollections.observableArrayList(newValue.getModelos());
            modelosList.add(0, "Modelo");
            modelos.setItems(modelosList);
        } else {
            modelos.getItems().clear();
            modelos.getItems().add("Modelo");
        }
        modelos.getSelectionModel().selectFirst();
    });

    // Seleccionar el primer elemento (prompt text)
    marcas.getSelectionModel().selectFirst();
    modelos.getSelectionModel().selectFirst();
    rango.getSelectionModel().selectFirst();
}

private void actualizarVista() {
    
    if (!catalogo.isEmpty()) {
        vehiculoUsar = catalogo.getHeader();
        Vehiculos vehiculo = vehiculoUsar.getContent();
        MarcaYModelo.setText(vehiculo.getMarca() + " " + vehiculo.getModelo());
        year.setText(String.valueOf(vehiculo.getAnio()));
        kilometraje.setText(String.valueOf(vehiculo.getKilometraje()));
        ubicacion.setText(vehiculo.getUbicacion());
        precio.setText(String.valueOf(vehiculo.getPrecio()));
        
        // Actualiza la imagen
        DoublyNodeList<String> rutaImagen = vehiculo.getFotos().getHeader();
        Path projectDir = Paths.get("").toAbsolutePath();
        Path rutaAbsoluta = projectDir.resolve(Paths.get("src/main/resources/imagenesCarros", rutaImagen.getContent()));
        File archivoImagen = rutaAbsoluta.toFile();
        if (!archivoImagen.exists()) {
            System.out.println("La imagen no se encuentra en la ruta especificada: " + rutaAbsoluta.toString());
            return;
        }
        Image image1 = new Image(archivoImagen.toURI().toString());
        imagen.setImage(image1);
    }
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
