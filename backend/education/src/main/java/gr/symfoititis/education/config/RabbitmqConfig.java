package gr.symfoititis.education.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.queues.courses.request}")
    private String coursesRequestQueue;

    @Value("${spring.rabbitmq.keys.courses.request}")
    private String coursesRequestKey;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue coursesRequestQueue() {
        return new Queue(coursesRequestQueue, false);
    }

    @Bean
    public Binding coursesRequestBinding(Queue coursesRequestQueue, DirectExchange exchange) {
        return BindingBuilder.bind(coursesRequestQueue).to(exchange).with(coursesRequestKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
