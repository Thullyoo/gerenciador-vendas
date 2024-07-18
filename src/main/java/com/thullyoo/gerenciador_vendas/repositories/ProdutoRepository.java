package com.thullyoo.gerenciador_vendas.repositories;

import com.thullyoo.gerenciador_vendas.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(String codigo);
}