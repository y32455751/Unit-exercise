package com.activeMQ.example.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * activemq 消息接收端
 */
public class JMSQueueRevicerForWhile {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://39.107.226.30:61616");

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
            while(true){
                TextMessage message = (TextMessage)consumer.receive();
                System.out.println(message.getText());
                session.commit();// 每次接收之后必须提交，不然下一次的消息无法进入，提交表示本次消息消费完毕。
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
