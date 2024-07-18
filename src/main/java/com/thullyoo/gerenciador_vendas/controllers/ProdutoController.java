package com.thullyoo.gerenciador_vendas.controllers;

import com.thullyoo.gerenciador_vendas.dtos.ProdutoRequest;
import com.thullyoo.gerenciador_vendas.dtos.ProdutoResponse;
import com.thullyoo.gerenciador_vendas.entities.Produto;
import com.thullyoo.gerenciador_vendas.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @PostMapping
    public ResponseEntity<ProdutoResponse> registrarProduto(@RequestBody ProdutoRequest produtoRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.registrarProduto(produtoRequest));
    }
}
