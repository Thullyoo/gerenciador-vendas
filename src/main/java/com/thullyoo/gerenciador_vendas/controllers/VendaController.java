package com.thullyoo.gerenciador_vendas.controllers;

import com.thullyoo.gerenciador_vendas.dtos.VendaRequest;
import com.thullyoo.gerenciador_vendas.dtos.VendaResponse;
import com.thullyoo.gerenciador_vendas.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponse> registrarVenda(@RequestBody VendaRequest vendaRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.registrarVenda(vendaRequest));
    }

    @GetMapping
    public ResponseEntity<List<VendaResponse>> resgatarTodasAsVendas(){
        return ResponseEntity.status(HttpStatus.FOUND).body(vendaService.resgatarTodasAsVendas());
    }

    @DeleteMapping("/{venda_id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable("venda_id") Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vendaService.deletarVenda(id));
    }

}
