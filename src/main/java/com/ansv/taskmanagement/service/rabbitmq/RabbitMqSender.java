package com.ansv.taskmanagement.service.rabbitmq;

import com.ansv.taskmanagement.dto.response.UserDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMqSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange-received:#{null}}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey-received:#{null}}")
    private String routingkey;

    @Value("${spring.rabbitmq.routingkey-human:#{null}}")
    private String routingkeyHumanSender;

    public void sender(String username) {
        rabbitTemplate.convertAndSend(exchange, routingkey, username);
    }

    public void senderToHuman(String username) {
        rabbitTemplate.convertAndSend(exchange, routingkeyHumanSender, username);
    }

    public void senderToGateway(UserDTO user) {
        rabbitTemplate.convertAndSend(exchange, routingkeyHumanSender, user);
    }


}
