package com.thullyoo.gerenciador_vendas.entities.produto_venda_models;

import com.thullyoo.gerenciador_vendas.dtos.responses.ProdutoVendaResponse;
import org.springframework.stereotype.Component;

@Component
public class ProdutoVendaMapper {

    public ProdutoVendaResponse ProdutoVendaToProdutoVendaResponse(ProdutoVenda produtoVenda, Double valor){
        return new ProdutoVendaResponse(produtoVenda.getVariacao().getProduto().getCodigo(), produtoVenda.getVariacao().getProduto().getNome(), valor, produtoVenda.getQuantidade(), produtoVenda.getVariacao().getVariacao_id());
    }
}
