package com.thullyoo.gerenciador_vendas.dtos.requests;

    public record VariacaoRequest(String codigo_produto, String cor, String tamanho, String sabor, Double valor, Integer quantidade) {
}
