package com.vitor.pedido;

public class ExcecaoDeProcessamento extends RuntimeException {
    public ExcecaoDeProcessamento(String message) {
        super(message);
    }
}
