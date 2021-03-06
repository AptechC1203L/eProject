/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.ConnectionFactory;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rbac.Permission;
import rbac.Role;
import rbac.SessionCollection;
import rbac.User;

/**
 *
 * @author chin
 */
public class UserController extends Controller implements IUserController {

    List<IUserEventListener> listeners = new LinkedList<>();
    
    public UserController(SessionCollection sessionManager,
            ConnectionFactory connectionFactory)
            throws RemoteException {
        super(sessionManager, connectionFactory);
    }

    @Override
    public void createUser(String sessionId, User user) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("create", "user"));
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO [Users] " +
                    "(username, name, honorific, about_me, phone) " +
                    "VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getName());
            statement.setString(3, "Mr");
            statement.setString(4, "Just me");
            statement.setString(5, "1234");
            int count = statement.executeUpdate();
            
            if (count > 0) {
                for (IUserEventListener listener : listeners) {
                    listener.onUserCreated(user);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User getUser(String sessionId, String username) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("view", "user"));

        User user = null;
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                "SELECT * from [Users] WHERE username = ?")) {
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                user = deserializeUser(result, null);
            }
        } catch (SQLException ex) {
            // FIXME What to do here?
        } finally {
            return user;
        }
    }

    @Override
    public List<User> getAllUsers(String sessionId) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("view", "user"));

        List<User> users = new LinkedList<>();
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                "SELECT * from [Users]")) {

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                users.add(deserializeUser(result, null));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            return users;
        }
    }

    @Override
    public boolean updateUser(String sessionId, String username, User newUser) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("update", "user"));
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM [Users] WHERE username = ?",
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                result.updateString("name", newUser.getName());
                // TODO Other properties
                result.updateRow();
                
                for (IUserEventListener listener : listeners) {
                    // TODO This implies having the view-user permission
                    listener.onUserUpdated(getUser(sessionId, username));
                }
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteUser(String sessionId, String username) throws RemoteException {
        sessionManager.get(sessionId).getUser().isAuthorizedThowsException(
                new Permission("remove", "user"));
        
        User user = getUser(sessionId, username);
        
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM [Users] WHERE username = ?")) {
            statement.setString(1, username);
            int count = statement.executeUpdate();
            if (count > 0) {
                for (IUserEventListener listener : listeners) {
                    // TODO This implies having the view-user permission
                    listener.onUserRemoved(user);
                }
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private User deserializeUser(ResultSet row, List<Role> roles) throws SQLException {
        String username = row.getString("username");
        String name = row.getString("name");
        String honorific = row.getString("honorific");
        String about_me = row.getString("about_me");
        String phone = row.getString("phone");

        User user = new User(username, name, new LinkedList<Role>());
        user.setPhone(phone);
        user.setAboutMe(about_me);
        user.setHonorific(honorific);

        return user;
    }

    @Override
    public void addUserEventListener(IUserEventListener listener) {
        listeners.add(listener);
    }
}
