/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.ConnectionFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rbac.SessionManager;

public class Main {

    public static void main(String[] args) {
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }
        try {
            ConnectionFactory connFactory =
                    new ConnectionFactory("localhost", 1433, "sa", "1111", "courier");
            SessionManager m = new SessionManager();
            OrderController orderController = new OrderController(m, connFactory);
            IAuthenticator authenticator = new Authenticator(m);
            UserController usercontroller = new UserController(m);
            Registry registry = LocateRegistry.createRegistry(1099);
            
            registry.bind("orders", orderController);
            registry.bind("authenticator", authenticator);
            registry.bind("users", usercontroller);
            System.out.println("Server listening...");
        } catch (Exception e) {
            System.err.println("OrderManager exception:");
            e.printStackTrace();
        }
    }
}