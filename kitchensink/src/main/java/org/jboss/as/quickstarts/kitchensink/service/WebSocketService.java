package org.jboss.as.quickstarts.kitchensink.service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ServerEndpoint(value = "/kitchen-socket")
public class WebSocketService {
    private final static Logger logger = Logger.getLogger(WebSocketService.class.getName());

    private Set<Session> peers = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        peers.add(session);
        logger.info("New websocket session created");
    }

    @OnMessage
    public void message(Session session, String msg) throws IOException, EncodeException {
        logger.info("New websocket message received " + msg);
        for (Session s : peers) {
            if (s.isOpen()) s.getBasicRemote().sendObject(msg);
        }
    }

    @OnError
    public void error(Session session, Throwable error) {
        peers.remove(session);
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        peers.remove(session);
    }
}
