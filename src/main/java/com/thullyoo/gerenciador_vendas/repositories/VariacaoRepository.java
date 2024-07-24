package com.thullyoo.gerenciador_vendas.repositories;

import com.thullyoo.gerenciador_vendas.entities.variacao_produto_models.VariacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariacaoRepository extends JpaRepository<VariacaoProduto, Long> {

}