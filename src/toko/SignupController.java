/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toko;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import konek.koneksi;

/**
 * FXML Controller class
 *
 * @author Rara ^___^
 */
public class SignupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    String User,Pass;
    
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField  password;

    @FXML
    private JFXTextField nama;

    @FXML
    private JFXRadioButton cewe;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton laki;

    @FXML
    private JFXButton up;

    @FXML
    private JFXButton exit;
    
    Connection con;
    Statement stat;
    ResultSet rs;
    String sql;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        koneksi DB = new koneksi();
        DB.config();
        con=DB.con;
        stat=DB.stm;
        username.setText("");
        password.setText("");
        nama.setText("");
        gender.setUserData("");
    }    

    @FXML
    private void btnsignup(javafx.event.ActionEvent event) {
        String Username=username.getText();
        String Gender=null;
        if(cewe.isSelected()){
            Gender="P";
        }
        else if(laki.isSelected()){
            Gender="L";
        }
        String Password=password.getText();
        String Nama=nama.getText();
        if(username.getText().equals("") || password.getText().equals("") || nama.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Harap isi");
        }
        else{
        try{
            sql="INSERT INTO `tb_akun`( `username`, `password`, `nama`, `gender`) VALUES ('"+Username+"','"+Password+"','"+Nama+"','"+Gender+"')";
            int i = stat.executeUpdate(sql);
            if(i==1){
                try {
                        // Hide this current window (if this is what you want)
                        ((Node)(event.getSource())).getScene().getWindow().hide();

                        // Load the new fxml
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("Login.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());

                        // Create new stage (window), then set the new Scene
                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setScene(scene);
                        stage.show();
            
                        } catch (IOException e) {
                            System.out.println("Failed to create new Window." + e);
                        }
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }  
    }
    @FXML
    private void btnexit(javafx.event.ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
    
}
