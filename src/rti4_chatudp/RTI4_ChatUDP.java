/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti4_chatudp;

import java.io.FileInputStream;
import java.util.Properties;
import log.LogServeur;

/**
 *
 * @author Pierre
 */
public class RTI4_ChatUDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int portFront = 57000;
        try {
            FileInputStream in = new FileInputStream("donnees.properties"); 
            Properties data = new Properties();
            data.load(in);
            
            portFront = Integer.parseInt((String) data.getProperty("portFront"));
        } catch (Exception e) {
            System.err.println("<MainProgram> " + e.getMessage());
        }
        
        ListeTaches lt = new ListeTaches(); 
        LogServeur ls = new LogServeur(); 

        ServeurInfo serv = new ServeurInfo(portFront, lt, ls); 
        Thread th = new Thread(serv); 
        th.start();
    }
    
}
