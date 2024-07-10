
package com.espol.proy4.ed;

import Objects.Gender;
import Objects.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MiperfilController implements Initializable {
    
    @FXML
    private Label l_user;
    @FXML
    private Label l_first_name;
    @FXML
    private Label l_last_name;
    @FXML
    private Label l_gender;
    @FXML
    private Label l_date_birth;
    @FXML
    private ImageView foto_gender;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        User userlogged = App.userlogged;
        
        l_user.setText(userlogged.getUsername());
        l_first_name.setText(userlogged.getName());
        l_last_name.setText(userlogged.getLastname());
        l_date_birth.setText(userlogged.getDate_birth());
        l_gender.setText(userlogged.getGender().toString());
        
        if(userlogged.getGender() == Gender.MASCULINO) {
            Image image = new Image(App.fileimages+"masculino.jpg");
            foto_gender.setImage(image);
        } else if (userlogged.getGender() == Gender.FEMENINO) {
            Image image = new Image(App.fileimages+"femenino.jpg");
            foto_gender.setImage(image);
            
        }
    }
    
    @FXML
    private void regresar() throws IOException{
        App.setRoot("principal");
    }


   
    
}
