package com.thullyoo.gerenciador_vendas.dtos.requests;

import java.util.List;

public record VendaRequest(List<VendaProdutoRequest> variacoes) {
}
