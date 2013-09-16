/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businessentity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import rbac.User;

/**
 *
 * @author chin
 */
@Data
@RequiredArgsConstructor
public class Order implements Serializable {
    final private int orderId;
    @NonNull private String sender;
    @NonNull private String receiver;
    @NonNull private double weight;
    @NonNull private String description;
    
    // Setup some default values to avoid NullPointerException
    private User createdBy = new User("-", "N/A");
    private User deliveredBy = new User("-", "N/A");
    private Date timestamp = new Date(0);
    private Date dueDate = new Date(0);
    private Date deliveredOn = new Date(0);
    private String status = "PENDING";
    
    /**
     * Copy constructor.
     * @param prototype
     */
    public Order(Order prototype) {
        this.orderId = prototype.orderId;
        this.sender = prototype.sender;
        this.receiver = prototype.receiver;
        this.weight = prototype.weight;
        this.description = prototype.description;
        this.createdBy = prototype.createdBy;
        this.timestamp = new Date(prototype.timestamp.getTime());
        this.dueDate = new Date(prototype.dueDate.getTime());
        this.deliveredOn = new Date(prototype.deliveredOn.getTime());
        this.status = prototype.status;
    }
    
    public double getCharge() {
        return weight * 0.5;
    }
}
