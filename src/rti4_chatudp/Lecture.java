/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti4_chatudp;

import Fenetres.CHAT;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierre
 */
public class Lecture extends Thread {
    
    private CHAT chat;
    String ipAddr;
    private  MulticastSocket MCSock = null;
    
    public Lecture(int port, CHAT c) {
        try {
            FileInputStream in = new FileInputStream("donnees.properties");
            Properties data = new Properties();
            data.load(in);
            ipAddr = (String)data.get("ip");

            MCSock = new MulticastSocket(port);
            MCSock.joinGroup(InetAddress.getByName("239.5.5.5"));
            chat = c;
        }
        catch(Exception e) {
            Logger.getLogger(Lecture.class.getName()).log(Level.SEVERE, null, e);
        } 
    }

    @Override
    public void run() {
        try {
            byte[] buf = new byte[256]; 
            DatagramPacket paquet; 
            InetAddress adresse;
            int port;
            String type = null;
            int cpt = 0;
            String qtag = null;
            byte[] digest = null;
            
            while(true) {
                // réception d'une requete
                paquet = new DatagramPacket(buf, 256);
                MCSock.receive(paquet);

                adresse = paquet.getAddress();
                port = paquet.getPort();
                System.out.println("Requete reçue de " + adresse + " - port: " + port);
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
                    if(st2.hasMoreTokens()) {
                        digest = Base64.getDecoder().decode(st2.nextToken());
                    }

                    String cut = msg.substring(3, msg.length()); // on enleve les 3 espace qu'il y a apres le "pseudo" 

                    System.out.println("digest : " + digest);
                    System.out.println("tag : " + tag);
                    System.out.println("cut : " + cut);

                    MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
                    md.update(cut.getBytes());

                    byte[] digestLoc = md.digest();

                    if(MessageDigest.isEqual(digestLoc, digest)) {
                        type = "QUESTION";

                        StringTokenizer st3 = new StringTokenizer(message,"#");
                        message = st3.nextToken();
                    }
                    else {
                        message = "Digest corrompu";
                    }
                }
                else {
                    type = "EVENT";
                }

                chat.write(message, type, user, qtag);
            }
        }
        catch(Exception e) {
            Logger.getLogger(Lecture.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
