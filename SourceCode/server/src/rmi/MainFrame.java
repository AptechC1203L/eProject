/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.ConnectionFactory;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import rbac.SessionCollection;

public class MainFrame extends javax.swing.JFrame {
    public MainFrame() {
        initComponents();
        start();
    }

    private Registry registry;
    private ConnectionFactory connFactory;
    private SessionCollection m;
    private UserController us;
    private IOrderController orderController;
    private  IUserController userController;
    private IAuthenticator authenticator ;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnStop = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnStop.setText("Stop");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btnStop)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnStop)
                .addGap(18, 18, 18)
                .addComponent(lblStatus)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void start(){
        try {
            connFactory = new ConnectionFactory("localhost", "sa", "1111", "courier");
            
            m = new SessionCollection();
            us = new UserController(m, connFactory);
            
            orderController = new OrderController(m, connFactory, us);
            userController = new UserController(m, connFactory);
            authenticator = new Authenticator(m);
            
            registry = LocateRegistry.createRegistry(1099);         
            registry.bind("orders", orderController);
            registry.bind("users", userController);
            registry.bind("authenticator", authenticator);
            
            lblStatus.setText("Server start...");
        } catch (Exception e) {
            System.err.println("OrderManager exception:");
            e.printStackTrace();
        }
    }
    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        try { 
            registry.unbind("orders");
            UnicastRemoteObject.unexportObject(orderController, true);
            registry.unbind("users");
            UnicastRemoteObject.unexportObject(userController, true);
            registry.unbind("authenticator");
            UnicastRemoteObject.unexportObject(authenticator, true);
            
            lblStatus.setText("Server stop...");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("OrderManager exception:");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnStopActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
