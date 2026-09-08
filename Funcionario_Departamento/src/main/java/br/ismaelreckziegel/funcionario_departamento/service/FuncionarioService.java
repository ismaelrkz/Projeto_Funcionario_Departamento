package br.ismaelreckziegel.funcionario_departamento.service;

import br.ismaelreckziegel.funcionario_departamento.exceptions.BadRequestException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.ConflictException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.NotFoundException;
import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.repo.DepartamentoRepo;
import br.ismaelreckziegel.funcionario_departamento.repo.FuncionarioRepo;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepo funcionarioRepo;
    private final DepartamentoRepo departamentoRepo;

    public FuncionarioService(FuncionarioRepo funcionarioRepo, DepartamentoRepo departamentoRepo) {
        this.funcionarioRepo = funcionarioRepo;
        this.departamentoRepo = departamentoRepo;
    }

    public FuncionarioModel create(FuncionarioModel funcionario){

        if(funcionario.getNomeFuncionario() == null || funcionario.getNomeFuncionario().isBlank() ){
            throw new BadRequestException("Nome do funcionário obrigatório!");
        }

        Optional<FuncionarioModel> existingFunc = funcionarioRepo.findByNomeFuncionario(funcionario.getNomeFuncionario());
        if(existingFunc.isPresent()){ // Funcionário
            throw new ConflictException("Funcionário já existente");
        }

        if(funcionario.getDepartamentoFuncionario() != null && funcionario.getDepartamentoFuncionario().getIdDepartamento() != null){
            Integer idDepto = funcionario.getDepartamentoFuncionario().getIdDepartamento();
            Optional<DepartamentoModel> existingDepart = departamentoRepo.findById(idDepto);
            if(existingDepart.isEmpty()){ //Departamento
                throw new NotFoundException("Departamento informado não existe!");
            }
        }

        if(funcionario.getSupervisor() != null && funcionario.getSupervisor().getIdFuncionario() != null){
            Integer idSup = funcionario.getSupervisor().getIdFuncionario();
            Optional<FuncionarioModel> existingSup = funcionarioRepo.findById(idSup);
            if(existingSup.isEmpty()){ // Supervisor
                throw new NotFoundException("Supervisor informado não existe!");
            }
        }

        return funcionarioRepo.save(funcionario);
    }

    public FuncionarioModel readById(Integer id){
        return funcionarioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Funcionario inexistente!"));
    }

    public FuncionarioModel readByName(String funcionario){
        return funcionarioRepo.findByNomeFuncionario(funcionario)
                .orElseThrow(() -> new NotFoundException("Funcionário inexistente!"));
    }

    //TODO: criar feature que retorne os projetos que Funcionario está vinculado [tbl_funcionario_projeto]

    public FuncionarioModel updateById(Integer id, FuncionarioModel updateFuncionario) {
        FuncionarioModel existing = funcionarioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado!"));

        if(updateFuncionario.getNomeFuncionario() != null && !updateFuncionario.getNomeFuncionario().isBlank()){
            existing.setNomeFuncionario(updateFuncionario.getNomeFuncionario());
        }

        if(updateFuncionario.getSalarioFuncionario() != null && updateFuncionario.getSalarioFuncionario() > 0){
            existing.setSalarioFuncionario(updateFuncionario.getSalarioFuncionario());
        }

        if(updateFuncionario.getDepartamentoFuncionario() != null && updateFuncionario.getDepartamentoFuncionario().getIdDepartamento() != null){
            Integer idDepto = updateFuncionario.getDepartamentoFuncionario().getIdDepartamento();
            DepartamentoModel deptoValido = departamentoRepo.findById(idDepto)
                    .orElseThrow(() -> new NotFoundException("Departamento informado não existe!"));
            existing.setDepartamentoFuncionario(deptoValido);
        }

        if(updateFuncionario.getSupervisor() != null && updateFuncionario.getSupervisor().getIdFuncionario() != null){
            Integer idSup = updateFuncionario.getSupervisor().getIdFuncionario();
            if(idSup.equals(id)){
                throw new ConflictException("Um funcionário não pode ser supervisor de si mesmo!");
            }
            FuncionarioModel supValido = funcionarioRepo.findById(idSup)
                    .orElseThrow(() -> new NotFoundException("Supervisor informado não existe!"));
            existing.setSupervisor(supValido);
        }

        return funcionarioRepo.save(existing);
    }

    public void deleteById(Integer id){
        if(!funcionarioRepo.existsById(id)){
            throw new NotFoundException("Funcionário não encontrado!");
        }

        funcionarioRepo.deleteById(id);
    }
}
