package com.thullyoo.gerenciador_vendas.services;

import com.thullyoo.gerenciador_vendas.dtos.requests.VariacaoPatchRequest;
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

    @Transactional
    public VariacaoResponse atualizarVariacao(Long id, VariacaoPatchRequest variacaoRequest){
        Optional<VariacaoProduto> variacao = this.variacaoRepository.findById(id);

        if (variacao.isEmpty()){
            throw new RuntimeException("Variação não encontrada");
        }

        if (variacaoRequest.cor() != null && variacaoRequest.cor() != ""){
            variacao.get().setCor(variacaoRequest.cor());
        }
        if (variacaoRequest.quantidade() != null && variacaoRequest.quantidade() != 0){
            variacao.get().setQuantidade(variacaoRequest.quantidade());
        }
        if (variacaoRequest.sabor() != null && variacaoRequest.sabor() != ""){
            variacao.get().setSabor(variacaoRequest.sabor());
        }
        if (variacaoRequest.tamanho() != null && variacaoRequest.tamanho() != ""){
            variacao.get().setTamanho(variacaoRequest.tamanho());
        }
        if (variacao.get().getValor() != null && variacaoRequest. valor() != 0){
            variacao.get().setValor(variacaoRequest.valor());
        }
        this.variacaoRepository.save(variacao.get());


        return new VariacaoResponse(variacao.get().getVariacao_id(),variacao.get().getCor(),variacao.get().getTamanho(),variacao.get().getSabor(),variacao.get().getValor(),variacao.get().getQuantidade(),variacao.get().getProduto().getCodigo());
    }

}
