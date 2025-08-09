package gr.symfoititis.chat.config;

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

    @Value("${spring.rabbitmq.queues.chatRooms}")
    private String chatRoomsQueue;

    @Value("${spring.rabbitmq.keys.chatRooms}")
    private String chatRoomsKey;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue chatRoomsQueue() {
        return new Queue(chatRoomsQueue, false);
    }

    @Bean
    public Binding chatRoomsBinding() {
        return BindingBuilder.bind(chatRoomsQueue()).to(exchange()).with(chatRoomsKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
