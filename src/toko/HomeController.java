/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toko;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import konek.koneksi;

/**
 * FXML Controller class
 *
 * @author Rara ^___^
 */
public class HomeController implements Initializable {

    @FXML
    private JFXTextField admin;
    @FXML
    private TextField kode;
    @FXML
    private TextField nama;
    @FXML
    private ComboBox kat;
    ObservableList<String> list1=FXCollections.observableArrayList("Makanan","Minuman","Sembako","dll");
    @FXML
    private ComboBox pack;
    ObservableList<String> list2=FXCollections.observableArrayList("pcs","Box");
    @FXML
    private TextField harga;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton refresh;
    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton exit;
    @FXML
    private JFXButton logout;
    @FXML
    private JFXButton update;
    @FXML
    private ComboBox cari;
    ObservableList<String> list3=FXCollections.observableArrayList("All","Makanan","Minuman","Sembako","dll");
    @FXML
    private JFXButton golek;
    @FXML
    private TableView<da.data> tabel;
    @FXML
    private TableColumn<?, ?> barkod;
    @FXML
    private TableColumn<?, ?> barang;
    @FXML
    private TableColumn<?, ?> kategori;
    @FXML
    private TableColumn<?, ?> isi;
    @FXML
    private TableColumn<?, ?> price;
    
    Connection con;
    Statement stat;
    ResultSet rs;
    PreparedStatement sta;
    
    private ObservableList<da.data> data=FXCollections.observableArrayList();
    private String kodbar,nambar,kate,packa,har,kateg=null,packag=null,namau;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        koneksi db = new koneksi();
        db.config();
        con=db.con;
        stat=db.stm;
        
        kat.setItems(list1);
        pack.setItems(list2);
        cari.setItems(list3);
        kat.setValue("Pilih kategori");
        pack.setValue("Pilih");
        cari.setValue("All");
        
