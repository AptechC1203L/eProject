/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import entity.Order;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import rbac.Permission;
import rbac.Session;
import rbac.SessionManager;

/**
 *
 * @author chin
 */
public class OrderController extends UnicastRemoteObject implements IOrderController {
    
    private SessionManager sessionManager;
    static public Order singletonOrder = new Order("123", "chin", "kin", Double.parseDouble("20"), "abc");
    
    public OrderController(SessionManager sessionManager) throws RemoteException {
        super();
        this.sessionManager = sessionManager;
    }

    @Override
    public Order createOrder(String sessionId,  Order order) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("create", "order"));
        
        singletonOrder = order;
        return new Order("newID",
                order.getSender(),
                order.getReceiver(),
                order.getWeight(),
                order.getProfile());
    }

    @Override
    public Order getOrder(String sessionId, String orderId) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("view", "order"));
        return singletonOrder;
    }

    @Override
    public boolean updateOrder(String sessionId, Order newOrder) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("update", "order"));
        
        singletonOrder = newOrder;
        return true;
    }

    @Override
    public boolean deleteOrder(String sessionId, String orderId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateOrderStatus(String sessionId, String orderId, String newStatus) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("update", "order.status"));
 
        singletonOrder.setStatus(newStatus);
        return true;
    }

    @Override
    public List<Order> getAllOrders(String sessionId) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("view", "order"));
        
        ArrayList<Order> allOrders = new ArrayList();
        allOrders.add(singletonOrder);
        return allOrders;
    }
}
