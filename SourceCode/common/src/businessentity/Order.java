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
    
    // Setup some default values to avoid NullPointerException
    private User createdBy = new User("-", "N/A");
    private User deliveredBy = new User("-", "N/A");
    private Date timestamp = new Date(0);
    private Date dueDate = new Date(0);
    private Date deliveredOn = new Date(0);
    private String status = "PENDING";
    
    public double getCharge() {
        return weight * 0.5;
    }
}
