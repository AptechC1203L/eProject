/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import businessentity.Order;
import java.util.List;
import rbac.Session;

/**
 *
 * @author chin
 */
public interface IOrderController extends Remote {

    Order createOrder(String sessionId, Order order) throws RemoteException;

    Order getOrder(String sessionId, int orderId) throws RemoteException;
    
    List<Order> getAllOrders(String sessionId) throws RemoteException;

    List<Order> getActiveOrders(String sessionId) throws RemoteException;
    
    boolean updateOrder(String sessionId, Order newOrder) throws RemoteException;

    boolean deleteOrder(String SessionId, int orderId) throws RemoteException;

    boolean updateOrderStatus(String sessionId, int orderId, String newStatus)
            throws RemoteException;
}
