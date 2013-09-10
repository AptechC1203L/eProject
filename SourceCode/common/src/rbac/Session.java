/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author chin
 */
@Data
public class Session implements Serializable {
    private final String sessionId;
    private final User user;
    private final List<Role> roles;
    
    public List<Permission> getAllPermissions() {
        LinkedList<Permission> permissionList = new LinkedList<>();
        for (Role r : getRoles()) {
            for (Permission p : r.getPermissions()) {
                permissionList.add(p);
            }
        }
        return permissionList;
    }
}
