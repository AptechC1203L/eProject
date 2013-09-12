/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import businessentity.Order;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import rbac.Permission;
import rbac.SessionManager;

/**
 *
 * @author chin
 */
public class OrderController extends UnicastRemoteObject implements IOrderController {

    private SessionManager sessionManager;
    LinkedList<Order> orders = new LinkedList<>();

    public OrderController(SessionManager sessionManager) throws RemoteException {
        super();
        this.sessionManager = sessionManager;
    }

    @Override
    public Order createOrder(String sessionId,  Order order) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("create", "order"));
        int size = this.orders.size();
        Order processedOrder = new Order("ORDER" + Integer.toString(size),
                               order.getSender(),
                               order.getReceiver(),
                               order.getWeight(),
                               order.getProfile());
        this.orders.add(processedOrder);
        return processedOrder;
    }

    @Override
    public Order getOrder(String sessionId, String orderId) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("view", "order"));
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public boolean updateOrder(String sessionId, Order newOrder) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("update", "order"));

        for (Order order : orders) {
            if (order.getOrderId().equals(newOrder.getOrderId())) {
                order = newOrder;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteOrder(String sessionId, String orderId) {
        return true;
    }

    @Override
    public boolean updateOrderStatus(String sessionId, String orderId, String newStatus) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("update", "order.status"));

        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Order> getAllOrders(String sessionId) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("view", "order"));
        return orders;
    }
}
