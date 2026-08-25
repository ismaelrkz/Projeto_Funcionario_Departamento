package br.ismaelreckziegel.funcionario_departamento.repo;

import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import org.springframework.data.repository.ListCrudRepository;

public interface ProjetoRepo extends ListCrudRepository<ProjetoModel, Integer> {
}
