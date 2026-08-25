package br.ismaelreckziegel.funcionario_departamento.repo;

import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import org.springframework.data.repository.ListCrudRepository;

public interface FuncionarioRepo extends ListCrudRepository<FuncionarioModel, Integer> {
}
