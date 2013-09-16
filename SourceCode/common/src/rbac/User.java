/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import java.rmi.AccessException;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author chin
 */
@Data
@RequiredArgsConstructor
public class User implements Serializable {
    final private String userId;
    @NonNull private String name;
    private String aboutMe;
    private String phone;
    @NonNull private List<Role> roles;
    
    public User(User prototype) {
        this.userId = prototype.userId;
        this.name = prototype.name;
        this.aboutMe = prototype.aboutMe;
        this.phone = prototype.phone;
    }
    
    public List<Permission> getAllPermissions() {
        LinkedList<Permission> permissionList = new LinkedList<>();
        for (Role r : getRoles()) {
            for (Permission p : r.getPermissions()) {
                permissionList.add(p);
            }
        }
        return permissionList;
    }
    
    /**
     * Check if the user has a certain permission.
     */
    public boolean isAuthorized(Permission permission) {
        List<Permission> allPermissions = getAllPermissions();
        return allPermissions.contains(permission);
    }

    /**
     * Similar to isAuthorized but returns nothing and throws an
     * AccessException on unauthorization.
     *
     * Mostly for using with remote RMI methods.
     *
     * @throws AccessException
     */
    public void isAuthorizedThowsException(Permission permission)
            throws AccessException {
        if (!isAuthorized(permission)) {
            throw new AccessException(String.format(
                    "You can't %s %s",
                    permission.getAction(),
                    permission.getTarget()));
        }
    }
}
