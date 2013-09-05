/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import entity.Order;
import java.util.List;
import rbac.Session;

/**
 *
 * @author chin
 */
public interface IOrderController extends Remote {

    boolean createOrder(String sessionId, Order order) throws RemoteException;

    Order getOrder(String sessionId, String orderId) throws RemoteException;
    
    List<Order> getAllOrder(String sessionId) throws RemoteException;

    boolean updateOrder(String sessionId, Order newOrder) throws RemoteException;

    boolean deleteOrder(String SessionId, String orderId) throws RemoteException;

    boolean updateOrderStatus(String sessionId, String orderId, String newStatus)
            throws RemoteException;
}
