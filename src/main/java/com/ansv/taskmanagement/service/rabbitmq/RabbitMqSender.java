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

//    public RabbitMqSender() {
//        this.rabbitTemplate = new RabbitTemplate();
//    }
//
//    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }


    @Value("${spring.rabbitmq.exchange:#{null}}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey:#{null}}")
    private String routingkey;

//    public void sender(UserDTO user) {
//        rabbitTemplate.convertAndSend(exchange, routingkey, user);
//    }

    public void sender(UserDTO user) {
        rabbitTemplate.convertAndSend(exchange, routingkey, user);
    }

}
