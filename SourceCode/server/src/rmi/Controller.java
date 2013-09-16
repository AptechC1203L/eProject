/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import db.ConnectionFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rbac.SessionCollection;

/**
 *
 * @author chin
 */
public class Controller extends UnicastRemoteObject {

    protected final SessionCollection sessionManager;
    protected final ConnectionFactory connectionFactory;

    public Controller(SessionCollection sessionManager,
            ConnectionFactory connectionFactory)
            throws RemoteException {
        this.sessionManager = sessionManager;
        this.connectionFactory = connectionFactory;
    }
    
}
