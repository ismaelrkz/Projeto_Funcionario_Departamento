package br.ismaelreckziegel.funcionario_departamento.repo;

import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjetoRepo extends ListCrudRepository<ProjetoModel, Integer> {

    @Query("""
        SELECT d FROM ProjetoModel d 
        WHERE REPLACE(LOWER(d.nomeProjeto), ' ', '') 
        LIKE LOWER(CONCAT('%', REPLACE(:projeto, ' ', ''), '%'))
    """)
    public Optional<ProjetoModel> findByNomeProjeto(@Param("projeto") String projeto);

}
