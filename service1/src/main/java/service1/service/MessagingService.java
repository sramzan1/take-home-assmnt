package main.java.service1.service;

import main.java.service1.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Service
public class MessagingService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //  initial "ping"
    @PostConstruct
    public void init() {
        Message initialPing = new Message("ping", "Service-1");
        System.out.println("S1 sending initial: " + initialPing);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_S1_TO_S2,
                initialPing
        );
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_S2_TO_S1)
    public void receiveMessage(Message message) {
        System.out.println("S1 received: " + message);
        if ("ping".equals(message.getContent())) {
            Message pongMessage = new Message("pong", "Service-1");
            System.out.println("S1 sending: " + pongMessage);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_S1_TO_S2,
                    pongMessage
            );
            try {
                System.out.println("S1 waiting for 10 seconds...");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Message pingMessage = new Message("ping", "Service-1");
            System.out.println("S1 sending: " + pingMessage);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_S1_TO_S2,
                    pingMessage
            );
        }
    }
}
