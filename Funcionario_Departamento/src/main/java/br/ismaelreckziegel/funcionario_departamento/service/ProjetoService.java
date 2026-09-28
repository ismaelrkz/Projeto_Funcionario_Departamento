package br.ismaelreckziegel.funcionario_departamento.service;

import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoEquipeDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoRequestEquipeDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoRequestDTO;
import br.ismaelreckziegel.funcionario_departamento.exceptions.BadRequestException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.ConflictException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.NotFoundException;
import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import br.ismaelreckziegel.funcionario_departamento.repo.FuncionarioRepo;
import br.ismaelreckziegel.funcionario_departamento.repo.ProjetoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    private final ProjetoRepo projetoRepo;
    private final FuncionarioRepo funcionarioRepo;

    public ProjetoService(ProjetoRepo projetoRepo, FuncionarioRepo funcionarioRepo) {
        this.projetoRepo = projetoRepo;
        this.funcionarioRepo = funcionarioRepo;
    }

    public ProjetoDTO create(ProjetoRequestDTO projeto) {
        ProjetoModel novoProjeto = new ProjetoModel();

        if (projeto.nome() == null || projeto.nome().isBlank()) {
            throw new BadRequestException("Nome do Projeto é obrigatório!");
        }

        Optional<ProjetoModel> existingNome = projetoRepo.findByNomeProjeto(projeto.nome());
        if (existingNome.isPresent()) {
            throw new ConflictException("Projeto já criado na base de dados!");
        }
        novoProjeto.setNomeProjeto(projeto.nome());

        if(projeto.data() == null){
            throw new BadRequestException("Data inicial do projeto obrigatório");
        }
        novoProjeto.setDataInicio(projeto.data());

        ProjetoModel projetoSalvo = projetoRepo.save(novoProjeto);

        return new ProjetoDTO(projetoSalvo);
    }

    public List<ProjetoDTO> readAll(){
        List<ProjetoModel> projetos = projetoRepo.findAll();

        return projetos.stream().map(ProjetoDTO::new).toList();
    }

    public ProjetoEquipeDTO readById(Integer id){
        ProjetoModel existing = projetoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        return new ProjetoEquipeDTO(existing);
    }

    public ProjetoEquipeDTO readByName(String projeto){
        ProjetoModel existing = projetoRepo.findByNomeProjeto(projeto)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        return new ProjetoEquipeDTO(existing);
    }

    public ProjetoDTO updateById(Integer id, ProjetoRequestDTO updateProjeto){
        ProjetoModel existing = projetoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        if(updateProjeto.nome() != null && !updateProjeto.nome().isBlank()){

            Optional<ProjetoModel> existingNome = projetoRepo.findByNomeProjeto(updateProjeto.nome());

            if(existingNome.isPresent() && !existingNome.get().getIdProjeto().equals(id)){
                throw new ConflictException("Já existe outro projeto com o nome informado!");
            }

            existing.setNomeProjeto(updateProjeto.nome());
        }

        if(updateProjeto.data() != null){
            existing.setDataInicio(updateProjeto.data());
        }

        ProjetoModel projetoUpdate = projetoRepo.save(existing);

        return new ProjetoDTO(projetoUpdate);
    }

    public ProjetoDTO updateEquipeById(Integer id, ProjetoRequestEquipeDTO updateEquipeProjeto){

        ProjetoModel existing = projetoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        if (updateEquipeProjeto.id() != null && !updateEquipeProjeto.id().isEmpty()) {

            List<Integer> ids = updateEquipeProjeto.id();

            List<FuncionarioModel> funcionariosDoBanco = funcionarioRepo.findAllById(ids);

            if (funcionariosDoBanco.size() != ids.size()) {
                throw new NotFoundException("Um ou mais funcionários informados para a equipe não foram encontrados!");
            }

            for (FuncionarioModel novoFuncionario : funcionariosDoBanco) {
                if (!existing.getEquipeProjeto().contains(novoFuncionario)) {
                    existing.getEquipeProjeto().add(novoFuncionario);
                }
            }
        }

        ProjetoModel equipeUpdate = projetoRepo.save(existing);

        return new ProjetoDTO(equipeUpdate);
    }

    public void deleteById(Integer id){
        if(!projetoRepo.existsById(id)){
            throw new NotFoundException("Projeto não encontrado!");
        }

        projetoRepo.deleteById(id);
    }

}
