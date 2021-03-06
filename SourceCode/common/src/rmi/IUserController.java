/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import rbac.User;

/**
 *
 * @author chin
 */
public interface IUserController extends Remote {

    void createUser(String sessionId, User user) throws RemoteException;

    User getUser(String sessionId, String username) throws RemoteException;
    
    List<User> getAllUsers(String sessionId) throws RemoteException;

    boolean updateUser(String sessionId, String username, User newUser) throws RemoteException;

    boolean deleteUser(String SessionId, String username) throws RemoteException;
    
    void addUserEventListener(IUserEventListener listener) throws RemoteException;
}
