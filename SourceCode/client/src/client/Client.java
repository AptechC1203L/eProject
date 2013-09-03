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
        try {
            IOrderController orders =
                    (IOrderController) Naming.lookup("rmi://localhost:1099/orders");
            IAuthenticator authenticator =
                    (IAuthenticator) Naming.lookup("rmi://localhost:1099/authenticator");
            
            Session session = authenticator.login("chin", "admin");
            if (session == null) {
                System.out.println("Cannot login");
                System.exit(-1);
            }
            orders.createOrder(session.getSessionId(), new Order("chinorder", "chin", "kin"));
            
            orders.updateOrderStatus(session.getSessionId(), "chinorder", "CONFIRMED");
            
            Order order = orders.getOrder(session.getSessionId(), "chinorder");
            System.out.println(order);
            
        } catch (NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            System.out.println(ex.detail.getMessage());
        }
    }
}
