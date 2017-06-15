package io.snowdrop.narayana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Service to send messages to the JMS queue.
 *
 * @author <a href="mailto:gytis@redhat.com">Gytis Trikleris</a>
 */
@Service
public class MessagesService {

    private static final String QUEUE_NAME = "quickstart-messages";

    private final List<String> messages;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public MessagesService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.messages = new LinkedList<>();
    }

    @JmsListener(destination = QUEUE_NAME)
    public void onMessage(String message) {
        messages.add(message);
    }

    @Transactional
    public void send(String message) {
        jmsTemplate.convertAndSend(QUEUE_NAME, message);
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

}
