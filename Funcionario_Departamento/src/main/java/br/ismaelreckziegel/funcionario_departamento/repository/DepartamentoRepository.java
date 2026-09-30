package br.ismaelreckziegel.funcionario_departamento.repository;

import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartamentoRepository extends ListCrudRepository<DepartamentoModel, Integer> {

    @Query("""
        SELECT d FROM DepartamentoModel d 
        WHERE REPLACE(LOWER(d.nomeDepartamento), ' ', '') 
        LIKE LOWER(CONCAT('%', REPLACE(:departamento, ' ', ''), '%'))
    """)
    public Optional<DepartamentoModel> findByNomeDepartamento(@Param("departamento") String departamento);
}
