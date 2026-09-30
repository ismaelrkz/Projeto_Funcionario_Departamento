package br.ismaelreckziegel.funcionario_departamento.service;

import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioRequestDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoDTO;
import br.ismaelreckziegel.funcionario_departamento.exceptions.BadRequestException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.ConflictException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.NotFoundException;
import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import br.ismaelreckziegel.funcionario_departamento.repository.DepartamentoRepository;
import br.ismaelreckziegel.funcionario_departamento.repository.FuncionarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final DepartamentoRepository departamentoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, DepartamentoRepository departamentoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public FuncionarioDTO create(FuncionarioRequestDTO funcionario){
        FuncionarioModel novoFuncionario = new FuncionarioModel();

        if (funcionario.nomeFuncionario() == null || funcionario.nomeFuncionario().isBlank()) {
            throw new BadRequestException("Nome do funcionário obrigatório!");
        }

        Optional<FuncionarioModel> existingFunc = funcionarioRepository.findByNomeFuncionario(funcionario.nomeFuncionario());
        if (existingFunc.isPresent()) {
            throw new ConflictException("Funcionário já existente!");
        }
        novoFuncionario.setNomeFuncionario(funcionario.nomeFuncionario());

        if (funcionario.salarioFuncionario() == null || funcionario.salarioFuncionario() <= 0) {
            throw new BadRequestException("Salário é obrigatório e deve ser positivo!");
        }
        novoFuncionario.setSalarioFuncionario(funcionario.salarioFuncionario());

        if (funcionario.departamentoFuncionario() != null) {
            DepartamentoModel existing = departamentoRepository.findById(funcionario.departamentoFuncionario())
                    .orElseThrow(() -> new NotFoundException("Departamento não encontrado!"));

            novoFuncionario.setDepartamentoFuncionario(existing);
        }

        if (funcionario.supervisor() != null) {
            FuncionarioModel existing = funcionarioRepository.findById(funcionario.supervisor())
                    .orElseThrow(() -> new NotFoundException("Supervisor não encontrado!"));

            novoFuncionario.setSupervisor(existing);
        }

        FuncionarioModel salvo = funcionarioRepository.save(novoFuncionario);

        return new FuncionarioDTO(salvo);
    }

    public List<FuncionarioDTO> readAll(){
        List<FuncionarioModel> funcionarios = funcionarioRepository.findAll();

        return funcionarios.stream().map(FuncionarioDTO::new).toList();
    }

    public FuncionarioDTO readById(Integer id){
        FuncionarioModel existing = funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Funcionario inexistente!"));

        return new FuncionarioDTO(existing);
    }

    public FuncionarioDTO readByName(String name){
        FuncionarioModel existing = funcionarioRepository.findByNomeFuncionario(name)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado!"));

        return new FuncionarioDTO(existing);
    }

    public List<ProjetoDTO> readProjetosFuncionario(Integer id){
        FuncionarioModel existing = funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado!"));

        List<ProjetoModel> projetos = existing.getProjetosFuncionario();

        return projetos.stream().map(ProjetoDTO::new).toList();
    }

    public FuncionarioDTO updateById(Integer id, FuncionarioRequestDTO updateFuncionario){
        FuncionarioModel existing = funcionarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado!"));

        if(updateFuncionario.nomeFuncionario() != null && !updateFuncionario.nomeFuncionario().isBlank()){
            existing.setNomeFuncionario(updateFuncionario.nomeFuncionario());
        }

        if(updateFuncionario.salarioFuncionario() != null && updateFuncionario.salarioFuncionario() > 0){
            existing.setSalarioFuncionario(updateFuncionario.salarioFuncionario());
        }

        if(updateFuncionario.departamentoFuncionario() != null){
            Integer idDepto = updateFuncionario.departamentoFuncionario();
            DepartamentoModel existingDepart = departamentoRepository.findById(idDepto)
                    .orElseThrow(() -> new NotFoundException("Departamento informado não existe!"));
            existing.setDepartamentoFuncionario(existingDepart);
        }

        if(updateFuncionario.supervisor() != null){
            Integer idSup = updateFuncionario.supervisor();
            if(idSup.equals(id)){
                throw new ConflictException("Um funcionário não pode ser supervisor de si mesmo!");
            }
            FuncionarioModel supValido = funcionarioRepository.findById(idSup)
                    .orElseThrow(() -> new NotFoundException("Supervisor informado não existe!"));
            existing.setSupervisor(supValido);
        }

        FuncionarioModel funcionarioUpdate = funcionarioRepository.save(existing);

        return new FuncionarioDTO(funcionarioUpdate);
    }

    public void deleteById(Integer id){
        if(!funcionarioRepository.existsById(id)){
            throw new NotFoundException("Funcionário não encontrado!");
        }

        funcionarioRepository.deleteById(id);
    }
}
