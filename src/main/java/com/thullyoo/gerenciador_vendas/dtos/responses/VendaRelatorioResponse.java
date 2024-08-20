package com.thullyoo.gerenciador_vendas.dtos.responses;

import java.time.LocalDateTime;
import java.util.List;

public record VendaRelatorioResponse(Long id, List<ProdutoVendaResponse> produtos, Double total, LocalDateTime horario_de_venda) {
}
