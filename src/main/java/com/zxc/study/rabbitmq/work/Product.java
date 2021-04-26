package com.zxc.study.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Product {

    public static ConnectionFactory connectionFactory = new ConnectionFactory();
    static{
        ;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        connectionFactory.setHost("121.37.143.53");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/order");


        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection("生产者");
            channel = connection.createChannel();

            String queueName = "queue1";

            channel.queueDeclare(queueName,false,false,false,null);

            String message = "hello word!";

            channel.basicPublish("",queueName,null,message.getBytes());

            System.out.println("send ok!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel!=null && channel.isOpen()){
                channel.close();
            }
            if (connection!=null && connection.isOpen()){
                connection.close();
            }

        }





    }




}
