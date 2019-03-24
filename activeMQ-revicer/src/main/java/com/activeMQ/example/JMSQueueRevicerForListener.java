package com.activeMQ.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * activemq 消息接收端
 */
public class JMSQueueRevicerForListener {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.103:61616");

        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            // 这里也需要创建队列，表示监听这个队列，如果没有这个队列则会创建
            Destination destination = session.createQueue("TestQueue");
            // 创建一个消息接收人，接收制定队列的消息
            MessageConsumer consumer = session.createConsumer(destination);
            // 接收消息，正常这里需要进行格式判断，这里使用TextMessage进行举例说明
            // 这里采取循环接收，而不是接收一次就断开，这样表示为非阻塞
            MessageListener listener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println(((TextMessage)message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            };

            while(true){
                consumer.setMessageListener(listener);
                session.commit();
            }

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
