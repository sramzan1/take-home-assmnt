package main.java.service1.service;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_S1_TO_S2 = "queue.s1.to.s2";
    public static final String QUEUE_S2_TO_S1 = "queue.s2.to.s1";
    public static final String EXCHANGE = "microservices.exchange";
    public static final String ROUTING_KEY_S1_TO_S2 = "s1.to.s2";
    public static final String ROUTING_KEY_S2_TO_S1 = "s2.to.s1";

    @Bean
    public Queue queueS1ToS2() {
        return new Queue(QUEUE_S1_TO_S2);
    }

    @Bean
    public Queue queueS2ToS1() {
        return new Queue(QUEUE_S2_TO_S1);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingS1ToS2(Queue queueS1ToS2, TopicExchange exchange) {
        return BindingBuilder.bind(queueS1ToS2).to(exchange).with(ROUTING_KEY_S1_TO_S2);
    }

    @Bean
    public Binding bindingS2ToS1(Queue queueS2ToS1, TopicExchange exchange) {
        return BindingBuilder.bind(queueS2ToS1).to(exchange).with(ROUTING_KEY_S2_TO_S1);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}