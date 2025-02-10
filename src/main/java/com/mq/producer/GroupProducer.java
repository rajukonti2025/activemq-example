package com.mq.producer;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.mq.logger.GroupLogger;

public class GroupProducer {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "GroupQueue";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer producer = session.createProducer(queue);

        // Creating a message when a group is created
        String groupId = "groupId123";
        String parentGroupId = "parentGroupId456";
        String operation = "CREATE";
        String timestamp = new Date().toString();

        String message = String.format("GroupID:%s, ParentGroupID:%s, Operation:%s, Timestamp:%s",
                groupId, parentGroupId, operation, timestamp);

        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);

        // Log the message
        GroupLogger.log("Produced: " + message);

        producer.close();
        session.close();
        connection.close();
    }
}
