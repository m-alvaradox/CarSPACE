package com.espol.proy4.ed;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Objects.ListaVehiculos;
import Objects.User;
import TDAS.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    // Metodos estaticos a utilizar en el proyecto. Validaciones, etc.
    
    public static ArrayList<User> loadUsers() {

        ArrayList<User> users_list = new ArrayList<>();

        try(ObjectInputStream oit = new ObjectInputStream(new FileInputStream(App.fileusers))) {
            users_list = (ArrayList<User>) oit.readObject();
        } catch(Exception e) {
            System.out.println("No hay ningun usuario por el momento");
        }
        return users_list;
    }
    
    public static ListaVehiculos loadCars() {

        ListaVehiculos cars_list = new ListaVehiculos();
        try(ObjectInputStream oit = new ObjectInputStream(new FileInputStream(App.filecars))) {
            cars_list = (ListaVehiculos) oit.readObject();
        } catch(Exception e) {
            System.out.println("No hay ningun usuario por el momento");
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

    public static void ActualizarListaUsuarios(){
        try(ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(fileusers))) {
            out1.writeObject(usuarios);
            out1.flush();
        } catch(IOException ex) {
            System.out.println("Error al encontrar el archivo: " + ex.getMessage());
            ex.printStackTrace();  // Esto imprime la traza completa de la excepción
        }
    }
    
    public static void ActualizarListaCars(){
        try(ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(filecars))) {
            out1.writeObject(catalogo);
            out1.flush();
        } catch(IOException ex) {
            System.out.println("Error al encontrar el archivo: " + ex.getMessage());
            ex.printStackTrace();  // Esto imprime la traza completa de la excepción
        }
    }
}