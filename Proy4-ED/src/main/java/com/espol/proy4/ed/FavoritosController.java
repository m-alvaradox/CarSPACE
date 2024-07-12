
package com.espol.proy4.ed;

import Objects.Vehiculos;
import TDAS.DoublyLinkedList;
import TDAS.DoublyNodeList;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.Event;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FavoritosController implements Initializable {
    
    @FXML
    private Label label_favoritos;
    @FXML
    private Label MarcaYModelo;
    @FXML
    private Label precio;
    @FXML
    private ImageView bttnadelante;
    @FXML
    private ImageView bttnatras;
    @FXML
    private ImageView favoritoSinMarcar;
    @FXML
    private ImageView favoritoMarcado;
    @FXML
    private ImageView imagen;
    
    private DoublyLinkedList<Vehiculos> FavVehiculos;
    public static DoublyNodeList<Vehiculos> vehiculoUsar;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ToolTips
        Tooltip tbuttonnext = new Tooltip("Adelante");
        Tooltip tbuttonback = new Tooltip("Atrás");
        Tooltip tbuttonlikeit = new Tooltip("Me gusta");
        Tooltip tbuttonnotlikeit = new Tooltip("Ya no me gusta");
        Tooltip.install(bttnadelante, tbuttonnext);
        Tooltip.install(bttnatras, tbuttonback);
        Tooltip.install(favoritoSinMarcar, tbuttonlikeit);
        Tooltip.install(favoritoMarcado, tbuttonnotlikeit);
        
        String msg = String.format("Favoritos de %s", App.userlogged.getName());
        label_favoritos.setText(msg);
        
        FavVehiculos = App.userlogged.getFavVehiculos();
        
        if(!FavVehiculos .isEmpty()){
            vehiculoUsar = FavVehiculos .getHeader();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            precio.setText(vehiculo.getPrecio()+" USD");
            favoritoSinMarcar.setImage(new Image("/imagenes/favorito_marcado.png"));
            favoritoSinMarcar.setId("favoritoMarcado");
            favoritoMarcado = favoritoSinMarcar;
            favoritoMarcado.setOnMouseClicked((evento) -> {    
            DesmarcarFavorito(evento);
            });
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
            
            Tooltip tmasdatos = new Tooltip(vehiculo.toString());
            Tooltip.install(imagen, tmasdatos);
        }  
    }
 
    @FXML
    private void regresar() throws IOException{
        vehiculoUsar=null;
        App.setRoot("principal");
    }
    
    @FXML
    private void siguienteVehiculo() throws IOException {
        if(vehiculoUsar.getNext()!=null){
            favoritoSinMarcar.setImage(new Image("/imagenes/favorito_marcado.png"));
            favoritoSinMarcar.setId("favoritoMarcado");
            favoritoMarcado = favoritoSinMarcar;
            favoritoMarcado.setOnMouseClicked((evento) -> {    
            DesmarcarFavorito(evento);
            });
            vehiculoUsar = vehiculoUsar.getNext();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            precio.setText(vehiculo.getPrecio()+" USD");
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
            
            Tooltip tmasdatos = new Tooltip(vehiculo.toString());
            Tooltip.install(imagen, tmasdatos);
            
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
            favoritoSinMarcar.setImage(new Image("/imagenes/favorito_marcado.png"));
            favoritoSinMarcar.setId("favoritoMarcado");
            favoritoMarcado = favoritoSinMarcar;
            favoritoMarcado.setOnMouseClicked((evento) -> {    
            DesmarcarFavorito(evento);
            });
            vehiculoUsar = vehiculoUsar.getPrevious();
            Vehiculos vehiculo = vehiculoUsar.getContent();
            MarcaYModelo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo());
            precio.setText(vehiculo.getPrecio()+" USD");
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
            
            Tooltip tmasdatos = new Tooltip(vehiculo.toString());
            Tooltip.install(imagen, tmasdatos);
            
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
    private void masInformacion() throws IOException{
        App.setRoot("InformacionVehiculo");
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
}
