package br.ismaelreckziegel.funcionario_departamento.repo;

import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import org.springframework.data.repository.ListCrudRepository;

public interface DepartamentoRepo extends ListCrudRepository<DepartamentoModel, Integer> {
}
