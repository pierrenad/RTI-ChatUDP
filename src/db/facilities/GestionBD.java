/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.facilities;

import java.sql.*;
import java.util.Random;

/**
 *
 * @author Pierre
 */
public class GestionBD {
    protected Connection con = null;
    protected Statement state = null; 
    protected ResultSet resSet = null; 
    
    public GestionBD(){
    }
    
    public synchronized void connection(String bd, String log, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bd+"?serverTimezone=UTC", log, pwd); 
    }
    
    public synchronized ResultSet requete(String req) {
        resSet = null;
        try {
            PreparedStatement requete = con.prepareStatement(req); 
            
            resSet = requete.executeQuery();
            //resSet.next(); 
            return resSet; 
        } catch (SQLException ex) {
            System.err.println("<requete> " + ex.getMessage());
            return null; 
        }
    }
}
