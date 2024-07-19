package com.thullyoo.gerenciador_vendas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_VARIACAO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VariacaoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variacao_id;

    private String cor;

    private String tamanho;

    private String sabor;

    private Double valor;

    private Integer quantidade;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
}
