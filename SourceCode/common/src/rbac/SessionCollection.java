/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbac;

import java.rmi.AccessException;
import java.util.List;

/**
 *
 * @author chin
 */
public class SessionCollection {

    private List<Session> sessions;

    /**
     * 
     * @param session
     * @return the new session's ID
     */
    public String add(Session session) {
        String id = generateSessionId();
        sessions.add(new Session(id, session.getUser()));
        return id;
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
