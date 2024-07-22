package com.thullyoo.gerenciador_vendas.dtos.responses;

public record VariacaoResponse(Long id,String cor,String tamanho,String sabor,Double valor,Integer quantidade, String produto_codigo ) {
}
