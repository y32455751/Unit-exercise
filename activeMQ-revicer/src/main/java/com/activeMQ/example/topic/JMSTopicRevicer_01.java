package com.activeMQ.example.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * activemq 消息接收端
 */
public class JMSTopicRevicer_01 {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://39.107.226.30:61616");

        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            // 这里也需要创建队列，表示监听这个队列，如果没有这个队列则会创建
            Destination destination = session.createTopic("TestTopic");
            // 创建一个消息接收人，接收制定队列的消息
            MessageConsumer consumer = session.createConsumer(destination);
            // 接收消息，正常这里需要进行格式判断，这里使用TextMessage进行举例说明
            TextMessage message = (TextMessage)consumer.receive();

            System.out.println(message.getText());

            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
