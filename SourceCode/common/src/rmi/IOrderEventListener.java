/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import businessentity.Order;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author chin
 */
public interface IOrderEventListener extends Remote {
    
    void onOrderCreated(Order newOrder) throws RemoteException;

    void onOrderRemoved(Order removedOrder) throws RemoteException;

    void onOrderUpdated(Order updatedOrder) throws RemoteException;
}
