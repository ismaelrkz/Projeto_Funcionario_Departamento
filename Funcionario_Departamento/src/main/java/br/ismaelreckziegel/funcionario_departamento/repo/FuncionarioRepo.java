package br.ismaelreckziegel.funcionario_departamento.repo;

import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface FuncionarioRepo extends ListCrudRepository<FuncionarioModel, Integer> {

    public Optional<FuncionarioModel> findByNomeFuncionario(String funcionario);

}
