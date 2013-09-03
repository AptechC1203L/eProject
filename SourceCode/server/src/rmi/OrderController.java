/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import entity.Order;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rbac.Permission;
import rbac.Session;
import rbac.SessionManager;

/**
 *
 * @author chin
 */
public class OrderController extends UnicastRemoteObject implements IOrderController {
    
    private SessionManager sessionManager;
    static public Order singletonOrder = new Order("123", "chin", "kin");
    
    public OrderController(SessionManager sessionManager) throws RemoteException {
        super();
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean createOrder(String sessionId,  Order order) throws RemoteException {
        if (sessionManager.isAuthorized(
                sessionId,
                new Permission("create", "order"))) {
            singletonOrder = order;
            return true;
        } else {
            throw new AccessException("You can't create order!");
        }
    }

    @Override
    public Order getOrder(String sessionId, String orderId) throws RemoteException {
        return singletonOrder;
    }

    @Override
    public boolean updateOrder(String sessionId, Order newOrder) throws RemoteException {
        if (sessionManager.isAuthorized(
                sessionId,
                new Permission("update", "order"))) {
            singletonOrder = newOrder;
            return true;
        } else {
            throw new AccessException("You can't update order!");
        }
    }

    @Override
    public boolean deleteOrder(String sessionId, String orderId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateOrderStatus(String sessionId, String orderId, String newStatus) throws RemoteException {
        if (sessionManager.isAuthorized(
                sessionId,
                new Permission("update", "order.status"))) {
            singletonOrder.setStatus(newStatus);
            return true;
        } else {
            throw new AccessException("You can't update order.status!");
        }
    }
}
