package com.ansv.taskmanagement.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    @Autowired
    private Environment environment;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(environment.getProperty("spring.rabbitmq.username"));
        connectionFactory.setPassword(environment.getProperty("spring.rabbitmq.password"));
        connectionFactory.setVirtualHost(environment.getProperty("spring.rabbitmq.virtualHost"));
        connectionFactory.setHost(environment.getProperty("spring.rabbitmq.host"));
        connectionFactory.setPort(Integer.parseInt(environment.getProperty("spring.rabbitmq.port")));

        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin
                = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
       SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
       factory.setConnectionFactory(connectionFactory());
       factory.setMessageConverter(jsonMessageConverter());

        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
       return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(MessageListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }


    // sender to
    @Bean
    public Queue queue() {
        return new Queue(environment.getProperty("spring.rabbitmq.queue"));
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(environment.getProperty("spring.rabbitmq.exchange"));
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(environment.getProperty("spring.rabbitmq.routingkey"));
//        return BindingBuilder.bind(queue()).to(directExchangeReceived()).with(environment.getProperty("spring.rabbitmq.routingkey"));
    }
    // sender


    // receiver from gateway
    @Bean
    public Queue queueReceived() {
        return new Queue(environment.getProperty("spring.rabbitmq.queue-received"));
    }

    @Bean
    public DirectExchange directExchangeReceived() {
        return new DirectExchange(environment.getProperty("spring.rabbitmq.exchange-received"));
    }
    // end receiver

    @Bean
    public Binding bindingReceived() {
        return BindingBuilder.bind(queueReceived()).to(directExchangeReceived()).with(environment.getProperty("spring.rabbitmq.routingkey-received"));
    }
    // sender

    // sender to human
    @Bean
    public Queue queueSenderToHuman() {
        return new Queue(environment.getProperty("spring.rabbitmq.queue-human"));
    }


    @Bean
    public Binding bindingSenderToHuman() {
        return BindingBuilder.bind(queueSenderToHuman()).to(directExchangeReceived()).with(environment.getProperty("spring.rabbitmq.routingkey-human"));
    }
    // sender

    // receiver from human
    @Bean
    public Queue queueReceiverFromHuman() {
        return new Queue(environment.getProperty("spring.rabbitmq.queue-task-human"));
    }


    @Bean
    public Binding bindingHumanSender() {
        return BindingBuilder.bind(queueReceiverFromHuman()).to(directExchangeReceived()).with(environment.getProperty("spring.rabbitmq.routingkey-task-human"));
    }
    // and receiver


}
