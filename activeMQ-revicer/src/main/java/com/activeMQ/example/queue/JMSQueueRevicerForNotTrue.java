package com.activeMQ.example.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * activemq 消息接收端
 */
public class JMSQueueRevicerForNotTrue {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://39.107.226.30:61616");

        Connection connection = null;
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            // 第一个参数决定session是否有事务性，类似于数据库事务性，有提交，回滚
            // session必须执行commit才能成功的消费，否则消息将会被重复消费
            // 当参数为false的时候，消息在接收之后直接变成为已消费
            /*
                （优先级低于第一个参数）
                第二个参数：
                    AUTO_ACKNOWLEDGE：表示所有接收的将会被自动确认。
                    CLIENT_ACKNOWLEDGE：表示消息需要调用message.acknowledge();来进行确认
                    DUPS_OK_ACKNOWLEDGE：延迟确认消息，有可能会发生重复消费，
             */
            Session session = connection.createSession(Boolean.FALSE,Session.CLIENT_ACKNOWLEDGE);
            // 这里也需要创建队列，表示监听这个队列，如果没有这个队列则会创建
            Destination destination = session.createQueue("TestQueue");
            // 创建一个消息接收人，接收制定队列的消息
            MessageConsumer consumer = session.createConsumer(destination);
            // 接收消息，正常这里需要进行格式判断，这里使用TextMessage进行举例说明
            for(int i=0;i<10;i++) {
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println(message.getText());
                /*
                    在消息循环接收的过程中，consumer会把所有已经接收到的消息都放到缓存中等待确认
                    当调用确认的时候，将会把缓存中所有的消息一起确认
                    在运行第二次的时候，就只有消息9会被再次消费，但是好像不会确认
                 */
                if(i==8)    // 这里会把8和8之前所有的消息统一进行确认
                message.acknowledge();
            }

//            session.commit();
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
