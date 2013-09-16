/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.io.Serializable;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
    
    public User(User prototype) {
        this.userId = prototype.userId;
        this.name = prototype.name;
        this.aboutMe = prototype.aboutMe;
        this.phone = prototype.phone;
    }
}
