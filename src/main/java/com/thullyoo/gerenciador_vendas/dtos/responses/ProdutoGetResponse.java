package com.thullyoo.gerenciador_vendas.dtos.responses;

import java.util.List;

public record ProdutoGetResponse(String codigo, String nome, Double valor_original, List<VariacaoResponse> variacoes) {
}
