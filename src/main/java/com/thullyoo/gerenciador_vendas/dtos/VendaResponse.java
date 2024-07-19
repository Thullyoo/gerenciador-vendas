package com.thullyoo.gerenciador_vendas.dtos;

import java.util.List;

public record VendaResponse(Long id, List<VendaProdutoResponse> produtos, Double total) {
}
