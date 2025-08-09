package gr.symfoititis.tutoring.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    @Value("${spring.rabbitmq.queues.courses.request}")
    private String coursesRequestQueue;
    @Value("${spring.rabbitmq.queues.courses.reply}")
    private String coursesReplyQueue;

    @Value("${spring.rabbitmq.keys.chatRooms}")
    private String chatRoomsKey;
    @Value("${spring.rabbitmq.keys.courses.request}")
    private String coursesRequestKey;
    @Value("${spring.rabbitmq.keys.courses.reply}")
    private String coursesReplyKey;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue chatRoomsQueue() {
        return new Queue(chatRoomsQueue, false);
    }
    @Bean
    public Queue coursesRequestQueue() {
        return new Queue(coursesRequestQueue, false);
    }
    @Bean
    public Queue coursesReplyQueue() {
        return new Queue(coursesReplyQueue, false);
    }

    @Bean
    public Binding chatRoomsBinding(Queue chatRoomsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(chatRoomsQueue).to(exchange).with(chatRoomsKey);
    }
    @Bean
    public Binding coursesRequestBinding(Queue coursesRequestQueue, DirectExchange exchange) {
        return BindingBuilder.bind(coursesRequestQueue).to(exchange).with(coursesRequestKey);
    }
    @Bean
    public Binding coursesReplyBinding(Queue coursesReplyQueue, DirectExchange exchange) {
        return BindingBuilder.bind(coursesReplyQueue).to(exchange).with(coursesReplyKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyAddress(coursesReplyQueue);
        rabbitTemplate.setReplyTimeout(5000);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    public String getExchange() {
        return exchange;
    }

    public String getChatRoomsKey() {
        return chatRoomsKey;
    }

    public String getCoursesRequestKey() {
        return coursesRequestKey;
    }
}
