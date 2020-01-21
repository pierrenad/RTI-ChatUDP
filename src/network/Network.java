/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import Interface.*;

/**
 *
 * @author Pierre
 */
public class Network {
    
    public Network() {
    }
    
    public Socket Init() {
        Socket cSock;
        String adresse = "192.168.43.236";
        int port = 57000; 

        try {
            FileInputStream in = new FileInputStream("donnees.properties");
            Properties data = new Properties();
            data.load(in);
            adresse = (String)data.get("ip");
            port = Integer.parseInt((String) data.getProperty("portFront"));
        } catch (Exception e) { 
            System.err.println("<Network-Init> " + e.getMessage()); 
        }

        cSock = null;
        try {
            cSock = new Socket(adresse, port); 
        }
        catch (Exception e) {
            System.err.println("<Network-Init> Erreur ! Aucune correspondance serveur trouvée : " + e.getMessage()); 
        }

        return cSock;
    }
    
    public Socket InitInter() {
        Socket cSock;
        String adresse = "192.168.43.236";
        int port = 57001; 
                
        try {
            FileInputStream in = new FileInputStream("donnees.properties");
            Properties data = new Properties();
            data.load(in);
            adresse = (String)data.get("ip");
            port = Integer.parseInt((String) data.getProperty("portInt"));
        } catch (Exception e) { 
            System.err.println("<Network-InitInter> " + e.getMessage()); 
        }
        
        cSock = null;
        try {
            cSock = new Socket(adresse, port); 
        }
        catch (Exception e) {
            System.err.println("<Network-InitInter> Erreur ! Aucune correspondance serveur trouvée : "+ e.getMessage()); 
        }
        
        return cSock;
    }
    
    public Socket InitConvert() {
        Socket cSock;
        String adresse = "192.168.43.236";
        int port = 60000; 
                
        try {
            FileInputStream in = new FileInputStream("donnees.properties");
            Properties data = new Properties();
            data.load(in);
            adresse = (String)data.get("ip");
            port = Integer.parseInt((String) data.getProperty("portChat"));
        } catch (Exception e) { 
            System.err.println("<Network-InitInter> " + e.getMessage()); 
        }
        
        cSock = null;
        try {
            cSock = new Socket(adresse, port); 
        }
        catch (Exception e) {
            System.err.println("<Network-InitInter> Erreur ! Aucune correspondance serveur trouvée : "+ e.getMessage()); 
        }
        
        return cSock;
    }
    
    public void SendRequest(Socket cSock, Requete req) {
        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(cSock.getOutputStream()); 
            oos.writeObject(req);
            oos.flush();
        } 
        catch (Exception e) {
            System.err.println("<Network-SendRequest> " + e.getMessage()); 
        }
    }
}
