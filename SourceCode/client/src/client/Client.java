/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import entity.Order;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rbac.Session;
import rmi.IAuthenticator;
import rmi.IOrderController;
/**
 *
 * @author chin
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                IOrderController orders;
                IAuthenticator authenticator = null;

                try {
                    orders = (IOrderController) Naming.lookup("rmi://localhost:1099/orders");
                    authenticator = (IAuthenticator) Naming.lookup("rmi://localhost:1099/authenticator");

                    Session session = authenticator.login("chin", "admin");
                    if (session == null) {
                        System.out.println("Cannot login");
                        System.exit(-1);
                    }
                    orders.createOrder(session.getSessionId(), new Order("chinorder", "chin", "kin"));

                    orders.updateOrderStatus(session.getSessionId(), "chinorder", "CONFIRMED");

                    Order order = orders.getOrder(session.getSessionId(), "chinorder");
                    System.out.println(order);

                } catch (RemoteException ex) {
                    System.out.println(ex.detail.getMessage());
                } catch (NotBoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }

                new NewJFrame().setVisible(true);
            }
        });
    }
}
