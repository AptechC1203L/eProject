/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author chin
 */
@Data
public class User implements Serializable {
    private String userId;
    private String name;
    private String honorific;
    private String about;
    private int phone;  

    public User(String userId, String name, String honorific, String about, int phone) {
        this.userId = userId;
        this.name = name;
        this.honorific = honorific;
        this.about = about;
        this.phone = phone;
    }
}
