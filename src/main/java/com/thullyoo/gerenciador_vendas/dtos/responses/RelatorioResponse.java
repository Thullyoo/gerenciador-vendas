package com.thullyoo.gerenciador_vendas.dtos.responses;

import java.util.List;

public record RelatorioResponse(Integer numero_de_vendas, Double valor_total, List<VendaResponse> vendas) {
}
