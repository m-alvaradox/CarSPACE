/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proy4.ed;

import Objects.*;
import TDAS.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import TDAS.CircularDoublyList;
import TDAS.DoublyNodeList;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class InformacionVehiculoController implements Initializable {
    
    @FXML
    private ImageView favoritoSinMarcar;
    @FXML
    private ImageView favoritoMarcado;
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
    private Label vendedor;
    @FXML
    private ImageView imagen;
    @FXML
    private ImageView bttnextphoto;
    @FXML
    private ImageView bttnbackphoto;
    @FXML
    private ImageView bttnhome;
    @FXML
    private ImageView regresar;
    
    DoublyNodeList<Vehiculos> vehiculoUsar;
    
    
    private CircularDoublyList<String> imagenes; // Imagenes que usa el vehiculo
    private DoublyNodeList<String> rutaImagen; // Nodo imagen 
    private int likes;
    private DoublyLinkedList<Vehiculos> FavVehiculos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Tooltip tbuttonhome = new Tooltip("Página Principal");
        Tooltip tbuttonregreso = new Tooltip("Regresar");
        Tooltip tbuttonnextphoto = new Tooltip("Siguiente foto");
        Tooltip tbuttonbackphoto = new Tooltip("Foto anterior");
        Tooltip tbuttonlikeit = new Tooltip("Me gusta");
        Tooltip tbuttonnotlikeit = new Tooltip("Ya no me gusta");
        Tooltip.install(bttnhome, tbuttonhome);
        Tooltip.install(regresar, tbuttonregreso);
        Tooltip.install(bttnextphoto, tbuttonnextphoto);
        Tooltip.install(bttnbackphoto, tbuttonbackphoto);
        Tooltip.install(favoritoSinMarcar, tbuttonlikeit);
        Tooltip.install(favoritoMarcado, tbuttonnotlikeit);
        
        vehiculoUsar = obtenerVehiculoUsar();
        regresar.setOnMouseClicked((event) -> {
            if(vehiculoUsar == CatalogoController.vehiculoUsar){
                try {
                    regresarCatalogo();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if(vehiculoUsar == FavoritosController.vehiculoUsar){
                try {
                    regresarFavoritos();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    regresarPrincipal();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        Vehiculos vehiculo = vehiculoUsar.getContent();
        marca.setText(vehiculo.getMarca());
        modelo.setText(vehiculo.getModelo());
        motor.setText(vehiculo.getMotor());
        ubicacion.setText(vehiculo.getUbicacion());
        kilometraje.setText(vehiculo.getKilometraje()+"");
        precio.setText(vehiculo.getPrecio()+" USD");
        year.setText(vehiculo.getAnio()+"");
        peso.setText(vehiculo.getPeso()+"");
        transmision.setText(vehiculo.getTransmision());
        vendedor.setText(""+vehiculo.getVendedor().getName()+" "+vehiculo.getVendedor().getLastname());
        
        // Condicion favorito
        
        FavVehiculos = App.userlogged.getFavVehiculos();
        
        condicionfavorito(vehiculo);
        
        // A cuantas personas le gustaron este vehiculo
        likes = obtenerLikes(vehiculo);

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
        Tooltip tlikes = new Tooltip("A "+likes+" personas le gustaron esto");
        Tooltip.install(imagen, tlikes);
        
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
    
    private DoublyNodeList<Vehiculos> obtenerVehiculoUsar(){
        DoublyNodeList<Vehiculos> vehiculo;
        if(CatalogoController.vehiculoUsar!=null){
            vehiculo = CatalogoController.vehiculoUsar;
        }else if(PrincipalController.vehiculoUsar!=null){
             vehiculo = PrincipalController.vehiculoUsar;
        } else {
            vehiculo = FavoritosController.vehiculoUsar;
        }
        return vehiculo;
    }
    
  
    
    @FXML
    private void principal() throws IOException{
        vehiculoUsar=null;
        CatalogoController.vehiculoUsar=null;
        App.setRoot("principal");
    }
    
    @FXML
    private void regresarCatalogo() throws IOException{
        App.setRoot("catalogo");
    }
    
    @FXML
    private void regresarFavoritos() throws IOException{
        App.setRoot("favoritos");
    }
    
    @FXML
    private void regresarPrincipal() throws IOException{
        App.setRoot("principal");
    }
   
   @FXML
   private void marcarFavorito() throws IOException{
       favoritoSinMarcar.setImage(new Image("/imagenes/favorito_marcado.png"));
       favoritoSinMarcar.setId("favoritoMarcado");
       favoritoMarcado = favoritoSinMarcar;
       
       // Aquí se debe agregar a la lista de carros favoritos
       Vehiculos vehiculo = vehiculoUsar.getContent();
       if(FavVehiculos == null) {
           FavVehiculos = new DoublyLinkedList<>();
       }
       FavVehiculos.addLast(vehiculo);
       App.userlogged.setFavVehiculos(FavVehiculos);
       App.ActualizarListaUsuarios();
       
       favoritoMarcado.setOnMouseClicked((evento) -> {    
            DesmarcarFavorito(evento);
      });
       
   }
   

   private void DesmarcarFavorito(Event evento){
       favoritoSinMarcar.setId("FavoritoSinMarcar");
       favoritoMarcado.setImage(new Image("/imagenes/favorito_sinMarcas.png"));
       favoritoSinMarcar = favoritoMarcado;
       
       // Aquí se debe eliminar el carro de la lista de favoritos
       Vehiculos vehiculo = vehiculoUsar.getContent();
       FavVehiculos.eliminar(vehiculo);
       App.userlogged.setFavVehiculos(FavVehiculos);
       App.ActualizarListaUsuarios();
       
       favoritoSinMarcar.setOnMouseClicked(event -> {
           try {
               marcarFavorito();
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       });
       
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
        
        Tooltip tlikes = new Tooltip("A "+likes+" personas le gustaron esto");
        Tooltip.install(imagen, tlikes);
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
        
        Tooltip tlikes = new Tooltip("A "+likes+" personas le gustaron esto");
        Tooltip.install(imagen, tlikes);
   }

    private void condicionfavorito(Vehiculos vehiculo) {
        
        if(FavVehiculos == null || FavVehiculos.isEmpty()) {
            System.out.println("Lista favoritos esta vacia");
            return;
        }
        DoublyNodeList<Vehiculos> current = FavVehiculos.getHeader();
        
            while(current != null) {
                if (current.getContent().compareTo(vehiculo) != 0) {
                    System.out.println("Vehiculo marcado como favorito");
                    favoritoSinMarcar.setImage(new Image("/imagenes/favorito_marcado.png"));
                    favoritoSinMarcar.setId("favoritoMarcado");
                    favoritoMarcado = favoritoSinMarcar;
                    favoritoMarcado.setOnMouseClicked((evento) -> {    
                        DesmarcarFavorito(evento);
                    });
                    return;
                } else {
                current = current.getNext(); }
            }
            
            System.out.println("Vehiculo no marcado como favorito");
    }

    private int obtenerLikes(Vehiculos v) {
        
        int contador = 0;
        
        for(User u : App.usuarios) {
            DoublyNodeList<Vehiculos> current = u.getFavVehiculos().getHeader();
            while (current != null) {
                if(current.getContent().compareTo(v) != 0) {
                    contador ++;
                }
                current = current.getNext();
            }  
        }
        return contador;
    }
   
  
}
