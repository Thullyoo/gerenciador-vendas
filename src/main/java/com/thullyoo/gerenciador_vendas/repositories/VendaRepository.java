package com.thullyoo.gerenciador_vendas.repositories;

import com.thullyoo.gerenciador_vendas.entities.venda_models.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query(value = "SELECT * FROM tb_vendas WHERE DATE(horario_de_venda) = DATE(:dataEspecifica)", nativeQuery = true)
    List<Venda> findBySpecificDate(@Param("dataEspecifica") LocalDate dataEspecifica);

}