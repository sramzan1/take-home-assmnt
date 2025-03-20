package service2.service

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val QUEUE_S1_TO_S2 = "queue.s1.to.s2"
        const val QUEUE_S2_TO_S1 = "queue.s2.to.s1"
        const val EXCHANGE = "microservices.exchange"
        const val ROUTING_KEY_S1_TO_S2 = "s1.to.s2"
        const val ROUTING_KEY_S2_TO_S1 = "s2.to.s1"
    }


    @Bean
    fun queueS1ToS2(): Queue {
        return Queue(QUEUE_S1_TO_S2)
    }

    @Bean
    fun queueS2ToS1(): Queue {
        return Queue(QUEUE_S2_TO_S1)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(EXCHANGE)
    }

    @Bean
    fun bindingS1ToS2(queueS1ToS2: Queue, exchange: TopicExchange): Binding =
            BindingBuilder.bind(queueS1ToS2).to(exchange).with(ROUTING_KEY_S1_TO_S2)

    @Bean
    fun bindingS2ToS1(queueS2ToS1: Queue, exchange: TopicExchange): Binding =
            BindingBuilder.bind(queueS2ToS1).to(exchange).with(ROUTING_KEY_S2_TO_S1)

    @Bean
    fun converter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun template(connectionFactory: ConnectionFactory): AmqpTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = converter()
        return rabbitTemplate
    }
}