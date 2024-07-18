package com.thullyoo.gerenciador_vendas.dtos;

import com.thullyoo.gerenciador_vendas.entities.VariacaoProduto;

import java.util.Set;

public record ProdutoResponse(Long id, String nome, String codigo, Double valor_original) {
}
