package com.espol.proy4.ed;

import Objects.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class IniciarSesionController {
    
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    
    @FXML
    private void registro() throws IOException {
        App.setRoot("registro");
    }
    
    @FXML
    private void iniciarSesion() throws IOException { // Aquí se debe verificar que exista el usuario y contraseña en la base de datos
        String user1 = user.getText();
        String password1 = password.getText();
        
        if(validarCredenciales(user1, password1)) {
            App.setRoot("principal");
        }
        
    }
    
    
    public boolean validarCredenciales(String user, String password) {
        
        User valid_user = App.contains(user);
        
        if(valid_user != null && valid_user.getPassword().equals(password)) {
            App.userlogged = valid_user; // defino el usuario logeado
            System.out.println("Inicio de sesion exitoso");
            return true;
        }  
        else {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Acceso denegado");
            alert.setTitle("Inicio de sesión");
            String msgerr = String.format("Usuario y/o contraseña incorrectos");
            alert.setContentText(msgerr);
            String css = this.getClass().getResource("/styles/estilos.css").toExternalForm();
            alert.getDialogPane().getStylesheets().add(css);
            alert.getDialogPane().getStyleClass().add("dialog-paneError");
            alert.showAndWait();
        }    
        return false;    
    }
}
