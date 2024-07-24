package com.thullyoo.gerenciador_vendas.services;

import com.thullyoo.gerenciador_vendas.dtos.requests.VariacaoRequest;
import com.thullyoo.gerenciador_vendas.dtos.responses.VariacaoResponse;
import com.thullyoo.gerenciador_vendas.entities.produto_models.Produto;
import com.thullyoo.gerenciador_vendas.entities.variacao_produto_models.VariacaoProduto;
import com.thullyoo.gerenciador_vendas.repositories.ProdutoRepository;
import com.thullyoo.gerenciador_vendas.repositories.VariacaoRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
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
