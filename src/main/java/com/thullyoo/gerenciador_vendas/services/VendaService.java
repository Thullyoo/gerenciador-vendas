package com.thullyoo.gerenciador_vendas.services;

import com.thullyoo.gerenciador_vendas.dtos.responses.VendaProdutoResponse;
import com.thullyoo.gerenciador_vendas.dtos.requests.VendaRequest;
import com.thullyoo.gerenciador_vendas.dtos.responses.VendaResponse;
import com.thullyoo.gerenciador_vendas.entities.Produto;
import com.thullyoo.gerenciador_vendas.entities.ProdutoVenda;
import com.thullyoo.gerenciador_vendas.entities.VariacaoProduto;
import com.thullyoo.gerenciador_vendas.entities.Venda;
import com.thullyoo.gerenciador_vendas.repositories.ProdutoRepository;
import com.thullyoo.gerenciador_vendas.repositories.ProdutoVendaRepository;
import com.thullyoo.gerenciador_vendas.repositories.VariacaoRepository;
import com.thullyoo.gerenciador_vendas.repositories.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    private VariacaoRepository variacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public VendaResponse registrarVenda(VendaRequest vendaRequest){
        Set<ProdutoVenda> produtos = new HashSet<>();
        Double total = 0.0;

        Venda venda = new Venda();

        Set<ProdutoVenda> produtoVendas = new HashSet<>();

        List<VendaProdutoResponse> produtosVendaResponse = new ArrayList<>();

        for(int i = 0; i < vendaRequest.variacoes().size(); i++){
            Optional<VariacaoProduto> variacaoProduto =  variacaoRepository.findById(vendaRequest.variacoes().get(i).variacao_id());
            Optional<Produto> produto = produtoRepository.findByCodigo(variacaoProduto.get().getProduto().getCodigo());
            Double valor = 0.0;
            if (variacaoProduto.isEmpty()){
                throw new RuntimeException("Variacão não registrada.");
            }
            if (variacaoProduto.get().getQuantidade() < vendaRequest.variacoes().get(i).quantidade()){
                throw new RuntimeException("Quantidade insuficiente.");
            }
            if (variacaoProduto.get().getValor() == null){
                total += produto.get().getValor_original() * vendaRequest.variacoes().get(i).quantidade();
                valor = produto.get().getValor_original() * vendaRequest.variacoes().get(i).quantidade();
            } else {
                total += variacaoProduto.get().getValor() * vendaRequest.variacoes().get(i).quantidade();
                valor = variacaoProduto.get().getValor() * vendaRequest.variacoes().get(i).quantidade();
            }

            ProdutoVenda produtoVenda = new ProdutoVenda();
            produtoVenda.setVariacao(variacaoProduto.get());
            produtoVenda.setVenda(venda);
            produtoVenda.setQuantidade(vendaRequest.variacoes().get(i).quantidade());
            produtoVenda.setValor_total(valor);

            variacaoProduto.get().setQuantidade(variacaoProduto.get().getQuantidade() - vendaRequest.variacoes().get(i).quantidade());
            variacaoRepository.save(variacaoProduto.get());
            produtoVendas.add(produtoVenda);

            produtosVendaResponse.add(new VendaProdutoResponse(produto.get().getCodigo(), produto.get().getNome(),valor,vendaRequest.variacoes().get(i).quantidade(),variacaoProduto.get().getVariacao_id()));
        }

        venda.setTotal(total);
        venda.setHorario_de_venda(LocalDateTime.now());
        venda.setProdutos(produtoVendas);
        vendaRepository.save(venda);

        for (ProdutoVenda produtoVenda : produtoVendas){
            produtoVenda.setVenda(venda);
            produtoVendaRepository.save(produtoVenda);
        }

        return new VendaResponse(venda.getId(), produtosVendaResponse, total, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, H:m:s")));
    }

    public List<VendaResponse> resgatarTodasAsVendas(){
        List<Venda> vendas = vendaRepository.findAll();

        List<VendaResponse> vendaResponses = new ArrayList<>();

        for (Venda v : vendas){
            List<VendaProdutoResponse> produtos = new ArrayList<>();
            v.getProdutos().forEach(produtoVenda -> {
                produtos.add(new VendaProdutoResponse(produtoVenda.getVariacao().getProduto().getCodigo(), produtoVenda.getVariacao().getProduto().getNome(), produtoVenda.getValor_total(), produtoVenda.getQuantidade(), produtoVenda.getVariacao().getVariacao_id()));
            });
            vendaResponses.add(new VendaResponse(v.getId(),produtos, v.getTotal(), v.getHorario_de_venda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, H:m:s"))));
        }

        return vendaResponses;

    }

    @Transactional
    public Void deletarVenda(Long id){
        Optional<Venda> venda = vendaRepository.findById(id);
        if (venda.isEmpty()){
            throw new RuntimeException("Id ou venda não registrada");
        }
        vendaRepository.delete(venda.get());
        return null;
    }


}
