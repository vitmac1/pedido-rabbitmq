package com.vitor.pedido;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusPedido {

    private UUID idPedido;

    private String status;

    private String mensagemErro;

    private LocalDateTime dataProcessamento;
}
