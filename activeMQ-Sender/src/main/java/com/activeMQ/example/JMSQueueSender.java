package com.activeMQ.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * activeMQ 消息发送端
 */
public class JMSQueueSender {

    public static void main(String[] args) {
        // 从连接工厂中获得连接，activemq使用tcp协议，默认传输端口为61616
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.103:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            // 获得本次连接会话
            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            // 创建队列，参数为队列名称,表示监听这个队列，如果没有这个队列则会创建
            Destination destination = session.createQueue("TestQueue");
            // 创建信息发送者，针对某个队列进行发送，参数为队列
            MessageProducer producer = session.createProducer(destination);
            // 创建被发送的信息，参数为信息，信息为多种格式，这里使用TextMessage作为举例
            TextMessage textMessage = session.createTextMessage("Test Message");
            // 发送信息
            producer.send(textMessage);
            // 提交上面的操作
            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
