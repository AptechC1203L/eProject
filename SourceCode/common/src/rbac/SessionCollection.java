/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.rmi.AccessException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author chin
 */
public class SessionCollection {

    private List<Session> sessions;

    public SessionCollection() {
        sessions = new LinkedList<>();
    }

    /**
     * 
     * @param session
     * @return the new session's ID
     */
    public Session add(Session session) {
        String id = generateSessionId();
        Session newSession = new Session(id, session.getUser());
        sessions.add(newSession);
        return newSession;
    }
    
    public Session get(String sessionId) {
        for (Session s : sessions) {
            if (s.getSessionId().equals(sessionId))
                return s;
        }
        return null;
    }

    public void remove(String sessionId) {
        sessions.remove(get(sessionId));
    }

    private String generateSessionId() {
        return Integer.toString(sessions.size());
    }
}
