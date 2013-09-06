/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import rbac.Permission;
import rbac.Role;
import rbac.Session;
import rbac.SessionManager;
import rbac.User;

/**
 *
 * @author chin
 */
public class Authenticator extends UnicastRemoteObject implements IAuthenticator {

    private SessionManager sessionManager;
    public Authenticator(SessionManager sessionManager) throws RemoteException {
        super();
        this.sessionManager = sessionManager;
    }

    @Override
    public Session login(String username, String password) throws RemoteException {
        User chin = new User("chin", "Chin");
        
        Role admin = new Role("admin");
        ArrayList<Permission> permission = new ArrayList<>();
        permission.add(new Permission("create", "order"));
        permission.add(new Permission("view", "order"));
        permission.add(new Permission("update", "order"));
        permission.add(new Permission("remove", "order"));
        permission.add(new Permission("update", "order.status"));
        admin.setPermissions(permission);
        
        switch (username) {
            case "chin":
                Session session = new Session(
                    generateSessionId(),
                    chin,
                    new ArrayList<>(Arrays.asList(admin)));
                sessionManager.add(session);
                return session;
            default:
                return null;
        }
    }

    
    /**
     * Generate a random session ID.
     * 
     * @return a random MD5 string
     */
    private String generateSessionId() {
        return "chin-session";
    }

    @Override
    public void logout(String sessionId) throws RemoteException {
        sessionManager.remove(sessionId);
    }
}
