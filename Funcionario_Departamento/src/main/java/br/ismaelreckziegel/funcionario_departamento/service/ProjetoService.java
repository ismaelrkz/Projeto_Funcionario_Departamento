package br.ismaelreckziegel.funcionario_departamento.service;

import br.ismaelreckziegel.funcionario_departamento.exceptions.BadRequestException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.ConflictException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.NotFoundException;
import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import br.ismaelreckziegel.funcionario_departamento.repo.FuncionarioRepo;
import br.ismaelreckziegel.funcionario_departamento.repo.ProjetoRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjetoService {

    private final ProjetoRepo projetoRepo;
    private final FuncionarioRepo funcionarioRepo;

    public ProjetoService(ProjetoRepo projetoRepo, FuncionarioRepo funcionarioRepo) {
        this.projetoRepo = projetoRepo;
        this.funcionarioRepo = funcionarioRepo;
    }

    public ProjetoModel create(ProjetoModel projeto){

        if(projeto.getNomeProjeto() == null || projeto.getNomeProjeto().isBlank()){
            throw new BadRequestException("Nome do Projeto é obrigatório!");
        }

        Optional<ProjetoModel> existingNome = projetoRepo.findByNomeProjeto(projeto.getNomeProjeto());
        if(existingNome.isPresent()){
            throw new ConflictException("Projeto já criado na base de dados!");
        }

        if(projeto.getEquipeProjeto() != null && !projeto.getEquipeProjeto().isEmpty()){
            for(FuncionarioModel func : projeto.getEquipeProjeto()){
                if(func.getIdFuncionario() == null || !funcionarioRepo.existsById(func.getIdFuncionario())){
                   throw new NotFoundException("Funcionario de ID" + func.getIdFuncionario() + "não existe na equipe.");
                }
            }
        }

        return projetoRepo.save(projeto);
    }

    public ProjetoModel readById(Integer id){
        return projetoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));
    }

    public ProjetoModel readByNome(String projeto){
        return projetoRepo.findByNomeProjeto(projeto)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));
    }

    public ProjetoModel updateById(Integer id, ProjetoModel updateProjeto){
        ProjetoModel existing = projetoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        if(updateProjeto.getNomeProjeto() != null && !updateProjeto.getNomeProjeto().isBlank()){

            Optional<ProjetoModel> existingNome = projetoRepo.findByNomeProjeto(updateProjeto.getNomeProjeto());

            if(existingNome.isPresent() && !existingNome.get().getIdProjeto().equals(id)){
                throw new ConflictException("Já existe outro projeto com o nome informado!");
            }

            existing.setNomeProjeto(updateProjeto.getNomeProjeto());
        }

        if(updateProjeto.getDataInicio() != null){
            existing.setDataInicio(updateProjeto.getDataInicio());
        }

        return projetoRepo.save(existing);
    }

    //TODO: criar feature para adicionar membro a equipe de um projeto já existente [updateEquipe].

    public void deleteById(Integer id){
        if(!projetoRepo.existsById(id)){
            throw new NotFoundException("Projeto não encontrado!");
        }
        projetoRepo.deleteById(id);
    }

}
