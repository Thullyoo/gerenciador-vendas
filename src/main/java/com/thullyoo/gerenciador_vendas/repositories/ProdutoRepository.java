package com.thullyoo.gerenciador_vendas.repositories;

import com.thullyoo.gerenciador_vendas.entities.produto_models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(String codigo);

    @Query(value = "SELECT * FROM TB_PRODUTOS WHERE LOWER(nome) LIKE LOWER(CONCAT('%', :nomeOuCodigo, '%')) OR LOWER(codigo) LIKE LOWER(CONCAT('%', :nomeOuCodigo, '%'))", nativeQuery = true)
    List<Produto> findByNomeOrCodigo(@Param("nomeOuCodigo") String nomeOuCodigo);

}