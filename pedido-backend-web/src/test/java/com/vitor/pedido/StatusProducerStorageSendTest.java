package com.vitor.pedido;

import com.vitor.pedido.send.StatusProducerStorageSend;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StatusProducerStorageSendTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private StatusProducerStorageSend statusProducer;

    @Test
    void deveEnviarStatusSucessoParaFila() {
        UUID id = UUID.randomUUID();

        statusProducer.enviarStatusSucesso(id);

        verify(rabbitTemplate).convertAndSend(eq("pedido.status.sucesso.vitor"), any(StatusPedido.class));
    }

    @Test
    void deveEnviarStatusFalhaParaFila() {
        UUID id = UUID.randomUUID();
        String erro = "Erro simulado";

        statusProducer.enviarStatusFalha(id, erro);

        verify(rabbitTemplate).convertAndSend(eq("pedido.status.falha.vitor"), any(StatusPedido.class));
    }
}
