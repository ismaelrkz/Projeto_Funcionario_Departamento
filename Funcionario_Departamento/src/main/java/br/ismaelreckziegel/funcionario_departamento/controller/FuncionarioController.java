package br.ismaelreckziegel.funcionario_departamento.controller;

import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioRequestDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoDTO;
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
    public ResponseEntity<FuncionarioDTO> create(@RequestBody FuncionarioRequestDTO novoFuncionario){
        return ResponseEntity.status(201).body(service.create(novoFuncionario));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioDTO>> readAll(){
        return ResponseEntity.status(200).body(service.readAll());
    }

    @GetMapping("/funcionario/search/{id}")
    public ResponseEntity<FuncionarioDTO> readById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readById(id));
    }

    @GetMapping("/funcionario/search")/*?name="valor"*/
    public ResponseEntity<FuncionarioDTO> readByName(@RequestParam String name){
        return ResponseEntity.status(200).body(service.readByName(name));
    }

    @GetMapping("/funcionario/{id}/projetos")
    public ResponseEntity<List<ProjetoDTO>> readProjetosFuncionario(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readProjetosFuncionario(id));
    }

    @PutMapping("/funcionario/{id}/update")
    public ResponseEntity<FuncionarioDTO> updateFuncionario(@PathVariable Integer id, @RequestBody FuncionarioRequestDTO funcionario){
        return ResponseEntity.status(204).body(service.updateById(id, funcionario));
    }

    @DeleteMapping("/funcionario/{id}/delete")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
