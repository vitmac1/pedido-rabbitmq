package com.vitor.pedido;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PedidoStatusService {

    private final Map<UUID, String> statusMap = new ConcurrentHashMap<>();

    public void atualizarStatus(UUID id, String status) {

        statusMap.put(id, status);
    }

    public String consultarStatus(UUID idPedido) {

        return statusMap.getOrDefault(idPedido, "DESCONHECIDO");
    }
}
