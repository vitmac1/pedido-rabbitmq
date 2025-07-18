package com.vitor.pedido.listener;

import com.vitor.pedido.ExcecaoDeProcessamento;
import com.vitor.pedido.Pedido;
import com.vitor.pedido.PedidoStatusService;
import com.vitor.pedido.send.StatusProducerStorageSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PedidoQueueStorageListener {

    private final PedidoStatusService pedidoStatusService;

    private final StatusProducerStorageSend statusProducerStorageSend;


    private final Random random = new Random();

    @RabbitListener(queues = "pedido.entrada.vitor")
    public void consumirPedido(Pedido pedido, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {

        log.debug("Iniciando processamento do pedido {}. fila {}", pedido.getIdPedido(), getQueueStorageDefinition());

        UUID idPedido = pedido.getIdPedido();

        try {
            Thread.sleep(1000 + random.nextInt(2000));

            if (random.nextDouble() < 0.2) {
                throw new ExcecaoDeProcessamento("Erro simulado no processamento.");
            }

            pedidoStatusService.atualizarStatus(idPedido, "SUCESSO");

            statusProducerStorageSend.enviarStatusSucesso(idPedido);

            log.error("Processamento de pedido {}. finalizado. fila {}", pedido.getIdPedido(), getQueueStorageDefinition());

        } catch (ExcecaoDeProcessamento e) {

            pedidoStatusService.atualizarStatus(idPedido, "FALHA");

            statusProducerStorageSend.enviarStatusFalha(idPedido, e.getMessage());

            throw e;
        }
    }

    public String getQueueStorageDefinition() {

        return "pedido.entrada.vitor";
    }
}
