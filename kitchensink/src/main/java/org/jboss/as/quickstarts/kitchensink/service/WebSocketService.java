package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.data.TeamRepository;
import org.jboss.as.quickstarts.kitchensink.model.Person;
import org.jboss.as.quickstarts.kitchensink.model.Team;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

@ServerEndpoint(value = "/kitchen-socket")
@Stateful
public class WebSocketService {
    private final static Logger logger = Logger.getLogger(WebSocketService.class.getName());
    private static Queue<Session> peers = new ConcurrentLinkedDeque<>();

    @Inject
    private EntityManager em;

    @Inject
    private Event<Team> teamEventSrc;

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        peers.add(session);
        logger.info("New websocket session created");
    }

    @OnMessage
    public void message(Session session, String msg) throws IOException, EncodeException {
        logger.info("New websocket message received " + msg);
        logger.info("Current peers " + peers.size());

        for (Session s : peers) {
            System.out.println("Sending to client " + msg);
            if (s.isOpen()) s.getBasicRemote().sendObject(msg);
        }

        String[] mess = msg.split("::");
        updateTeam(Long.parseLong(mess[0]), Team.State.valueOf(mess[1]));
    }

    @Transactional
    private void updateTeam(long id, Team.State state) {
        Team t = em.find(Team.class, id);
        t.setState(state);
        em.merge(t);
        teamEventSrc.fire(t);
    }

    @OnError
    public void error(Session session, Throwable error) {
        error.printStackTrace();
        peers.remove(session);
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        peers.remove(session);
    }

    public void jmsMessage(String mess) {
        for (Session s : peers) {
            System.out.println("Sending to client " + mess);
            if (s.isOpen()) {
                try {
                    s.getBasicRemote().sendObject(mess);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] m = mess.split("::");
        updateTeam(Long.parseLong(m[0]), Team.State.valueOf(m[1]));
    }
}