        setkolomtable();
        ambildatabase();
        settabel();
    }

    public void datauser(String namauser){
        namau=namauser;
        admin.setText(namau);
    }
    
    private void settabel(){
        tabel.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               da.data pl=(tabel.getItems().get(tabel.getSelectionModel().getSelectedIndex()));
               kode.setText(pl.getKodebarang());
               nama.setText(pl.getNamabarang());
               kat.setValue(pl.getKategori());
               pack.setValue(pl.getPackages());
               harga.setText(pl.getHarga());
            }
        });
    }
    
    private void setkolomtable(){
        barkod.setCellValueFactory(new PropertyValueFactory<>("kodebarang"));
        barang.setCellValueFactory(new PropertyValueFactory<>("namabarang"));
        kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        isi.setCellValueFactory(new PropertyValueFactory<>("packages"));
        price.setCellValueFactory(new PropertyValueFactory<>("harga"));
    }
    
    private void ambildatabase(){
        data.clear();
        String SQL="SELECT * FROM tb_barang";
        try{
            sta=con.prepareStatement(SQL);
            rs=sta.executeQuery();
            while(rs.next()){
                data.add(new da.data(""+rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        }catch(SQLException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null,ex);
        }
        tabel.setItems(data);
    }

    @FXML
    private void save(ActionEvent event) throws SQLException {
        kodbar=kode.getText();
        nambar=nama.getText();
        kate=kat.getValue().toString();
        packa=pack.getValue().toString();
        har=harga.getText();
        if(kate=="Makanan"){
            kateg="Makanan";
        }
        else if(kate=="Minuman"){
            kateg="Minuman";
        }
        else if(kate=="Sembako"){
            kateg="Sembako";
        }
        else if(kate=="dll"){
            kateg="dll";
        }
        if(packa=="pcs"){
            packag="pcs";
        }
        else if(packa=="Box"){
            packag="Box";
        }
        
        String SQL="INSERT INTO `tb_barang`(`kode_barang`, `nama_barang`, `kategori`, `packages`, `harga`) VALUES (?,?,?,?,?)";
        try{
            sta=con.prepareStatement(SQL);
            sta.setString(1,kodbar);
            sta.setString(2,nambar);
            sta.setString(3,kateg);
            sta.setString(4,packag);
            sta.setString(5,har);
            
            int i=sta.executeUpdate();
            if(i==1){
                JOptionPane.showMessageDialog(null,"SUCCESS");
            }
            else{
                JOptionPane.showMessageDialog(null,"Data Can't be Write");
            }
            sta.close();
        }catch(SQLException se){
            JOptionPane.showMessageDialog(null,se);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        ambildatabase();
    }

    @FXML
    private void refresh(ActionEvent event) {
        kode.setText("");
        nama.setText("");
        kat.setValue("Pilih Kategori");
        pack.setValue("Pilih");
        harga.setText("");
        cari.setValue("Pilih Pencarian");
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING,"Yakin untuk menghapus?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult()==ButtonType.YES){
            String SQL="DELETE FROM `tb_barang` WHERE kode_barang=?";
            
            try{
                sta=con.prepareStatement(SQL);
                sta.setInt(1, Integer.valueOf(kode.getText()));
                int i=sta.executeUpdate();
                if(i==1){
                    JOptionPane.showMessageDialog(null,"Delete Akun Succesfully");
                    ambildatabase();
                    kode.setText("");
                    nama.setText("");
                    kat.setValue("Pilih Kategori");
                    pack.setValue("Pilih");
                    harga.setText("");
                    cari.setValue("Pilih Pencarian");
                }else{
                    JOptionPane.showMessageDialog(null,"Delete Data Error");
                }
                sta.close();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void logout(ActionEvent event) {
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

    @FXML
    private void update(ActionEvent event) {
        kodbar=kode.getText();
        nambar=nama.getText();
        kate=kat.getValue().toString();
        packa=pack.getValue().toString();
        har=harga.getText();
        if(kate=="Makanan"){
            kateg="Makanan";
        }
        else if(kate=="Minuman"){
            kateg="Minuman";
        }
        else if(kate=="Sembako"){
            kateg="Sembako";
        }
        else if(kate=="dll"){
            kateg="dll";
        }
        if(packa=="pcs"){
            packag="pcs";
        }
        else if(packa=="Box"){
            packag="Box";
        }
        
        String SQL="UPDATE `tb_barang` SET `nama_barang`=?,`kategori`=?,`packages`=?,`harga`=? WHERE kode_barang=?";
        try{
            sta=con.prepareStatement(SQL);
            sta.setString(1,nambar);
            sta.setString(2,kateg);
            sta.setString(3,packag);
            sta.setString(4,har);
            sta.setString(5,kodbar);
            
            int i=sta.executeUpdate();
            if(i==1){
                JOptionPane.showMessageDialog(null,"SUCCESS");
            }
            else{
                JOptionPane.showMessageDialog(null,"Data Can't be Write");
            }
            sta.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        ambildatabase();
    }

    @FXML
    private void cari(ActionEvent event) throws SQLException {
        kate=cari.getValue().toString();
        String SQL1="SELECT * FROM tb_barang WHERE kategori = 'Makanan'";
        String SQL2="SELECT * FROM tb_barang WHERE kategori = 'Minuman'";
        String SQL3="SELECT * FROM tb_barang WHERE kategori = 'Sembako'";
        String SQL4="SELECT * FROM tb_barang WHERE kategori = 'dll'";
        if(kate=="All"){
            ambildatabase();
        }
        else if(kate=="Makanan"){
            data.clear();
            String namaj=null;
            try{
                sta=con.prepareStatement(SQL1);
                rs=sta.executeQuery();
                while(rs.next()){
                    if(rs.getString(3).equalsIgnoreCase("dll")){
                        namaj="dll";
                    }
                    else if(rs.getString(3).equalsIgnoreCase("Sembako")){
                        namaj="Sembako";
                    }
                    else if(rs.getString(3).equalsIgnoreCase("Minuman")){
                        namaj="Minuman";
                    }
                    else{
                        namaj="Makanan";
                    }
                    data.add(new da.data(""+rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                }
            }catch(SQLException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null,ex);
            }  
        }
        else if(kate=="Minuman"){
            data.clear();
            String namaj=null;
            try{
                sta=con.prepareStatement(SQL2);
                rs=sta.executeQuery();
                while(rs.next()){
                    if(rs.getString(3).equalsIgnoreCase("dll")){
                        namaj="dll";
                    }
                    else if(rs.getString(3).equalsIgnoreCase("Sembako")){
                        namaj="Sembako";
                    }
                    else if(rs.getString(3).equalsIgnoreCase("Makanan")){
                        namaj="Makanan";
                    }
                    else{
                        namaj="Minuman";
                    }
                    data.add(new da.data(""+rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                }
            }catch(SQLException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null,ex);
            }  
        }
        else if(kate=="Sembako"){
            data.clear();
            String namaj=null;
            try{
                sta=con.prepareStatement(SQL3);
                rs=sta.executeQuery();
                while(rs.next()){
                    if(rs.getString(3).equalsIgnoreCase("dll")){
                        namaj="dll";
                    }
                    else if(rs.getString(3).equalsIgnoreCase("Makanan")){
                        namaj="Makanan";
                    }
                    else if(rs.getString(3).equalsIgnoreCase("Minuman")){
                        namaj="Minuman";
                    }
                    else{
                        namaj="Sembako";
                    }
                    data.add(new da.data(""+rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                }
            }catch(SQLException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null,ex);
            }  
        }
        else if(kate=="dll"){
            data.clear();
            String namaj=null;
            try{
                sta=con.prepareStatement(SQL4);
                rs=sta.executeQuery();
                while(rs.next()){
                    if(rs.getString(3).equalsIgnoreCase("Makanan")){
                        namaj="Makanan";
                    }
                    else if(rs.getString(3).equalsIgnoreCase("Sembako")){
                        namaj="Sembako";
                    }
                    else if(rs.getString(3).equalsIgnoreCase("Minuman")){
                        namaj="Minuman";
                    }
                    else{
                        namaj="dll";
                    }
                    data.add(new da.data(""+rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                }
            }catch(SQLException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null,ex);
            }  
        }
        else{
            JOptionPane.showMessageDialog(null, "Harap pilih yang akan dicari");
        }
    }
    
}
