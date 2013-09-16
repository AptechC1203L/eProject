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
import rbac.SessionCollection;
import rbac.User;

/**
 *
 * @author chin
 */
public class Authenticator extends UnicastRemoteObject implements IAuthenticator {

    private SessionCollection sessionManager;
    public Authenticator(SessionCollection sessionManager) throws RemoteException {
        super();
        this.sessionManager = sessionManager;
    }

    @Override
    public Session login(String username, String password) throws RemoteException {
        
        Role admin = new Role("admin");
        ArrayList<Permission> admPerms = new ArrayList<>();
        admPerms.add(new Permission("create", "order"));
        admPerms.add(new Permission("view", "order"));
        admPerms.add(new Permission("update", "order"));
        admPerms.add(new Permission("remove", "order"));
        admPerms.add(new Permission("update", "order.status"));
        admPerms.add(new Permission("create", "user"));
        admPerms.add(new Permission("update", "user"));
        admPerms.add(new Permission("remove", "user"));
        admPerms.add(new Permission("view", "user"));
        admPerms.add(new Permission("update", "user"));
        admPerms.add(new Permission("remove", "user"));
        admin.setPermissions(admPerms);
        
        Role receptionist = new Role("receptionist");
        ArrayList<Permission> recPerms = new ArrayList<>();
        recPerms.add(new Permission("create", "order"));
        recPerms.add(new Permission("view", "order"));
        recPerms.add(new Permission("update", "order"));
        recPerms.add(new Permission("remove", "order"));
        receptionist.setPermissions(recPerms);
        
        Role deliverer = new Role("deliverer");
        ArrayList<Permission> delPerms = new ArrayList<>();
        delPerms.add(new Permission("view", "order"));
        delPerms.add(new Permission("update", "order.status"));
        deliverer.setPermissions(delPerms);
        
        User adm = new User("admin", "Admin", Arrays.asList(admin));
        User rep = new User("rep", "Receptionist", Arrays.asList(receptionist));
        User del = new User("deliverer", "Deliverer", Arrays.asList(deliverer));
        
        Session session = null;
        switch (username) {
            case "admin":
                return sessionManager.add(new Session(null, adm));
            case "receptionist":
                return sessionManager.add(new Session(null, rep));
            case "deliverer":
                return sessionManager.add(new Session(null, del));
            default:
                return null;
        }
    }

    @Override
    public void logout(String userId) throws RemoteException {
    }
}
