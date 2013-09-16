/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.ConnectionFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rbac.SessionManager;

/**
 *
 * @author chin
 */
public class Controller extends UnicastRemoteObject {

    protected final SessionManager sessionManager;
    protected final ConnectionFactory connectionFactory;

    public Controller(SessionManager sessionManager,
            ConnectionFactory connectionFactory)
            throws RemoteException {
        this.sessionManager = sessionManager;
        this.connectionFactory = connectionFactory;
    }
    
}
