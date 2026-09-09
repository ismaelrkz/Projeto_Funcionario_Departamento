package br.ismaelreckziegel.funcionario_departamento.controller;

import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import br.ismaelreckziegel.funcionario_departamento.service.DepartamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartamentoController {

    private final DepartamentoService service;

    public DepartamentoController(DepartamentoService service) {
        this.service = service;
    }

    @PostMapping("/novodepartamento")
    public ResponseEntity<DepartamentoModel> createDepartamento(@RequestBody DepartamentoModel novoDepartamento){
        return ResponseEntity.status(201).body(service.create(novoDepartamento));
    }

    @GetMapping("/departamentos")
    public ResponseEntity<List<DepartamentoModel>> readAllDepartamentos(){
        return ResponseEntity.status(200).body(service.readAll());
    }

    @GetMapping("/departamentos/searchid/{id}")
    public ResponseEntity<DepartamentoModel> readDepartamento(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readById(id));
    }

    @GetMapping("/departamentos/search")
    public ResponseEntity<DepartamentoModel> readDepartamento(@RequestParam String departamento){
        return ResponseEntity.status(200).body(service.readByName(departamento));
    }

    @PutMapping("/departamento/update/{id}")
    public ResponseEntity<DepartamentoModel> updateDepartamento(@PathVariable Integer id, @RequestBody DepartamentoModel departamento){
        return ResponseEntity.status(200).body(service.updateById(id, departamento));
    }

    @DeleteMapping("/departamento/delete/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
