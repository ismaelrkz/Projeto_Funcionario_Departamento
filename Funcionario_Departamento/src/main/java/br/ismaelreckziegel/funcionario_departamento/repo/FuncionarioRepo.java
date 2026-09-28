package br.ismaelreckziegel.funcionario_departamento.repo;

import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepo extends ListCrudRepository<FuncionarioModel, Integer> {

    @Query("""
        SELECT d FROM FuncionarioModel d 
        WHERE REPLACE(LOWER(d.nomeFuncionario), ' ', '') 
        LIKE LOWER(CONCAT('%', REPLACE(:funcionario, ' ', ''), '%'))
    """)
    public Optional<FuncionarioModel> findByNomeFuncionario(@Param("funcionario") String funcionario);

}
