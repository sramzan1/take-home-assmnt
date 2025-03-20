package service2.service

import service2.model.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class MessagingService(private val rabbitTemplate: RabbitTemplate) {

    @RabbitListener(queues = [RabbitMQConfig.QUEUE_S1_TO_S2])
    fun receiveMessage(message: Message) {
        println("S2 received: $message")
        if (message.content == "ping") {
            val pongMessage = Message(content = "pong", sender = "Service-2")
            println("S2 sending: $pongMessage")
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_S2_TO_S1,
                    pongMessage
            )
            println("S2 waiting for 10 seconds...")
            Thread.sleep(10000)
            val pingMessage = Message(content = "ping", sender = "Service-2")
            println("S2 sending: $pingMessage")
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_S2_TO_S1,
                    pingMessage
            )
        }
    }
}
