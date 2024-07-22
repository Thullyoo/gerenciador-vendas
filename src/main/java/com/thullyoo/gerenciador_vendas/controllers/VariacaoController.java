package com.thullyoo.gerenciador_vendas.controllers;

import com.thullyoo.gerenciador_vendas.dtos.requests.VariacaoRequest;
import com.thullyoo.gerenciador_vendas.dtos.responses.VariacaoResponse;
import com.thullyoo.gerenciador_vendas.services.VariacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/variacao")
public class VariacaoController {

    @Autowired
    private VariacaoService variacaoService;

    @PostMapping()
    public ResponseEntity<VariacaoResponse> registrarVariacao(@RequestBody VariacaoRequest variacaoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(variacaoService.registrarVariacao(variacaoRequest));
    }
}
