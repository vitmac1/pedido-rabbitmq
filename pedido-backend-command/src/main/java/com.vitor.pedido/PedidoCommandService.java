package com.vitor.pedido;

import com.vitor.pedido.send.PedidoQueueStorageSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PedidoCommandService {

    PedidoQueueStorageSend pedidoQueueStorageSend;

    public Pedido processarPedido(Pedido pedido) {

        try {

            UUID id = UUID.randomUUID();

            pedido.setIdPedido(id);
            pedido.setDataCriacao(LocalDateTime.now());

            enviarPedido(pedido);

            return pedido;

        } catch (Exception e) {

            log.error("Erro ao processar pedido", e);

            throw new IllegalStateException("Erro ao processar pedido", e);
        }
    }

    private void enviarPedido(Pedido pedido) {

        pedidoQueueStorageSend.send(pedido);
    }

}
