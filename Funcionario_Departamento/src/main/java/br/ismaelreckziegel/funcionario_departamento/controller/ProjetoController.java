package br.ismaelreckziegel.funcionario_departamento.controller;

import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import br.ismaelreckziegel.funcionario_departamento.service.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjetoController {

    private final ProjetoService service;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @PostMapping("/novoprojeto")
    public ResponseEntity<ProjetoModel> create(@RequestBody ProjetoModel novoProjeto){
        return ResponseEntity.status(201).body(service.create(novoProjeto));
    }

    @GetMapping("projeto/search")
    public ResponseEntity<List<ProjetoModel>> readAll(){
        return ResponseEntity.status(200).body(service.readAll());
    }

    @GetMapping("/projeto/serachid/{id}")
    public ResponseEntity<ProjetoModel> readById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readById(id));
    }

    @GetMapping("/projeto/searchname")// ?projeto=ERP
    public ResponseEntity<ProjetoModel> readByName(@RequestParam String projeto){
        return ResponseEntity.status(200).body(service.readByName(projeto));
    }

    @PutMapping("/projeto/update/{id}")
    public ResponseEntity<ProjetoModel> updateProjeto(@PathVariable Integer id, @RequestBody ProjetoModel projeto){
        return ResponseEntity.status(200).body(service.updateById(id, projeto));
    }

    @DeleteMapping("/projeto/delete/{id}")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.status(204).build();
    }


}
