/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toko;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import konek.koneksi;
import sun.security.util.Password;

/**
 * FXML Controller class
 *
 * @author Rara ^___^
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private JFXButton up;

    @FXML
    private JFXButton in;

    @FXML
    private JFXButton metu;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;
    
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
        
        user.setText("");
        pass.setText("");
    }   

    @FXML
    private void signup(javafx.event.ActionEvent event) {
        try {
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            // Load the new fxml
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Signup.fxml"));
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

    @FXML
    private void signin(javafx.event.ActionEvent event){ 
        if(user.getText().equals("") || pass.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Harap isi");
        }
        else{
        try{
            sql="SELECT * FROM tb_akun WHERE username='"+user.getText()+"'AND password = '"+pass.getText()+"'";
            rs=stat.executeQuery(sql);
            while(rs.next()){
                String namakirim=rs.getString(2);
                if(user.getText().equals(rs.getString("username"))&&pass.getText().equals(rs.getString("password")))
                {
                    try {
                        // Hide this current window (if this is what you want)
                        ((Node)(event.getSource())).getScene().getWindow().hide();

                        // Load the new fxml
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("Home.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        
                        HomeController Data = new HomeController();
                        Data = fxmlLoader.getController();
                        
                        Data.datauser(namakirim);

                        // Create new stage (window), then set the new Scene
                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setScene(scene);
                        stage.show();
            
                        } catch (IOException e) {
                            System.out.println("Failed to create new Window." + e);
                        }
                }
                else{
                        JOptionPane.showMessageDialog(null,"username/password salah");
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
        
    }

    @FXML
    private void exit(javafx.event.ActionEvent event) {
        Stage stage = (Stage) metu.getScene().getWindow();
        stage.close();
    }
    
}
