/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author chin
 */
@Data
public class Order implements Serializable {
    final private String orderId;
    @NonNull private String sender;
    @NonNull private String receiver;
    private String status = "PENDING";
}
