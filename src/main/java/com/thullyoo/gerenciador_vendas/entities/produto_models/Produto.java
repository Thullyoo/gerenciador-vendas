package com.thullyoo.gerenciador_vendas.entities.produto_models;

import com.thullyoo.gerenciador_vendas.entities.variacao_produto_models.VariacaoProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "TB_PRODUTOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produto_id;

    private String codigo;

    private String nome;

    private Double valor_original;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VariacaoProduto> variacaoProduto;


}
