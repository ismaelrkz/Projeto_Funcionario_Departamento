package br.ismaelreckziegel.funcionario_departamento.repo;

import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepo extends ListCrudRepository<FuncionarioModel, Integer> {

    public Optional<FuncionarioModel> findByNomeFuncionario(String funcionario);

}
