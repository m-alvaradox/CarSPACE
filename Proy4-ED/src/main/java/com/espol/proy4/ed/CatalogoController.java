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
import Objects.SubTipo;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
    private ComboBox<SubTipo> subTipos;
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
    private Label vendedor;
    @FXML
    private ImageView imagen;
    @FXML
    private ImageView bttnhouse;
    @FXML
    private ImageView bttnadelante;
    @FXML
    private ImageView bttnatras;
    
            
    
    public static DoublyLinkedList<Vehiculos> catalogo;
    public static DoublyNodeList<Vehiculos> vehiculoUsar;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ToolTips
        Tooltip tbuttonhouse = new Tooltip("Página Principal");
        Tooltip tbuttonnext = new Tooltip("Adelante");
        Tooltip tbuttonback = new Tooltip("Atrás");
        Tooltip.install(bttnhouse, tbuttonhouse);
        Tooltip.install(bttnadelante, tbuttonnext);
        Tooltip.install(bttnatras, tbuttonback);
         
        catalogo = App.catalogo.getVehiculos();
        ObservableList<Marca> ComboMarcas = FXCollections.observableArrayList(Marca.values());
        marcas.setItems(ComboMarcas);
        subTipos.getItems().addAll(SubTipo.values());
        modelos.getItems().setAll("Elija una marca");
        rango.getItems().setAll("Kilometraje","Precio");
        marcas.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue!=null){
                ObservableList<String> ComboModelos = FXCollections.observableArrayList(newValue.getModelos());
                modelos.setItems(ComboModelos);
            }else{
                modelos.getItems().clear();
            }
        }); 
        if(!catalogo.isEmpty()){
            vehiculoUsar = catalogo.getHeader();
            actualizarInfo();   
        }
    } 
    
    private void actualizarInfo() {
            Vehiculos vehiculo = vehiculoUsar.getContent();
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            year.setText("Año: "+vehiculo.getAnio()+"");
            kilometraje.setText(vehiculo.getKilometraje()+" kms");
            ubicacion.setText("Ubicación: "+vehiculo.getUbicacion());
            precio.setText(vehiculo.getPrecio()+" USD");
            vendedor.setText("Vendedor: "+vehiculo.getVendedor().getName()+" "+vehiculo.getVendedor().getLastname());
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
    }
    
    
    @FXML
    private void siguienteVehiculo() throws IOException {
        if(vehiculoUsar.getNext()!=null){
            vehiculoUsar = vehiculoUsar.getNext();
            actualizarInfo();
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
            actualizarInfo();
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
    Marca selectedMarca= null;
    String selectedModelo = "";
    SubTipo subTipo=subTipos.getValue();
    String selectedRango="";
    if(marcas.getValue()!=null &&modelos.getValue() !=null ){
        selectedMarca =  marcas.getValue();
        selectedModelo = modelos.getValue().toString();
    }
  
    if(rango.getValue()!=null){
        selectedRango =  rango.getValue().toString();
    }
    if(selectedMarca == null && selectedModelo == null && rango.getValue()==null && subTipos.getValue()==null){
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Filtras Vehiculos");
        alert.setTitle("Error al filtrar");
        alert.setContentText("No ha seleccionado ningún itém");
        String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(css);
        alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
        alert.showAndWait();
    }else if (selectedMarca != null && selectedModelo != null) {
        catalogo = App.catalogo.filtrarPorMarcaYModelo(selectedMarca.toString(), selectedModelo); 
    } else if(subTipo!=null){
        catalogo = App.catalogo.filtrarPorTipoVehiculo(subTipo);
    }   else if("Kilometraje".equals(selectedRango)){
        String inicio = RangoInicio.getText();
        String limite = RangoFinal.getText();
        catalogo = App.catalogo.filtrarPorRangoDeKilometraje(Integer.parseInt(inicio), Integer.parseInt(limite));
    } else {
        int inicio = Integer.parseInt(RangoInicio.getText());
        int limite = Integer.parseInt(RangoFinal.getText());
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
    subTipos.getSelectionModel().clearSelection();
    
    // Añadir elementos de prompt a los ComboBoxes
    ObservableList<Marca> ComboMarcas = FXCollections.observableArrayList();
    ComboMarcas.addAll(Marca.values());
    marcas.setItems(ComboMarcas);

    ObservableList<String> ComboModelos = FXCollections.observableArrayList();
    modelos.setItems(ComboModelos);

    ObservableList<String> ComboRango = FXCollections.observableArrayList();
    ObservableList<SubTipo> ComboSubTipo = FXCollections.observableArrayList(SubTipo.values());
    
    ComboRango.add("Rango");
    ComboRango.addAll("Kilometraje", "Precio");
    rango.setItems(ComboRango);
    subTipos.setItems(ComboSubTipo);
    
    // Listener para actualizar modelos cuando se selecciona una marca
    marcas.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            ObservableList<String> modelosList = FXCollections.observableArrayList(newValue.getModelos());
            modelos.setItems(modelosList);
        } else {
            modelos.getItems().clear();
        }
    });
    rango.getSelectionModel().selectFirst();
    marcas.getSelectionModel().select(0);
}

private void actualizarVista() { 
    if (!catalogo.isEmpty()) {
        vehiculoUsar = catalogo.getHeader();
        actualizarInfo();
    }
}
       
    @FXML
    private void principal() throws IOException {
        vehiculoUsar=null;
        catalogo=null;
        App.setRoot("principal");
    }
    
    @FXML
    private void masInformacion() throws IOException{
        App.setRoot("InformacionVehiculo");
    }
    
}
