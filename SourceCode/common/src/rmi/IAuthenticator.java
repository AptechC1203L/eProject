/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import rbac.Session;
/**
 *
 * @author chin
 */
public interface IAuthenticator extends Remote {
    /**
     * Lets a legitimate user login using username and password.
     * 
     * @param username
     * @param password
     * @return a Session object
     * @throws RemoteException
     */
    Session login(String username, String password) throws RemoteException;
    
    /***
     * Stops an ongoing session.
     * 
     * @param sessionId
     * @throws RemoteException 
     */
    void logout(String sessionId) throws RemoteException;
}
