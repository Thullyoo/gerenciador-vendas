package com.thullyoo.gerenciador_vendas.controllers;

import com.thullyoo.gerenciador_vendas.dtos.responses.ProdutoGetResponse;
import com.thullyoo.gerenciador_vendas.dtos.requests.ProdutoRequest;
import com.thullyoo.gerenciador_vendas.dtos.responses.ProdutoResponse;
import com.thullyoo.gerenciador_vendas.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @PostMapping
    public ResponseEntity<ProdutoResponse> registrarProduto(@RequestBody ProdutoRequest produtoRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.registrarProduto(produtoRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoGetResponse>> resgatarTodosOsProdutos(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.resgatarTodosOsProdutos());
    }
}
