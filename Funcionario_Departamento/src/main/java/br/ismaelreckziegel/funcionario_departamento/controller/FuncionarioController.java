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

    @PostMapping("/funcionarios")
    public ResponseEntity<FuncionarioDTO> createFuncionario(@RequestBody FuncionarioRequestDTO novoFuncionario){
        return ResponseEntity.status(201).body(service.create(novoFuncionario));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioDTO>> readAllFuncionarios(){
        return ResponseEntity.status(200).body(service.readAll());
    }

    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<FuncionarioDTO> readFuncionario(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readById(id));
    }

    @GetMapping("/funcionarios/search")/*?name="valor"*/
    public ResponseEntity<List<FuncionarioDTO>> readFuncionario(@RequestParam String funcionario){
        return ResponseEntity.status(200).body(service.readByName(funcionario));
    }

    @GetMapping("/funcionarios/{id}/projetos")
    public ResponseEntity<List<ProjetoDTO>> readProjetosFuncionario(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readProjetosFuncionario(id));
    }

    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<FuncionarioDTO> updateFuncionario(@PathVariable Integer id, @RequestBody FuncionarioRequestDTO funcionario){
        return ResponseEntity.status(200).body(service.updateById(id, funcionario));
    }

    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
