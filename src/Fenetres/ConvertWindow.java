/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fenetres;

import network.Network;
import Protocol_Info.ReponseInfo;
import Protocol_Info.RequeteInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierre
 */
public class ConvertWindow extends javax.swing.JFrame {

    /**
     * Creates new form mainWindow_Info
     *
     */
        
    public ConvertWindow() {
            initComponents();
            this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jl_titre = new javax.swing.JLabel();
        jtf_montant = new javax.swing.JTextField();
        jl_montant = new javax.swing.JLabel();
        jcb_monnai_from = new javax.swing.JComboBox<>();
        jl_converir_en = new javax.swing.JLabel();
        jcb_monnai_to = new javax.swing.JComboBox<>();
        jl_titre_res = new javax.swing.JLabel();
        jl_montant_res = new javax.swing.JLabel();
        jb_convertir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusable(false);

        jl_titre.setFont(new java.awt.Font("Sylfaen", 0, 24)); // NOI18N
        jl_titre.setText("Convertion");

        jl_montant.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jl_montant.setText("Montant :");

        jcb_monnai_from.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "Dollar US", "Yen", "Franc Suisse", "Livre Sterling" }));

        jl_converir_en.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jl_converir_en.setText("Convertir en :");

        jcb_monnai_to.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euro", "Dollar US", "Yen", "Franc Suisse", "Livre Sterling" }));

        jl_titre_res.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jl_titre_res.setText("Résultat :");

        jl_montant_res.setText("0");

        jb_convertir.setText("Convertir");
        jb_convertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_convertirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_converir_en, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcb_monnai_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_montant)
                                .addGap(18, 18, 18)
                                .addComponent(jtf_montant, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcb_monnai_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jb_convertir, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jl_titre_res)
                        .addGap(18, 18, 18)
                        .addComponent(jl_montant_res, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jl_titre)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jl_titre)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_montant)
                    .addComponent(jtf_montant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_monnai_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_converir_en, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_monnai_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jb_convertir)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_titre_res, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_montant_res, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jb_convertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_convertirActionPerformed

            String monnaie;
            String dest;
            
            switch(jcb_monnai_from.getSelectedItem().toString())
            {
                case "Euro":            monnaie = new String("EUR");
                                        break;
                case "Dollar US":       monnaie = new String("USD");
                                        break;
                case "Yen":             monnaie = new String("JPY");
                                        break;
                case "Franc Suisse":    monnaie = new String("CHF");
                                        break;
                case "Livre Sterling":  monnaie = new String("GBP");
                                        break;
                default :               monnaie = new String("EUR");
            }
            
            switch(jcb_monnai_to.getSelectedItem().toString())
            {
                case "Euro":            dest = new String("EUR");
                                        break;
                case "Dollar US":       dest = new String("USD");
                                        break;
                case "Yen":             dest = new String("JPY");
                                        break;
                case "Franc Suisse":    dest = new String("CHF");
                                        break;
                case "Livre Sterling":  dest = new String("GBP");
                                        break;
                default :               dest = new String("EUR");
            }
            
            
            RequeteInfo req = new RequeteInfo(RequeteInfo.CONVERT,new String(monnaie + "#" + dest + "#" + jtf_montant.getText()));
            
            Network nw = new Network();
            Socket cliSock = nw.Init();
            
            nw.SendRequest(cliSock, req);
            
            ReponseInfo rep = null;
            try 
            {
                ObjectInputStream ois = new ObjectInputStream(cliSock.getInputStream());
                        
                rep = (ReponseInfo)ois.readObject();
                System.out.println(" *** Reponse reçue : " + rep.getCharge());

                if(rep.getCode() == ReponseInfo.CONVERT_OK)
                {
                    jl_montant_res.setText(rep.getCharge());
                }
                else
                {
                    System.out.println("No esta lol ");
                }
            } catch (Exception e) {
            }
            //ObjectInputStream ois = new ObjectInputStream(cliSock.get)     
        
    }//GEN-LAST:event_jb_convertirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConvertWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConvertWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConvertWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConvertWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConvertWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jb_convertir;
    private javax.swing.JComboBox<String> jcb_monnai_from;
    private javax.swing.JComboBox<String> jcb_monnai_to;
    private javax.swing.JLabel jl_converir_en;
    private javax.swing.JLabel jl_montant;
    private javax.swing.JLabel jl_montant_res;
    private javax.swing.JLabel jl_titre;
    private javax.swing.JLabel jl_titre_res;
    private javax.swing.JTextField jtf_montant;
    // End of variables declaration//GEN-END:variables
}
