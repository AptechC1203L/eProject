/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businessentity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NonNull;
import rbac.User;

/**
 *
 * @author chin
 */
@Data
public class Order implements Serializable {
    final private String orderId;
    @NonNull private String sender;
    @NonNull private String receiver;
    @NonNull private double weight;
    @NonNull private String description;
    private User createdBy;
    private User deliveredBy;
    private Date timestamp;
    private Date dueDate;
    private Date deliveredOn;
    private String status = "PENDING";
    
    public double getCharge() {
        return weight * 0.5;
    }
}
