package com.thullyoo.gerenciador_vendas.dtos;

import java.util.List;

public record VendaRequest(List<VendaProdutoRequest> variacoes) {
}
