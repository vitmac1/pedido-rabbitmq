package com.vitor.pedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Data
public class Pedido {

    private UUID idPedido;

    @NotBlank
    private String produto;

    @NotNull
    @Min(0)
    private int quantidade;

    private LocalDateTime dataCriacao;
}
