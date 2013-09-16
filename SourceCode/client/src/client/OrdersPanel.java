/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import businessentity.Order;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import rbac.Permission;
import rbac.Session;
import rmi.IOrderController;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import lombok.Data;

/**
 *
 * @author Louis DeRossi
 */
public class OrdersPanel extends javax.swing.JPanel {

    final private Session session;
    private OrderTableModel tableModel;
    final private IOrderController orderController;
    private Order currentOrderShown = null;
    private int currentTableModelRow = 0;
    private TableRowSorter<OrderTableModel> sorter;

    /**
     * Creates new form mani
     */
    public OrdersPanel(Session session, IOrderController orderController) throws RemoteException {
        initComponents();
        this.session = session;
        this.orderController = orderController;
        this.tableModel = new OrderTableModel();

        this.setupTable();
        this.setupPermissions();
        this.setupSearchBox();
    }

    private void setupSearchBox() throws RemoteException {
        sorter = new TableRowSorter<>(tableModel);
        orderTable.setRowSorter(sorter);
        
        searchBox.getDocument().addDocumentListener(
                new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                newFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                newFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                newFilter();
            }

            private void newFilter() {
                RowFilter<OrderTableModel, Object> rf = null;
                try {
                    rf = RowFilter.regexFilter(searchBox.getText());
                } catch (java.util.regex.PatternSyntaxException e) {
                    return;
                }
                sorter.setRowFilter(rf);
            }
        });
    }

    private void setupTable() throws RemoteException {
        List<Order> allOrders = orderController.getAllOrders(session.getSessionId());
        for (Order order : allOrders) {
            this.tableModel.add(order);
        }

        this.orderTable.setModel(tableModel);
        // Make the table react to row selection
        this.orderTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Show the first selected order in the side bar
                    int[] selection = orderTable.getSelectedRows();
                    final int index = orderTable.convertRowIndexToModel(selection[0]);
                    final Order selectedOrder = tableModel.get(index);
                    currentOrderShown = selectedOrder;
                    currentTableModelRow = index;

                    orderID.setText(selectedOrder.getOrderId());
                    orderFrom.setText(selectedOrder.getSender());
                    orderTo.setText(selectedOrder.getReceiver());
                    orderCharge.setText(Double.toString(selectedOrder.getWeight() * 0.5));
                    orderWeight.setText(Double.toString(selectedOrder.getWeight()));
                    createdByField.setText("chin");
                    deliveredByField.setText("chin");

                    // Update the status UI
                    DefaultComboBoxModel statuses = new DefaultComboBoxModel();
                    statuses.addElement("PENDING");
                    statuses.addElement("CONFIRMED");
                    statuses.addElement("DONE");
                    statuses.addElement("REJECTED");
                    status = new JComboBox(statuses);
                    status.setSelectedItem(selectedOrder.getStatus());

                    if (!selectedOrder.getStatus().equals("PENDING")) {
                        orderFrom.setEditable(false);
                        orderTo.setEditable(false);
                        orderWeight.setEditable(false);
                        orderCharge.setEditable(false);
                        cancelOrderButton.setEnabled(false);
                        descriptionText.setEditable(false);
                    }

                    status.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                String s = (String) status.getSelectedItem();
                                orderController.updateOrderStatus(
                                        session.getSessionId(),
                                        selectedOrder.getOrderId(),
                                        s);
                                selectedOrder.setStatus(s);
                                tableModel.fireTableRowsUpdated(index, index);
                            } catch (RemoteException ex) {
                                Utils.showErrorDialog(null, ex.getCause().getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private void setupPermissions() {
        @Data
        class ComponentPermissionTuple {

            final Component component;
            final Permission permission;
        }

        // rp = required permissions ; to ease typing and reading
        List<ComponentPermissionTuple> rp = new LinkedList<>();
        rp.add(new ComponentPermissionTuple(this.createOrderButton, new Permission("create", "order")));
        rp.add(new ComponentPermissionTuple(this.cancelOrderButton, new Permission("remove", "order")));
        rp.add(new ComponentPermissionTuple(this.orderFrom, new Permission("update", "order")));
        rp.add(new ComponentPermissionTuple(this.orderCharge, new Permission("update", "order")));
        rp.add(new ComponentPermissionTuple(this.orderTo, new Permission("update", "order")));
        rp.add(new ComponentPermissionTuple(this.orderWeight, new Permission("update", "order")));
        rp.add(new ComponentPermissionTuple(this.deliveredByField, new Permission("update", "order")));
        rp.add(new ComponentPermissionTuple(this.createdByField, new Permission("update", "order")));
        rp.add(new ComponentPermissionTuple(this.orderDueDate, new Permission("update", "order")));
        rp.add(new ComponentPermissionTuple(this.descriptionText, new Permission("update", "order")));
        rp.add(new ComponentPermissionTuple(this.status, new Permission("update", "order.status")));
        List<Permission> allPermissions = session.getAllPermissions();

        for (ComponentPermissionTuple pair : rp) {
            if (!allPermissions.contains(pair.permission)) {
                if (pair.component instanceof JTextComponent) {
                    ((JTextComponent) pair.component).setEditable(false);
                } else {
                    pair.component.setEnabled(false);
                }
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tablePanel = new javax.swing.JPanel();
        tableButtonPanel = new javax.swing.JPanel();
        createOrderButton = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        searchBox = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        detailsPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        orderID = new javax.swing.JLabel();
        charge = new javax.swing.JLabel();
        status = new javax.swing.JComboBox();
        status2 = new javax.swing.JLabel();
        orderTo = new javax.swing.JTextField();
        deliveredBy = new javax.swing.JLabel();
        orderFrom = new javax.swing.JTextField();
        to = new javax.swing.JLabel();
        from = new javax.swing.JLabel();
        createBy = new javax.swing.JLabel();
        orderCharge = new javax.swing.JTextField();
        weight = new javax.swing.JLabel();
        orderWeight = new javax.swing.JTextField();
        dueDate = new javax.swing.JLabel();
        deliveredByField = new javax.swing.JTextField();
        createdByField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionText = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        orderDueDate = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        buttonsPane = new javax.swing.JPanel();
        cancelOrderButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        tablePanel.setLayout(new javax.swing.BoxLayout(tablePanel, javax.swing.BoxLayout.Y_AXIS));

        tableButtonPanel.setMaximumSize(new java.awt.Dimension(2147483647, 25));
        tableButtonPanel.setLayout(new javax.swing.BoxLayout(tableButtonPanel, javax.swing.BoxLayout.LINE_AXIS));

        createOrderButton.setText("Create Order");
        createOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createOrderButtonActionPerformed(evt);
            }
        });
        tableButtonPanel.add(createOrderButton);
        tableButtonPanel.add(filler1);

        searchBox.setMaximumSize(new java.awt.Dimension(2000, 300));
        searchBox.setMinimumSize(new java.awt.Dimension(200, 19));
        searchBox.setPreferredSize(new java.awt.Dimension(200, 19));
        searchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBoxActionPerformed(evt);
            }
        });
        tableButtonPanel.add(searchBox);

        tablePanel.add(tableButtonPanel);

        orderTable.setAutoCreateRowSorter(true);
        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Order ID", "Create At", "Due In", "Status"
            }
        ));
        orderTable.setAlignmentX(1.0F);
        jScrollPane1.setViewportView(orderTable);

        tablePanel.add(jScrollPane1);

        add(tablePanel);

        detailsPanel.setMinimumSize(new java.awt.Dimension(250, 19));
        detailsPanel.setPreferredSize(new java.awt.Dimension(350, 676));
        detailsPanel.setLayout(new javax.swing.BoxLayout(detailsPanel, javax.swing.BoxLayout.Y_AXIS));

        infoPanel.setAlignmentY(1.0F);
        infoPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        infoPanel.setLayout(new java.awt.GridBagLayout());

        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        infoPanel.add(jSeparator1, gridBagConstraints);

        orderID.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        orderID.setText("Order #24");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        infoPanel.add(orderID, gridBagConstraints);

        charge.setText("Charge:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(charge, gridBagConstraints);

        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(status, gridBagConstraints);

        status2.setText("Status");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(status2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(orderTo, gridBagConstraints);

        deliveredBy.setText("Delivered By:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(deliveredBy, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(orderFrom, gridBagConstraints);

        to.setText("To:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(to, gridBagConstraints);

        from.setText("From:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(from, gridBagConstraints);

        createBy.setText("Created By:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(createBy, gridBagConstraints);

        orderCharge.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(orderCharge, gridBagConstraints);

        weight.setText("Weight:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(weight, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(orderWeight, gridBagConstraints);

        dueDate.setText("Due Date:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(dueDate, gridBagConstraints);

        deliveredByField.setEnabled(false);
        deliveredByField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deliveredByFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(deliveredByField, gridBagConstraints);

        createdByField.setEnabled(false);
        createdByField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createdByFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(createdByField, gridBagConstraints);

        jLabel1.setText("Description:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        infoPanel.add(jLabel1, gridBagConstraints);

        descriptionText.setColumns(20);
        descriptionText.setRows(5);
        descriptionText.setMinimumSize(new java.awt.Dimension(0, 200));
        descriptionText.setPreferredSize(new java.awt.Dimension(220, 200));
        jScrollPane2.setViewportView(descriptionText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(jScrollPane2, gridBagConstraints);

        jLabel2.setText("gram");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        infoPanel.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        infoPanel.add(orderDueDate, gridBagConstraints);

        jLabel3.setText("USD");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        infoPanel.add(jLabel3, gridBagConstraints);

        detailsPanel.add(infoPanel);

        buttonsPane.setMaximumSize(new java.awt.Dimension(32767, 35));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10);
        flowLayout1.setAlignOnBaseline(true);
        buttonsPane.setLayout(flowLayout1);

        cancelOrderButton.setText("Cancel Order");
        cancelOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOrderButtonActionPerformed(evt);
            }
        });
        buttonsPane.add(cancelOrderButton);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        buttonsPane.add(saveButton);

        detailsPanel.add(buttonsPane);
        detailsPanel.add(filler2);

        add(detailsPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void createOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOrderButtonActionPerformed
        Order createdOrder = new CreateOrderDialog(null)
                .showDialog(session, orderController);
        if (createdOrder != null) {
            this.tableModel.add(createdOrder);
        }
    }//GEN-LAST:event_createOrderButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        double weight = Double.parseDouble(orderWeight.getText());
        Order editedOrder = new Order(
                currentOrderShown.getOrderId(),
                orderFrom.getText(),
                orderTo.getText(),
                weight,
                descriptionText.getText());

        boolean isOk = false;
        try {
            isOk = orderController.updateOrder(session.getSessionId(), editedOrder);
        } catch (RemoteException ex) {
        } finally {
            if (isOk) {
                Utils.showInfoDialog(this, "Done!");
                this.tableModel.set(currentTableModelRow, editedOrder);
            } else {
                Utils.showErrorDialog(this, "Cannot update the order!");
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void deliveredByFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deliveredByFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deliveredByFieldActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void createdByFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createdByFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createdByFieldActionPerformed

    private void cancelOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOrderButtonActionPerformed
        boolean isOk = false;
        try {
            String sessionId = this.session.getSessionId();
            String oderId = currentOrderShown.getOrderId();
            isOk = this.orderController.deleteOrder(sessionId, oderId);
        } catch (RemoteException ex) {
            Logger.getLogger(OrdersPanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (isOk) {
                Utils.showInfoDialog(this, "Done");
                tableModel.remove(currentTableModelRow);
            } else {
                Utils.showErrorDialog(this, "Can't delete order!");
            }
        }
    }//GEN-LAST:event_cancelOrderButtonActionPerformed

    private void searchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPane;
    private javax.swing.JButton cancelOrderButton;
    private javax.swing.JLabel charge;
    private javax.swing.JLabel createBy;
    private javax.swing.JButton createOrderButton;
    private javax.swing.JTextField createdByField;
    private javax.swing.JLabel deliveredBy;
    private javax.swing.JTextField deliveredByField;
    private javax.swing.JTextArea descriptionText;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JLabel dueDate;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel from;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField orderCharge;
    private javax.swing.JSlider orderDueDate;
    private javax.swing.JTextField orderFrom;
    private javax.swing.JLabel orderID;
    private javax.swing.JTable orderTable;
    private javax.swing.JTextField orderTo;
    private javax.swing.JTextField orderWeight;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField searchBox;
    private javax.swing.JComboBox status;
    private javax.swing.JLabel status2;
    private javax.swing.JPanel tableButtonPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JLabel to;
    private javax.swing.JLabel weight;
    // End of variables declaration//GEN-END:variables
}
