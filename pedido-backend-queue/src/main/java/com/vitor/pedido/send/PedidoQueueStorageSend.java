package com.vitor.pedido.send;

import com.vitor.pedido.Pedido;
import com.vitor.pedido.PedidoStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Listener para processar pedido.
 *
 * @author Vitor Machado
 * @since 1.0 (18/07/25)
 */
@Slf4j
@Component
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PedidoQueueStorageSend {

    private final RabbitTemplate rabbitTemplate;

    private final PedidoStatusService pedidoStatusService;

    public void send(Pedido pedido) {

        log.debug("Enviando para fila {}. idPedido {}", getQueueStorageDefinition(), pedido.getIdPedido());

        pedidoStatusService.atualizarStatus(pedido.getIdPedido(), "RECEBIDO");

        rabbitTemplate.convertAndSend("pedido.entrada.vitor", pedido);

        log.debug("Mensagem enviada com sucesso para fila {}. idPedido {}", getQueueStorageDefinition(), pedido.getIdPedido());
    }

    public String getQueueStorageDefinition() {

        return "pedido.entrada.vitor";
    }
}
