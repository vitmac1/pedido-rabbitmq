package com.vitor.pedido.send;

import com.vitor.pedido.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatusProducerStorageSend {

    private final RabbitTemplate rabbitTemplate;

    public void enviarStatusSucesso(UUID idPedido) {

        StatusPedido status = new StatusPedido(idPedido, "SUCESSO", null, LocalDateTime.now());

        rabbitTemplate.convertAndSend("pedido.status.sucesso.vitor", status);
    }

    public void enviarStatusFalha(UUID idPedido, String erro) {

        StatusPedido status = new StatusPedido(idPedido, "FALHA", null, LocalDateTime.now());

        rabbitTemplate.convertAndSend("pedido.status.falha.vitor", status);
    }
}
