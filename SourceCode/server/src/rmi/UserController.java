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
import rbac.User;
/**
 *
 * @author kien
 */
public class UserController extends UnicastRemoteObject implements IUserController{
    private SessionManager sessionManager;
    LinkedList<User> users = new LinkedList<>();
    public UserController(SessionManager sessionManager) throws RemoteException{
        super();
        this.sessionManager = sessionManager;
    }

    @Override
    public User createUser(String sessionId, User user) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("create", "user"));
        int size = this.users.size();
        User processedUser = new User(user.getUserId(),
                               user.getName(),
                               user.getHonorific(),
                               user.getAbout(),
                               user.getPhone());
        this.users.add(processedUser);
        return processedUser;
    }

    @Override
    public User getUser(String sessionId, String userId) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("view", "user"));
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUser(String sessionId) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("view", "user"));
        return users;
    }

    @Override
    public boolean updateUser(String sessionId, User newUser) throws RemoteException {
        sessionManager.isAuthorizedWithSideEffect(
                sessionId,
                new Permission("update", "user"));

        for (User user : users) {
            if (user.getUserId().equals(newUser.getUserId())) {
                user = newUser;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(String SessionId, String userId) throws RemoteException {
        return true;
    }
    
    
}
