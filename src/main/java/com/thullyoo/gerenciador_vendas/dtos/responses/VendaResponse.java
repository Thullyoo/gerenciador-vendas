package com.thullyoo.gerenciador_vendas.dtos.responses;

import java.util.List;

public record VendaResponse(Long id, List<ProdutoVendaResponse> produtos, Double total, String data_horario) {
}
