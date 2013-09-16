/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import businessentity.Order;
import db.ConnectionFactory;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rbac.Permission;
import rbac.SessionCollection;
import rbac.User;

/**
 *
 * @author chin
 */
public class OrderController extends Controller implements IOrderController {

    public OrderController(SessionCollection sessionManager,
                             ConnectionFactory connectionFactory)
            throws RemoteException {
        super(sessionManager, connectionFactory);
    }

    @Override
    public Order createOrder(String sessionId,  Order order) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("create", "order"));
        
        Order processedOrder = null; 
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO [Orders] " +
                     "([from], [to], [creator_id], [status], [weight], [description], [timestamp], [due_date]) " +
                     "OUTPUT INSERTED.id VALUES " +
                     "(?, ?, ?, ?, ?, ?, ?, ?)");
                ) {
            statement.setString(1, order.getSender());
            statement.setString(2, order.getReceiver());
            statement.setString(3, "me");
            statement.setString(4, order.getStatus());
            statement.setDouble(5, order.getWeight());
            statement.setString(6, "Blank description");
            statement.setTimestamp(7, new Timestamp(new Date().getTime()));
            statement.setTimestamp(8, new Timestamp(new Date().getTime()));
            
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
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return processedOrder;
        }
    }

    @Override
    public Order getOrder(String sessionId, int orderId) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("view", "order"));
        
        Order order = null;
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                "SELECT * from [Orders] WHERE id = ?")) {
            statement.setInt(1, orderId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                order = deserializeOrder(result);
            }
        } catch (SQLException ex) {
            // FIXME What to do here?
        } finally {
            return order;
        }
    }

    @Override
    public boolean updateOrder(String sessionId, int orderId, Order newOrder) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("update", "order"));
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM [Orders] WHERE id = ?",
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.setInt(1, orderId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                result.updateString("from", newOrder.getSender());
                result.updateString("to", newOrder.getReceiver());
                result.updateDouble("weight", newOrder.getWeight());
                result.updateString("description", newOrder.getDescription());
                
                // TODO Other properties
                result.updateRow();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteOrder(String sessionId, int orderId) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("remove", "order"));
        
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                "DELETE from [Orders] where id = ?")) {
            statement.setInt(1, orderId);
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean updateOrderStatus(String sessionId, int orderId, String newStatus) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("update", "order.status"));
        return true;
    }

    @Override
    public List<Order> getAllOrders(String sessionId) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("view", "order"));
        List<Order> orders = new LinkedList<>();
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                "SELECT * from [Orders]")) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                orders.add(deserializeOrder(result));
            }
        } catch (SQLException ex) {
            // FIXME What to do here?
        } finally {
            return orders;
        }
    }

    @Override
    public List<Order> getActiveOrders(String sessionId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Order deserializeOrder(ResultSet result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
