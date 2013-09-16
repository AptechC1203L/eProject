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

    User createUser(String sessionId, User user) throws RemoteException;

    User getUser(String sessionId, String username) throws RemoteException;
    
    List<User> getAllUsers(String sessionId) throws RemoteException;

    boolean updateUser(String sessionId, User newUser) throws RemoteException;

    boolean deleteUser(String SessionId, String username) throws RemoteException;
}
