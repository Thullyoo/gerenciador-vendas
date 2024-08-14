package com.thullyoo.gerenciador_vendas.controllers;

import com.thullyoo.gerenciador_vendas.dtos.requests.DataRequest;
import com.thullyoo.gerenciador_vendas.dtos.requests.VendaRequest;
import com.thullyoo.gerenciador_vendas.dtos.responses.VendaResponse;
import com.thullyoo.gerenciador_vendas.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@CrossOrigin(origins = "http://localhost:4200")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponse> registrarVenda(@RequestBody VendaRequest vendaRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.registrarVenda(vendaRequest));
    }

    @GetMapping
    public ResponseEntity<List<VendaResponse>> resgatarTodasAsVendas(){
        return ResponseEntity.status(HttpStatus.OK).body(vendaService.resgatarTodasAsVendas());
    }

    @DeleteMapping("/{venda_id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable("venda_id") Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vendaService.deletarVenda(id));
    }

    @PostMapping("/data")
    public ResponseEntity<List<VendaResponse>> resgatarTodasAsVendasPorData(@RequestBody DataRequest data){
        return ResponseEntity.status(HttpStatus.OK).body(vendaService.resgatarVendaPorData(data.data()));
    }
}
