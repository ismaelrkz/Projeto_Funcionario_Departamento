package br.ismaelreckziegel.funcionario_departamento.controller;

import br.ismaelreckziegel.funcionario_departamento.dto.departamento.DepartamentoDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.departamento.DepartamentoRequestDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioSimplesDTO;
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

    @PostMapping("/departamentos")
    public ResponseEntity<DepartamentoDTO> createDepartamento(@RequestBody DepartamentoRequestDTO novoDepartamento){
        return ResponseEntity.status(201).body(service.create(novoDepartamento));
    }

    @GetMapping("/departamentos")
    public ResponseEntity<List<DepartamentoDTO>> readAllDepartamentos(){
        return ResponseEntity.status(200).body(service.readAll());
    }

    @GetMapping("/departamentos/search/{id}")
    public ResponseEntity<DepartamentoDTO> readDepartamento(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readById(id));
    }
    @GetMapping("/departamentos/search")/*?departamento="valor"*/
    public ResponseEntity<DepartamentoDTO> readDepartamento(@RequestParam String departamento){
        return ResponseEntity.status(200).body(service.readByName(departamento));
    }
    @GetMapping("/departamentos/{id}/funcionarios")
    public ResponseEntity<List<FuncionarioSimplesDTO>> readFuncionarios(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readFuncionariosDepartamento(id));
    }

    @PutMapping("/departamentos/update/{id}")
    public ResponseEntity<DepartamentoDTO> updateDepartamento(@PathVariable Integer id, @RequestBody DepartamentoRequestDTO departamento){
        return ResponseEntity.status(200).body(service.updateById(id, departamento));
    }

    @DeleteMapping("/departamentos/delete/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
