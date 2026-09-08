package br.ismaelreckziegel.funcionario_departamento.repo;

import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface DepartamentoRepo extends ListCrudRepository<DepartamentoModel, Integer> {

    public Optional<DepartamentoModel> findByNomeDepartamento(String departamento);

}
