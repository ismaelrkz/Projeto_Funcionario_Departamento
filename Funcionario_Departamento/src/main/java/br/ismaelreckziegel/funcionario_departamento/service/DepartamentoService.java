package br.ismaelreckziegel.funcionario_departamento.service;

import br.ismaelreckziegel.funcionario_departamento.dto.departamento.DepartamentoDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.departamento.DepartamentoRequestDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioSimplesDTO;
import br.ismaelreckziegel.funcionario_departamento.exceptions.BadRequestException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.ConflictException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.NotFoundException;
import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import br.ismaelreckziegel.funcionario_departamento.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public DepartamentoDTO create(DepartamentoRequestDTO departamento){
        DepartamentoModel novoDepartamento = new DepartamentoModel();

        if(departamento.nomeDepartamento() == null || departamento.nomeDepartamento().isBlank()){
            throw new BadRequestException("Nome do Departamento é obrigatório!");
        }

        if(departamentoRepository.existsByNomeDepartamento(departamento.nomeDepartamento())){
            throw new ConflictException("Departamento já existente!");
        }

        novoDepartamento.setNomeDepartamento(departamento.nomeDepartamento());

        DepartamentoModel departamentoSalvo = departamentoRepository.save(novoDepartamento);

        return new DepartamentoDTO(departamentoSalvo);
    }

    public List<DepartamentoDTO> readAll(){
        List<DepartamentoModel> departamentos = departamentoRepository.findAll();

        return departamentos.stream().map(DepartamentoDTO::new).toList();
    }

    public DepartamentoDTO readById(Integer id){
        return departamentoRepository.findById(id).map(DepartamentoDTO::new)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado!"));
    }

    public List<DepartamentoDTO> readByName(String name){
        List<DepartamentoModel> departamentos = departamentoRepository.findByNomeDepartamentoContaining(name);

        if(departamentos.isEmpty()){
                throw new NotFoundException("Departamento não encontrado!");
        }

        return departamentos.stream().map(DepartamentoDTO::new).toList();
    }

    public List<FuncionarioSimplesDTO> readFuncionariosDepartamento(Integer id){
        DepartamentoModel existing = departamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado!"));

        return existing.getListaFuncionarios().stream().map(FuncionarioSimplesDTO::new).toList();
    }

    public DepartamentoDTO updateById(Integer id, DepartamentoRequestDTO updateDepartamento){
        DepartamentoModel existing = departamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ID departamento inexistente!"));

        if(updateDepartamento.nomeDepartamento() != null && !updateDepartamento.nomeDepartamento().isBlank()){

            if(departamentoRepository
                    .existsByNomeDepartamentoAndIdDepartamentoNot(
                            updateDepartamento.nomeDepartamento(),
                            id)){
                throw new ConflictException("Já existe um Departamento com o nome informado");
            }
            existing.setNomeDepartamento(updateDepartamento.nomeDepartamento());
        }

        DepartamentoModel departamentoSalvo = departamentoRepository.save(existing);
        return new DepartamentoDTO(departamentoSalvo);
    }

    public void deleteById(Integer id){
        if(!departamentoRepository.existsById(id)){
            throw new NotFoundException("ID departamento inexistente!");
        }
        departamentoRepository.deleteById(id);
    }

}