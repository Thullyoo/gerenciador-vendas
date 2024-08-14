package com.thullyoo.gerenciador_vendas.controllers;

import com.thullyoo.gerenciador_vendas.dtos.responses.ProdutoGetResponse;
import com.thullyoo.gerenciador_vendas.dtos.requests.ProdutoRequest;
import com.thullyoo.gerenciador_vendas.dtos.responses.ProdutoResponse;
import com.thullyoo.gerenciador_vendas.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:4200")
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

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> desativarProduto(@PathVariable("codigo") String codigo){
        produtoService.desativarProduto(codigo);
        return null;
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Void> ativarProduto(@PathVariable("codigo") String codigo){
        produtoService.ativarProduto(codigo);
        return null;
    }

    @GetMapping("/{nome_ou_codigo}")
    public ResponseEntity<List<ProdutoGetResponse>> resgatarPorNomeOuCodigo(@PathVariable String nome_ou_codigo){
        return ResponseEntity.status(HttpStatus.OK).body(this.produtoService.resgatarProdutosPorCodigoOuNome(nome_ou_codigo));
    }
}
