package com.thullyoo.gerenciador_vendas.dtos.responses;

public record VendaProdutoResponse(String codigo, String nome, Double valor_total, Integer quantidade, Long variacao_id) {
}
