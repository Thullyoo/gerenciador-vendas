package com.thullyoo.gerenciador_vendas.services;

import com.thullyoo.gerenciador_vendas.dtos.responses.ProdutoVendaResponse;
import com.thullyoo.gerenciador_vendas.dtos.requests.VendaRequest;
import com.thullyoo.gerenciador_vendas.dtos.responses.RelatorioResponse;
import com.thullyoo.gerenciador_vendas.dtos.responses.VendaResponse;
import com.thullyoo.gerenciador_vendas.entities.produto_models.Produto;
import com.thullyoo.gerenciador_vendas.entities.produto_venda_models.ProdutoVenda;
import com.thullyoo.gerenciador_vendas.entities.produto_venda_models.ProdutoVendaMapper;
import com.thullyoo.gerenciador_vendas.entities.variacao_produto_models.VariacaoProduto;
import com.thullyoo.gerenciador_vendas.entities.venda_models.Venda;
import com.thullyoo.gerenciador_vendas.repositories.ProdutoRepository;
import com.thullyoo.gerenciador_vendas.repositories.ProdutoVendaRepository;
import com.thullyoo.gerenciador_vendas.repositories.VariacaoRepository;
import com.thullyoo.gerenciador_vendas.repositories.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private ProdutoVendaMapper produtoVendaMapper;

    @Transactional
    public VendaResponse registrarVenda(VendaRequest vendaRequest){
        Set<ProdutoVenda> produtos = new HashSet<>();
        Double total = 0.0;

        Venda venda = new Venda();

        Set<ProdutoVenda> produtoVendas = new HashSet<>();

        List<ProdutoVendaResponse> produtosVendaResponse = new ArrayList<>();

        for(int i = 0; i < vendaRequest.variacoes().size(); i++){

            Optional<VariacaoProduto> variacaoProduto =  variacaoRepository.findById(vendaRequest.variacoes().get(i).variacao_id());

            if (variacaoProduto.isEmpty()){
                throw new RuntimeException("Produto ou variação não resgistrado");
            }

            Optional<Produto> produto = produtoRepository.findByCodigo(variacaoProduto.get().getProduto().getCodigo());

            if (produto.isEmpty()){
                throw new RuntimeException("Produto não registrado");
            }

            if (produto.get().getProduto_ativo() == false){
                throw new RuntimeException("Produto indisponível");
            }

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

            produtosVendaResponse.add(produtoVendaMapper.ProdutoVendaToProdutoVendaResponse(produtoVenda, valor));
        }

        venda.setTotal(total);
        venda.setHorario_de_venda(LocalDateTime.now());
        venda.setProdutos(produtoVendas);
        vendaRepository.save(venda);

        for (ProdutoVenda produtoVenda : produtoVendas){
            produtoVenda.setVenda(venda);
            produtoVendaRepository.save(produtoVenda);
        }

        return new VendaResponse(venda.getId(), produtosVendaResponse, total, formatDate(LocalDateTime.now()));
    }

    public Page<VendaResponse> resgatarTodasAsVendas(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Venda> vendas = vendaRepository.findAll(pageable);

        List<VendaResponse> vendaResponses = vendas.getContent().stream()
                .map(v -> {
                    List<ProdutoVendaResponse> produtos = v.getProdutos().stream()
                            .map(produtoVenda -> produtoVendaMapper.ProdutoVendaToProdutoVendaResponse(produtoVenda, produtoVenda.getValor_total()))
                            .collect(Collectors.toList());

                    return new VendaResponse(v.getId(), produtos, v.getTotal(), formatDate(v.getHorario_de_venda()));
                })
                .collect(Collectors.toList());

        return new PageImpl<>(vendaResponses, pageable, vendas.getTotalElements());
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

    public List<VendaResponse> resgatarVendaPorData(String date){
        LocalDate localDate = LocalDate.parse(date);
        List<Venda> vendas = vendaRepository.findBySpecificDate(localDate);
        System.out.println(date);

        if (vendas.isEmpty()){
            throw new RuntimeException("Não existe vendas nesse dia");
        }

        List<VendaResponse> vendaResponses = vendas.stream().map(venda -> {
            return new VendaResponse(venda.getId(), venda.getProdutos().stream().map(produtoVenda -> {
                return produtoVendaMapper.ProdutoVendaToProdutoVendaResponse(produtoVenda, produtoVenda.getValor_total());
            }).toList(), venda.getTotal(), formatDate(venda.getHorario_de_venda()));
        }).toList();

        return vendaResponses;

    }


    public RelatorioResponse resgatarRelatorioDia(){

        List<Venda> vendasDoDia = this.vendaRepository.findVendasForToday();

        List<VendaResponse> vendaResponses = vendasDoDia.stream().map(venda -> {
            return new VendaResponse(venda.getId(), venda.getProdutos().stream().map(produtoVenda -> {
                return produtoVendaMapper.ProdutoVendaToProdutoVendaResponse(produtoVenda, produtoVenda.getValor_total());
            }).toList(), venda.getTotal(), formatDate(venda.getHorario_de_venda()));
        }).toList();

        if (vendasDoDia.isEmpty()){
            throw new RuntimeException("Nenhuma venda feita no dia");
        }
        Double valor_total = 0.0;
        int numero_de_vendas = vendasDoDia.size();
        System.out.println(numero_de_vendas);
        for (Venda venda : vendasDoDia){
            valor_total += venda.getTotal();
        }



        return new RelatorioResponse(numero_de_vendas, valor_total,vendaResponses);
    }

    private String formatDate(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy, H:m:s"));
    }


}
