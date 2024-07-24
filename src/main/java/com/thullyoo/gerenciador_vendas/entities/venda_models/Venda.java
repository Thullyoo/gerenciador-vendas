package com.thullyoo.gerenciador_vendas.entities.venda_models;

import com.thullyoo.gerenciador_vendas.entities.produto_venda_models.ProdutoVenda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
}
