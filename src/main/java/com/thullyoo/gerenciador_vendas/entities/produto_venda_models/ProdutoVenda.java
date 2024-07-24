package com.thullyoo.gerenciador_vendas.entities.produto_venda_models;


import com.thullyoo.gerenciador_vendas.entities.variacao_produto_models.VariacaoProduto;
import com.thullyoo.gerenciador_vendas.entities.venda_models.Venda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "TB_PRODUTOSVENDAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variacao_id", nullable = false)
    private VariacaoProduto variacao;

    @Column(nullable = false)
    private int quantidade;

    private Double valor_total;
}
