package br.ismaelreckziegel.funcionario_departamento.controller;

import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoDTO;
import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import br.ismaelreckziegel.funcionario_departamento.service.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping("/novofuncionario")
    public ResponseEntity<FuncionarioModel> create(@RequestBody FuncionarioModel novoFuncionario){
        return ResponseEntity.status(201).body(service.create(novoFuncionario));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioModel>> readAll(){
        return ResponseEntity.status(200).body(service.readAll());
    }

    @GetMapping("/funcionario/searchid/{id}")
    public ResponseEntity<FuncionarioModel> readById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readById(id));
    }

    @GetMapping("/funcionario/searchname") // ?name=João
    public ResponseEntity<FuncionarioModel> readByName(@RequestParam String funcionario){
        return ResponseEntity.status(200).body(service.readByName(funcionario));
    }

    @GetMapping("funcionario/projetos/{id}")
    public ResponseEntity<List<ProjetoDTO>> readProjetosFuncionario(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readProjetosFuncionario(id));
    }

    @PutMapping("/funcionario/update/{id}")
    public ResponseEntity<FuncionarioModel> updateFuncionario(@PathVariable Integer id, @RequestBody FuncionarioModel funcionario){
        return ResponseEntity.status(204).body(service.updateById(id, funcionario));
    }

    @DeleteMapping("/funcionario/delete/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
