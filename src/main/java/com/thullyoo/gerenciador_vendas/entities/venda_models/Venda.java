package com.thullyoo.gerenciador_vendas.entities.venda_models;

import com.thullyoo.gerenciador_vendas.dtos.responses.ProdutoVendaResponse;
import com.thullyoo.gerenciador_vendas.dtos.responses.VendaRelatorioResponse;
import com.thullyoo.gerenciador_vendas.entities.produto_venda_models.ProdutoVenda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TB_VENDAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ProdutoVenda> produtos;

    private Double total;

    private LocalDateTime horario_de_venda;

    public VendaRelatorioResponse vendaToVendaRelatorio(Venda venda){
        return new VendaRelatorioResponse(venda.getId(), produtoVendaToResponse(venda.getProdutos()), venda.getTotal(), venda.getHorario_de_venda());
    }
    public List<ProdutoVendaResponse> produtoVendaToResponse(Set<ProdutoVenda> produtoVendas){
        List<ProdutoVendaResponse> produtoVendaResponses = new ArrayList<>();
        for(ProdutoVenda produtoVenda : produtoVendas){
            produtoVendaResponses.add(new ProdutoVendaResponse(produtoVenda.getVariacao().getProduto().getCodigo(),produtoVenda.getVariacao().getProduto().getNome(), produtoVenda.getValor_total(), produtoVenda.getQuantidade(), produtoVenda.getVariacao().getVariacao_id() ));
        }
        return produtoVendaResponses;
    }
}
