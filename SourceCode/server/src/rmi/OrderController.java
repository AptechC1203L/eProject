/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import businessentity.Order;
import db.ConnectionFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rbac.Permission;
import rbac.SessionManager;

/**
 *
 * @author chin
 */
public class OrderController extends UnicastRemoteObject implements IOrderController {

    private SessionManager sessionManager;
    ConnectionFactory connectionFactory;
    LinkedList<Order> orders = new LinkedList<>();

    public OrderController(SessionManager sessionManager,
                             ConnectionFactory connectionFactory)
            throws RemoteException {
        super();
        this.sessionManager = sessionManager;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Order createOrder(String sessionId,  Order order) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("create", "order"));
        int size = this.orders.size();
        Order processedOrder = null; 
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO [Orders] " +
                     "(from, to, creator_id, status, weight, description, timestamp, due_date) " +
                     "OUTPUT INSERTED.id" +
                     "VALUES (N'?', N'?', ?, ?, ?, N'?', ?, ?)");
                ) {
            statement.setString(1, order.getSender());
            statement.setString(2, order.getReceiver());
            statement.setString(3, "me");
            statement.setString(4, order.getStatus());
            statement.setDouble(5, order.getWeight());
            statement.setString(6, "Blank description");
            statement.setTimestamp(7, new Timestamp(new Date().getTime()));
            statement.setTime(8, null);
            ResultSet output = statement.executeQuery();
            if (output.next()) {
                // Remember that OUTPUT clause above?
                int id = output.getInt(1);
                processedOrder = new Order(id,
                               order.getSender(),
                               order.getReceiver(),
                               order.getWeight(),
                               order.getDescription());
            }
        } catch (SQLException ex) {
            // Returns the null processedOrder
        }
        return processedOrder;
    }

    @Override
    public Order getOrder(String sessionId, int orderId) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("view", "order"));
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
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
            if (order.getOrderId() == newOrder.getOrderId()) {
                order = newOrder;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteOrder(String sessionId, int orderId) {
        return true;
    }

    @Override
    public boolean updateOrderStatus(String sessionId, int orderId, String newStatus) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("update", "order.status"));

        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
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
