package br.ismaelreckziegel.funcionario_departamento.controller;

import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoEquipeDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoEquipeRequestDTO;
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

    @PostMapping("/projetos")
    public ResponseEntity<ProjetoDTO> createProjeto(@RequestBody ProjetoRequestDTO novoProjeto){
        return ResponseEntity.status(201).body(service.create(novoProjeto));
    }

    @GetMapping("/projetos")
    public ResponseEntity<List<ProjetoDTO>> readAllProjetos(){
        return ResponseEntity.status(200).body(service.readAll());
    }

    @GetMapping("/projetos/{id}")
    public ResponseEntity<ProjetoEquipeDTO> readProjetos(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.readById(id));
    }

    @GetMapping("/projetos/search")/*?projeto="valor"*/
    public ResponseEntity<List<ProjetoEquipeDTO>> readProjetos(@RequestParam String projeto){
        return ResponseEntity.status(200).body(service.readByName(projeto)); //TODO: atenção nesse teste...!!!
    }

    @PutMapping("/projetos/{id}")
    public ResponseEntity<ProjetoDTO> updateProjeto(@PathVariable Integer id, @RequestBody ProjetoRequestDTO projeto){
        return ResponseEntity.status(200).body(service.updateById(id, projeto));
    }

    @PutMapping("/projetos/equipe/{id}")
    public ResponseEntity<ProjetoEquipeDTO> updateEquipe(@PathVariable Integer id, @RequestBody ProjetoEquipeRequestDTO projeto){
        return ResponseEntity.status(200).body(service.updateEquipeById(id, projeto));
    }

    @DeleteMapping("/projetos/{id}")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.status(204).build();
    }


}
