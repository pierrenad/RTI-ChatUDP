/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * CURAX = CUrrency RAte eXchange
 */
package Protocol_Info;

import Interface.ConsoleServeur;
import Interface.Requete;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.LogServeur;

/**
 *
 * @author Pierre
 */
public class RequeteInfo implements Requete, Serializable{
     
    public static final int CONVERT = 1;
    
    private int type;
    private Socket socketClient;
    private LogServeur ls;

    private String chargeUtile;
    
    public RequeteInfo(int t, String cu)
    {
        setType(t);
        chargeUtile = cu;
    }
    
    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getChargeUtile() {
        return chargeUtile;
    }
    
    public Runnable createRunnable(Socket s, ConsoleServeur cs, ObjectInputStream ois, ObjectOutputStream oos) {
            
            socketClient = s;
            
            return new Runnable()
            {
                @Override
                public void run()
                {
                    requeteConvert(socketClient, ls, oos);
                }
            }; 
    }
    
    
    private void requeteConvert(Socket sock, ConsoleServeur ls, ObjectOutputStream oos) { 
       
        ReponseInfo rep;
        try {
            StringTokenizer st = new StringTokenizer(this.getChargeUtile(), "#");
            String monnaieBase = st.nextToken();
            String monnaieFinale = st.nextToken();
            String montant = st.nextToken(); 
            
            String url_str = "https://api.exchangerate-api.com/v4/latest/" + monnaieBase;
            
            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            
            
            // Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();
            
            // Accessing object
            JsonObject jso = jsonobj.getAsJsonObject("rates");
            JsonElement jse = jso.get(monnaieFinale);
            
            
            montant = String.valueOf(Float.parseFloat(montant)*Float.parseFloat(String.valueOf(jse)));
            System.out.println("Montant : " + montant);
            rep = new ReponseInfo(ReponseInfo.CONVERT_OK, montant);
        } catch (Exception ex) {
            Logger.getLogger(RequeteInfo.class.getName()).log(Level.SEVERE, null, ex);
            rep = new ReponseInfo(ReponseInfo.CONVERT_PASOK, "Erreur " + ex.getMessage());
        }
        
        try {
            //oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);
            oos.flush();

        } catch (IOException ex) {
            Logger.getLogger(ReponseInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
