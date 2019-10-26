package org.jboss.as.quickstarts.kitchensink.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup",
                        propertyValue = "java:jboss/exported/jms/queue/test"),
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        })
public class JMSService implements MessageListener {

    @Inject
    private WebSocketService webSocketService;

    @Override
    public void onMessage(Message message) {
        System.out.println("Message received");
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                webSocketService.jmsMessage(textMessage.getText());
            }
        } catch (JMSException e) {
            // TODO: Do smth
        }
    }
}
