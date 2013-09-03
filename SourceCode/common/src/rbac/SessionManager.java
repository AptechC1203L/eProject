/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

/**
 *
 * @author chin
 */
public class SessionManager {
    private Session singletonSession;
    
    public boolean isAuthorized(String sessionId, Permission permission) {
        for (Role r : singletonSession.getRoles())
            for (Permission p : r.getPermissions())
                if (p.equals(permission))
                    return true;
        return false;
    }

    public void add(Session session) {
        singletonSession = session;
    }

    public void remove(String sessionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
