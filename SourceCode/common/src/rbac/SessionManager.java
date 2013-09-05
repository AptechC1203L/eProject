/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.rmi.AccessException;

/**
 *
 * @author chin
 */
public class SessionManager {

    private Session singletonSession;

    /**
     * Check if a session has a certain permission.
     */
    public boolean isAuthorized(String sessionId, Permission permission) {
        for (Role r : singletonSession.getRoles()) {
            for (Permission p : r.getPermissions()) {
                if (p.equals(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    
    /**
     * Similar to isAuthorized but returns nothing and throws an
     * AccessException on failure.
     * 
     * Mostly for using with remote RMI methods.
     * 
     * @throws AccessException
     */
    public void isAuthorizedWithSideEffect(
            String sessionId,
            Permission permission) throws AccessException {
        if (!isAuthorized(sessionId, permission)) {
            throw new AccessException(String.format(
                    "You can't %s %s",
                    permission.getAction(),
                    permission.getTarget()));
        }
    }

    public void add(Session session) {
        singletonSession = session;
    }

    public void remove(String sessionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
