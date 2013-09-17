/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import rbac.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author chin
 */
public interface IUserEventListener extends Remote {
    
    void onUserCreated(User newUser) throws RemoteException;

    void onUserRemoved(User removedUser) throws RemoteException;

    void onUserUpdated(User updatedUser) throws RemoteException;
}
