package com.thullyoo.gerenciador_vendas.services;

import com.thullyoo.gerenciador_vendas.dtos.VariacaoRequest;
import com.thullyoo.gerenciador_vendas.dtos.VariacaoResponse;
import com.thullyoo.gerenciador_vendas.entities.Produto;
import com.thullyoo.gerenciador_vendas.entities.VariacaoProduto;
import com.thullyoo.gerenciador_vendas.repositories.ProdutoRepository;
import com.thullyoo.gerenciador_vendas.repositories.VariacaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VariacaoService {

    @Autowired
    private VariacaoRepository variacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public VariacaoResponse registrarVariacao(VariacaoRequest variacaoRequest){
        Optional<Produto> produto = produtoRepository.findByCodigo(variacaoRequest.codigo_produto());

        if (produto.isEmpty()){
            throw new RuntimeException("Produto não registrado.");
        }

        VariacaoProduto variacao = new VariacaoProduto();

        BeanUtils.copyProperties(variacaoRequest, variacao);

        variacao.setProduto(produto.get());

        variacaoRepository.save(variacao);

        return new VariacaoResponse(variacao.getVariacao_id(),variacao.getCor(),variacao.getTamanho(),variacao.getSabor(),variacao.getValor(),variacao.getQuantidade(),variacaoRequest.codigo_produto());

    }

}
