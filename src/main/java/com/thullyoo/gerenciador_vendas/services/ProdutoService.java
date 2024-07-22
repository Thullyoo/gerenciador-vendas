package com.thullyoo.gerenciador_vendas.services;

import com.thullyoo.gerenciador_vendas.dtos.ProdutoGetResponse;
import com.thullyoo.gerenciador_vendas.dtos.ProdutoRequest;
import com.thullyoo.gerenciador_vendas.dtos.ProdutoResponse;
import com.thullyoo.gerenciador_vendas.dtos.VariacaoResponse;
import com.thullyoo.gerenciador_vendas.entities.Produto;
import com.thullyoo.gerenciador_vendas.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public ProdutoResponse registrarProduto(ProdutoRequest produtoRequest) throws Exception {
        if (produtoRepository.findByCodigo(produtoRequest.codigo()).isPresent()){
            throw new Exception("Produto já registrado.");
        }

        Produto produto = new Produto();

        produto.setNome(produtoRequest.nome());
        produto.setCodigo(produtoRequest.codigo());
        System.out.println(produtoRequest.valor_original());
        produto.setValor_original(produtoRequest.valor_original());
        produto.setVariacaoProduto(null);

        produtoRepository.save(produto);

        return new ProdutoResponse(produto.getProduto_id(), produto.getNome(), produto.getCodigo(), produto.getValor_original());
    }

    public List<ProdutoGetResponse> resgatarTodosOsProdutos(){
        List<Produto> produtos = produtoRepository.findAll();

        List<ProdutoGetResponse> produtosResponse = new ArrayList<>();

        produtos.forEach(produto -> {
            produtosResponse.add(new ProdutoGetResponse(produto.getCodigo(),produto.getNome(), produto.getValor_original(),produto.getVariacaoProduto().stream().map(variacaoProduto -> {
                return new VariacaoResponse(variacaoProduto.getVariacao_id(), variacaoProduto.getCor(), variacaoProduto.getTamanho(),variacaoProduto.getTamanho(), variacaoProduto.getValor(), variacaoProduto.getQuantidade(), produto.getCodigo());
            }).toList()));
        });
        return produtosResponse;
    }


}
