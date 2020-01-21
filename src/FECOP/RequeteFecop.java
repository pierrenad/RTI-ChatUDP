/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FECOP;

import Interface.*;
import Message.MsgSigned;
import Message.SecretMsg;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

/**
 *
 * @author Pierre
 */
public class RequeteFecop implements Requete, Serializable {
    public static final int LOGIN = 0; 
    public static final int POST_QUESTION = 1;
    public static final int ANSWER_QUESTION = 2; 
    public static final int POST_EVENT = 3; 
    
    public static final int CLOSE = 15; 
    
    private String charge;
    private int type;
    private MsgSigned msg; 
    ObjectInputStream in;
    
    private MulticastSocket MCSock = null;
    private int portChat; 
    
    private static Properties hashtable = new Properties();
    
    public Properties getHashtable(){return hashtable;}
    
    public void setCharge(String chargeUtile) {
        charge = chargeUtile;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
    public String getCharge() {
        return charge;
    }

    
    public RequeteFecop(int t, String ch) {
        type = t;
        charge = ch;
    }
    public RequeteFecop(int t, MsgSigned msg) {
        type = t;
        this.msg = msg; 
    }
    
    @Override
    public Runnable createRunnable (final Socket s, final ConsoleServeur cs, final ObjectInputStream ois, final ObjectOutputStream oos) { 
        in = ois;
        
        return new Runnable() {
            @Override
            public void run() {
                if(!traiteRequeteLogin(s, cs, in, oos)) { // Si ça se passe mal  
                    try {
                        s.close();
                    } catch (IOException ex) {
                        //System.out.println("<createRunnable> Erreur de connection au serveur  " + ex.getMessage());
                        Logger.getLogger(RequeteFecop.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else { 
                    try {
                        MCSock = new MulticastSocket(portChat);
                        MCSock.joinGroup(InetAddress.getByName("239.5.5.5"));

                        byte[] buf = new byte[256];
                        DatagramPacket paquet; 
                        InetAddress adresse;
                        int port;
                        String type = null;
                        String qtag = null;
                        
                        do {
                            paquet = new DatagramPacket(buf, 256);
                                MCSock.receive(paquet);
                                System.out.println("PAQUET RECU DANS LE SERVEUR");

                                adresse = paquet.getAddress();
                                port = paquet.getPort();
                                String message = new String(paquet.getData());
                                message = message.substring(0, paquet.getLength()); // Sinon caractères bizarres

                                StringTokenizer st = new StringTokenizer(message,":,@,#");
                                String user = st.nextToken();
                                String msg = st.nextToken();
                                if(st.hasMoreTokens()) {
                                    qtag = st.nextToken();
                                }
                                
                                if(msg.endsWith(" ") && msg.startsWith(" ")) { // Reponse
                                    qtag = "@" + qtag.substring(0,4);
                                    type = "REPONSE";
                                }
                                else if(msg.substring(msg.length()-1).equals("?")) { // Question 
                                    StringTokenizer st2 = new StringTokenizer(message, "?,#");
                                    st2.nextToken();
                                    String tag = st2.nextToken();
                                    String digest = st2.nextToken();

                                    String cut = msg.substring(3, msg.length());
                                    byte[] b = digest.getBytes();

                                    MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
                                    md.update(cut.getBytes());

                                    byte[] digestLoc = md.digest();
                                    if(MessageDigest.isEqual(digestLoc, b)) {
                                    }
                                    else {
                                    }

                                    type = "QUESTION";
                                    
                                    /*StringTokenizer st3 = new StringTokenizer(message,"#");
                                    message = st3.nextToken();*/
                                }
                                else {
                                    type = "EVENT";
                                }
                                System.out.println(type+"\n");
                        }while(true); 
                    } catch(Exception e) {
                        Logger.getLogger(RequeteFecop.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
                        traiteRequeteClose(s, cs, oos);
                    }
                }
            }
        };
    }

    private boolean traiteRequeteLogin(Socket sock, ConsoleServeur ls, ObjectInputStream ois, ObjectOutputStream oos) {
        String user = msg.getUser();
        long temps = msg.getTemps();
        double alea = msg.getAlea();
        byte[] msgD = msg.getDigest();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); // bouncycastle 
        
        ReponseFecop rep; 
        
        try { 
            try {
                FileInputStream in = new FileInputStream("config.properties");
                getHashtable().load(in);
            } catch (Exception ex) {
                System.err.println("<RequeteFecop> " + ex.getMessage()); 
            } 

            String pwd = null;

            pwd = getHashtable().getProperty(user);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(user.getBytes());
            md.update(pwd.getBytes());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream bdos = new DataOutputStream(baos);

            bdos.writeLong(temps); 
            bdos.writeDouble(alea);

            md.update(baos.toByteArray());
            byte[] msgDLocal = md.digest();

            if(MessageDigest.isEqual(msgD, msgDLocal)) {
                System.out.println("<RequeteFecop> Logged");
                ls.TraceEvenements("Logged # RequeteFecop"); 
                
                try {
                    FileInputStream in = new FileInputStream("donnees.properties");
                    try {
                        getHashtable().load(in);
                    } catch (IOException ex) {
                        System.err.println("IOException RequeteFECOP: " + ex.getMessage());
                    }
                } catch (FileNotFoundException ex) {
                    System.err.println("FileNotFound RequeteFECOP: " + ex.getMessage());
                }
                String adresse = getHashtable().getProperty("ip");
                String port = getHashtable().getProperty("portChat");
                portChat = Integer.parseInt(port);
                
                rep = new ReponseFecop(ReponseFecop.LOG_OK, adresse + "#" + port);
                try {
                    oos.writeObject(rep); 
                    oos.flush();
                    return true; 
                } 
                catch (IOException e) {
                    System.err.println("<RequeteFecop> Erreur réseau "+  e.getMessage());
                    rep = new ReponseFecop(ReponseFecop.LOG_PAS_OK , "");

                    try {
                        oos.writeObject(rep);
                        oos.flush();
                        return false; 
                    } catch (IOException ex) { 
                        Logger.getLogger(RequeteFecop.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
            else {
                System.err.println("<RequeteFecop> Erreur de login/password"); 
                rep = new ReponseFecop(ReponseFecop.LOG_PAS_OK , "");

                try {
                    oos.writeObject(rep);
                    oos.flush();
                    return false; 
                } catch (IOException ex) { 
                    Logger.getLogger(RequeteFecop.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
        catch(Exception e) {
            System.err.println("<RequeteFecop> " + e + e.getMessage()); 
        }
        return false; 
    }
    
    private void traiteRequeteClose(Socket sock, ConsoleServeur cs, ObjectOutputStream oos)
    {
        try {
            ReponseFecop rep;
            rep = new ReponseFecop(ReponseFecop.CLOSE , "Fermeture");
            
            try {
                oos.writeObject(rep);
                oos.flush();
            } catch (IOException ex) {
                Logger.getLogger(RequeteFecop.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RequeteFecop.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(RequeteFecop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
