package com.abm.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    private final String directExchange = "directExchange";
    private final String queueUserSave = "queueUserSave";
    private final String keyUserSave = "keyUserSave";
    private final String queueActivation = "queueActivation";
    private final String keyActivation = "keyActivation";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }
    @Bean
    public Queue queueUserSave() {
        return new Queue(queueUserSave);
    }
    @Bean
    public Queue queueActivation() {
    return new Queue(queueActivation);
    }

    @Bean
    public Binding bindingActivation(Queue queueActivation, DirectExchange directExchange) {
        return BindingBuilder.bind(queueActivation).to(directExchange).with(keyActivation);
    }
    @Bean
    public Binding bindingUserSave(Queue queueUserSave, DirectExchange directExchange) {
        return BindingBuilder.bind(queueUserSave).to(directExchange).with(keyUserSave);
    }
    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
