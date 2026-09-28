package br.ismaelreckziegel.funcionario_departamento.controller;

import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoEquipeDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoRequestEquipeDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoRequestDTO;
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
    public ResponseEntity<ProjetoDTO> create(@RequestBody ProjetoRequestDTO novoProjeto){
        return ResponseEntity.status(201).body(service.create(novoProjeto));
    }

    @GetMapping("/projeto/search")
    public ResponseEntity<List<ProjetoDTO>> readAll(){
        return ResponseEntity.status(200).body(service.readAll());
    }

    @GetMapping("/projeto/search/{id}")
    public ResponseEntity<ProjetoEquipeDTO> readById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readById(id));
    }

    @GetMapping("/projeto/searchname")/*?projeto=ERP*/
    public ResponseEntity<ProjetoEquipeDTO> readByName(@RequestParam String projeto){
        return ResponseEntity.status(200).body(service.readByName(projeto));
    }

    @PutMapping("/projeto/{id}/update")
    public ResponseEntity<ProjetoDTO> updateProjeto(@PathVariable Integer id, @RequestBody ProjetoRequestDTO projeto){
        return ResponseEntity.status(200).body(service.updateById(id, projeto));
    }

    @PutMapping("/projeto/{id}/update/equipe")
    public ResponseEntity<ProjetoEquipeDTO> updateEquipe(@PathVariable Integer id, @RequestBody ProjetoRequestEquipeDTO projeto){
        return ResponseEntity.status(200).body(service.updateEquipeById(id, projeto));
    }

    @DeleteMapping("/projeto/delete/{id}")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.status(204).build();
    }


}
