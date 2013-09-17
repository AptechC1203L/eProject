/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import businessentity.Order;
import java.rmi.Remote;

/**
 *
 * @author chin
 */
public interface IOrderEventListener extends Remote {
    void onOrderCreated(Order newOrder);

    void onOrderRemoved(Order removedOrder);

    void onOrderUpdated(Order updatedOrder);
}
