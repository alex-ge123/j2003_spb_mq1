package com.qf.j2003.demo4;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息接收者
 * Created by jeffrey on 2020/9/18.
 */
public class ConsumerMessage {
    static String QUEUENAME="hellox";
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
        //        配置信道绑定的队列为持久化队列
        channel.queueDeclare(QUEUENAME,true,false,false,null);
        System.out.println("准备接收消息：。。。。");
        DefaultConsumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumer:------"+new String(body,"UTF-8"));
            }
        };
        /**
         * 参数1：侦听的队列
         * 参数2：是否自动提醒
         * 参数3：接受消息后回调执行的逻辑
         */
        channel.basicConsume(QUEUENAME,true,consumer);
        System.in.read();//使用标准输入中断
        channel.close();
        connection.close();
    }
}
