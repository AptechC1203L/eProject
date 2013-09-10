/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entity.Order;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chin
 */
public class OrderTableModel extends AbstractTableModel {

    private LinkedList<Order> orderList = new LinkedList<>();
    
    public void add(Order order) {
        this.orderList.add(order);
        int size = orderList.size() - 1;
        System.out.println(size);
        fireTableRowsInserted(size, size);
    }
    
    public Order get(int index) {
        return orderList.get(index);
    }
    
    @Override
    public int getRowCount() {
        return orderList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        List<String> columnNames = Arrays.asList("ID", "Status");
        return columnNames.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orderList.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return order.getOrderId();
            case 1:
                return order.getStatus();
            default:
                return null;
        }
    }    
}
