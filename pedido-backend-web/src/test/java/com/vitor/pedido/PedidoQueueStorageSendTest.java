package com.vitor.pedido;

import com.vitor.pedido.send.PedidoQueueStorageSend;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoQueueStorageSendTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private PedidoStatusService pedidoStatusService;

    @InjectMocks
    private PedidoQueueStorageSend pedidoQueueStorageSend;

    @Test
    void deveEnviarPedidoParaFilaEAtualizarStatus() {

        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);

        pedidoQueueStorageSend.send(pedido);

        verify(pedidoStatusService).atualizarStatus(id, "RECEBIDO");
        verify(rabbitTemplate).convertAndSend("pedido.entrada.vitor", pedido);
    }
}
