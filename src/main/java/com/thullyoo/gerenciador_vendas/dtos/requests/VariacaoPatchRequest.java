package com.thullyoo.gerenciador_vendas.dtos.requests;

public record VariacaoPatchRequest(String cor, String tamanho, String sabor, Double valor, Integer quantidade) {
}
