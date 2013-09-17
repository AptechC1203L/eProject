/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import rbac.User;
import java.rmi.Remote;

/**
 *
 * @author chin
 */
public interface IUserEventListener extends Remote {
    void onUserCreated(User newUser);

    void onUserRemoved(User removedUser);

    void onUserUpdated(User updatedUser);
}
