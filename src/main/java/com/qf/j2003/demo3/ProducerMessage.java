package com.qf.j2003.demo3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息发送者
 * Created by jeffrey on 2020/9/18.
 */
public class ProducerMessage {

    public static void main(String[] args) throws IOException, TimeoutException {
//        1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);//此端口为编程端口 ，15672是web访问端口
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
//        2、创建连接
        Connection connection = factory.newConnection();
//        3、创建信道
        Channel channel = connection.createChannel();
        channel.basicQos(1);//确保只发送1个消息（同时）
        for (int i=0;i<10;i++) {
//        4、创建消息
            String msg = "this is a rab4bitmq's messages!---"+i;
//        5、发送消息
            channel.basicPublish("", "hello", null, msg.getBytes());
            System.out.println("------producer:"+ msg);
        }
//        6、释放资源
        channel.close();
        connection.close();
    }
}
