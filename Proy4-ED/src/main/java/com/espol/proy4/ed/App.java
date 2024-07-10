package com.espol.proy4.ed;

import java.io.IOException;

import Objects.*;
import TDAS.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static String fileusers = "src\\main\\resources\\datos\\usuarios.ser";
    public static String filecars = "src\\main\\resources\\datos\\vehiculos.ser";
    public static String fileimages = "imagenes/";

    public static ArrayList<User> usuarios = loadUsers();
        /* Mario Alvarado: Decidi usar un ArrayList para almacenar a los usuarios
       porque puedo acceder rapidamente a mis elementos por indice y es eficiente
       para lectura y busqueda. No me importa la posición donde quiero agregar el nuevo
       usuario, solo lo agrego al final. Considero también que no estamos implementando
       la funcionalidad de eliminar usuario en este proyecto.
    */
    
    public static User userlogged = null;
    public static ListaVehiculos catalogo = loadCars();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("iniciarSesion"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();

    }
    
    // Operaciones App
    
    public static ArrayList<User> loadUsers() {

        ArrayList<User> users_list = new ArrayList<>();
        
        try {
            users_list = Container.deserialize(fileusers);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
        
        return users_list;
    }
    
    public static ListaVehiculos loadCars() {

        ListaVehiculos cars_list = new ListaVehiculos();
        
        try {
            cars_list = Container.deserialize(filecars);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }

        return cars_list;
    }

    public static void createUser(User user) {
        
        usuarios.addLast(user);
        ActualizarListaUsuarios();
    }
        
    public static User contains(String user_to_enter) {
        for (int i = 0; i < usuarios.size(); i++) {
            User user = usuarios.get(i);
            String username = user.getUsername();
            if (username.equals(user_to_enter)) {
                return user ;
            }
        }
        return null;
    }

    public static void ActualizarListaUsuarios() {
        try {        
            Container.serialize(usuarios, fileusers);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void ActualizarListaCars() {
        try {
            Container.serialize(catalogo, filecars);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
    }
    
    public static void EliminarVehiculoFavorito(Vehiculos v){
        for(int i=0;i<usuarios.size();i++){
            User usuario = usuarios.get(i);
            DoublyLinkedList<Vehiculos> vehiculosFav = usuario.getFavVehiculos();
            if(!vehiculosFav.isEmpty()){
                DoublyNodeList<Vehiculos> p = vehiculosFav.getHeader();
                while(p!=null){
                    if(p.getContent().compareTo(v)==0){
                        vehiculosFav.eliminar(p.getContent());
                    }
                    p = p.getNext();
                }
                usuario.setFavVehiculos(vehiculosFav);
            }  
        }
        ActualizarListaUsuarios();
    }
}