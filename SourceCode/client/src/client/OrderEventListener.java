/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import businessentity.Order;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author chin
 */
public class OrderEventListener extends UnicastRemoteObject implements rmi.IOrderEventListener {
    private final OrderTableModel tableModel;

    OrderEventListener(OrderTableModel tableModel) throws RemoteException {
        this.tableModel = tableModel;
    }
    
    @Override
    public void onOrderCreated(Order newOrder) throws RemoteException {
        tableModel.add(newOrder);
    }

    @Override
    public void onOrderRemoved(Order removedOrder) throws RemoteException {
        int index = tableModel.findById(removedOrder.getOrderId());
        System.out.println(index);
        tableModel.remove(index);
    }

    @Override
    public void onOrderUpdated(Order updatedOrder) throws RemoteException {
        int index = tableModel.findById(updatedOrder.getOrderId());
        tableModel.set(index, updatedOrder);
    }
}
