package com.thullyoo.gerenciador_vendas.repositories;

import com.thullyoo.gerenciador_vendas.entities.produto_venda_models.ProdutoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoVendaRepository extends JpaRepository<ProdutoVenda, Long> {

}