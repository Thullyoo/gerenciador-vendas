package com.thullyoo.gerenciador_vendas.dtos.responses;

import java.time.LocalDateTime;
import java.util.List;

public record VendaResponse(Long id, List<VendaProdutoResponse> produtos, Double total, String data_horario) {
}
