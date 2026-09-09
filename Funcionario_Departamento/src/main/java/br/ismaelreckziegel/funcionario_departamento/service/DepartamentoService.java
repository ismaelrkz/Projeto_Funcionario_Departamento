package br.ismaelreckziegel.funcionario_departamento.service;

import br.ismaelreckziegel.funcionario_departamento.exceptions.BadRequestException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.ConflictException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.NotFoundException;
import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import br.ismaelreckziegel.funcionario_departamento.repo.DepartamentoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    private final DepartamentoRepo departamentoRepo;

    public DepartamentoService(DepartamentoRepo departamentoRepo) {
        this.departamentoRepo = departamentoRepo;
    }

    public DepartamentoModel create(DepartamentoModel departamento){
        if(departamento.getNomeDepartamento() == null || departamento.getNomeDepartamento().isBlank()){
            throw new BadRequestException("Nome do Departamento é obrigatório!");
        }

        Optional<DepartamentoModel> existing = departamentoRepo.findByNomeDepartamento(departamento.getNomeDepartamento());
        if (existing.isPresent()){
            throw new ConflictException("Departamento já existente!");
        }
        return departamentoRepo.save(departamento);
    }

    public List<DepartamentoModel> readAll(){
        return departamentoRepo.findAll();
    }

    public DepartamentoModel readById(Integer id){
        return departamentoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("ID departamento inexistente!"));
    }

    public DepartamentoModel readByName(String name){
        return departamentoRepo.findByNomeDepartamento(name)
                .orElseThrow(() -> new NotFoundException("Departamento inexistente!"));
    }

    public DepartamentoModel updateById(Integer id, DepartamentoModel updateDepartamento){
        DepartamentoModel existing = departamentoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("ID departamento inexistente!"));

        if(updateDepartamento.getNomeDepartamento() != null && !updateDepartamento.getNomeDepartamento().isBlank()){
            Optional<DepartamentoModel> existingNome = departamentoRepo.findByNomeDepartamento(updateDepartamento.getNomeDepartamento());

            if(existingNome.isPresent() && existingNome.get().getIdDepartamento().equals(id)){
                throw new ConflictException("Já existe um Departamento com o nome informado");
            }
            existing.setNomeDepartamento(updateDepartamento.getNomeDepartamento());
        }

        return departamentoRepo.save(existing);
    }

    public void deleteById(Integer id){
        if(!departamentoRepo.existsById(id)){
            throw new NotFoundException("ID departamento inexistente!");
        }
        departamentoRepo.deleteById(id);
    }

}