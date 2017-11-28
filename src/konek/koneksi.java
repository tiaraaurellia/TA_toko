/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konek;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Rara ^___^
 */
public class koneksi {
    public Connection con;
    public Statement stm;
    
    public void config(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/ta_toko","root","");
            stm=con.createStatement();
            System.out.println("oke");
        }
        catch(Exception e){
            System.out.println("Failed");
            JOptionPane.showMessageDialog(null,"koneksi gagal"+e.getMessage());
        }
    } 
}
