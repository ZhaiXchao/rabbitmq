package com.zxc.study.rabbitmq.work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {


    public static ConnectionFactory connectionFactory = new ConnectionFactory();

    public static void main(String[] args) throws IOException, TimeoutException {
        connectionFactory.setHost("121.37.143.53");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/order");


        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection("消费者");
            channel = connection.createChannel();

            String queueName = "queue1";

            DeliverCallback deliverCallback = new DeliverCallback() {
                public void handle(String consumerTag, Delivery message) throws IOException {
                    System.out.println(new String(message.getBody(),"UTF-8"));

                }
            };
            channel.basicConsume(queueName, true, deliverCallback, new CancelCallback() {
                public void handle(String consumerTag) throws IOException {
                    System.out.println(consumerTag);
                    System.out.println("接收完成！");
                }
            });

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
