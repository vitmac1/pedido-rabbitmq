package com.vitor.pedido;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Endpoint para operações relacionadas a {@link Pedido}.
 *
 * @author Vitor Machado
 * @since 1.0 (18/07/2025)
 */
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PedidoCommandEndpoint {

    private final PedidoCommandService pedidoCommandService;

    private final PedidoStatusService pedidoStatusService;

    @PostMapping
    public ResponseEntity<Pedido> enviarPedido(@Valid @RequestBody Pedido pedido) {

        return new ResponseEntity<>(pedidoCommandService.processarPedido(pedido), HttpStatus.ACCEPTED);
    }

    @GetMapping("/status/{idPedido}")
    public ResponseEntity<String> consultarStatus(@PathVariable UUID idPedido) {

        String status = pedidoStatusService.consultarStatus(idPedido);

        return ResponseEntity.ok(status);
    }

}
