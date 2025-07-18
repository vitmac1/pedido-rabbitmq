package com.vitor.pedido;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_ENTRADA = "pedido.entrada.vitor";
    public static final String DLQ = "pedido.entrada.vitor.dlq";
    public static final String EXCHANGE = "pedido.exchange";
    public static final String QUEUE_STATUS_SUCESSO = "pedido.status.sucesso.vitor";
    public static final String QUEUE_STATUS_FALHA = "pedido.status.falha.vitor";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue pedidosEntradaQueue() {
        return QueueBuilder.durable(QUEUE_ENTRADA)
                .withArgument("x-dead-letter-exchange", EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLQ)
                .build();
    }

    @Bean
    public Queue pedidosDlqQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding bindingEntrada() {
        return BindingBuilder.bind(pedidosEntradaQueue())
                .to(exchange()).with(QUEUE_ENTRADA);
    }

    @Bean
    public Binding bindingDlq() {
        return BindingBuilder.bind(pedidosDlqQueue())
                .to(exchange()).with(DLQ);
    }

    @Bean
    public Queue pedidosStatusSucesso() {
        return QueueBuilder.durable(QUEUE_STATUS_SUCESSO).build();
    }

    @Bean
    public Queue pedidosStatusFalha() {
        return QueueBuilder.durable(QUEUE_STATUS_FALHA).build();
    }
}
