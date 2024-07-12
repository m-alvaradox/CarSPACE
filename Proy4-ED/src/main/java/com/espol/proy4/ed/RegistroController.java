package com.espol.proy4.ed;

import Objects.Gender;
import Objects.User;
import TDAS.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import Objects.Vehiculos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class RegistroController {
    
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidos;
    @FXML
    private TextField usuario;
    @FXML
    private DatePicker fecha;
    @FXML
    private PasswordField password;
    @FXML
    private RadioButton masculino;
    @FXML
    private RadioButton femenino;
    @FXML
    private RadioButton otro;
    
    @FXML
    private void iniciarSesion() throws IOException {
        App.setRoot("iniciarSesion");
    }
    
    @FXML
    private void registrar() throws IOException { // se crea un nuevo usuario;
        
        Gender genero = null;
        
        if (masculino.isSelected()) {
                genero = genero.MASCULINO;
            } else if (femenino.isSelected()) {
                genero = genero.FEMENINO;
            } else if (otro.isSelected()) {
                genero = genero.OTRO;
            }
        
        if(nombre.getText()!=null && apellidos.getText()!=null && fecha.getValue()!=null && usuario.getText()!=null && password.getText()!=null && genero != null){
            String nom = nombre.getText();
            String ape = apellidos.getText();
            String user = usuario.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            String naci = fecha.getValue().format(formatter);
            String contra = password.getText();

            DoublyLinkedList <Vehiculos> misvehiculos = new DoublyLinkedList<>();
            DoublyLinkedList <Vehiculos> FavVehiculos = new DoublyLinkedList<>();
            // Al construir el nuevo usuario, mis listas seran null
          
            if(App.contains(user) != null) {
                Alert alert= new Alert(AlertType.ERROR);
                alert.setHeaderText("Creación de cuenta");
                alert.setTitle("Error");
                String msgerr = String.format("%s ya existe en el sistema", user);
                alert.setContentText(msgerr);
                String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
                alert.getDialogPane().getStylesheets().add(css);
                alert.getDialogPane().getStyleClass().add("dialog-paneError");
                alert.showAndWait();
            } else {
                App.createUser(new User(nom, ape, user, naci, genero, contra, misvehiculos, FavVehiculos));
                
                Alert alert= new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Creación de cuenta");
                alert.setTitle("Éxito");
                String msgconf = String.format("%s fue registrado exitosamente :D, por favor inicie sesión", user);
                alert.setContentText(msgconf);
                String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
                alert.getDialogPane().getStylesheets().add(css);
                alert.getDialogPane().getStyleClass().add("dialog-paneConfirmacion");
                alert.showAndWait();
                iniciarSesion();
            }
            
            
        } else {
            Alert alert= new Alert(AlertType.ERROR);
            alert.setHeaderText("Creación de cuenta");
            alert.setTitle("Error de pedido");
            alert.setContentText("Debe completar todos los campos para crear la cuenta");
            alert.showAndWait();
        }
    }
                
    }
