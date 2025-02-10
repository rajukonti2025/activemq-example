package com.mq.consumer;
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.mq.logger.GroupLogger;

public class GroupConsumer {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "GroupQueue";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(queue);

        while (true) {
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("Received: " + text);

                // Filter messages based on operation type
                if (text.contains("Operation:CREATE")) {
                    System.out.println("Processing CREATE operation...");
                    // Add  CREATE operation logic here
                } else if (text.contains("Operation:DELETE")) {
                    System.out.println("Processing DELETE operation...");
                    // Add  DELETE operation logic here
                }

                // Log the message
                GroupLogger.log("Consumed: " + text);
            } else {
                break;
            }
        }

        consumer.close();
        session.close();
        connection.close();
    }
}
