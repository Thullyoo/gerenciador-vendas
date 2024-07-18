package com.thullyoo.gerenciador_vendas.repositories;

import com.thullyoo.gerenciador_vendas.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

}