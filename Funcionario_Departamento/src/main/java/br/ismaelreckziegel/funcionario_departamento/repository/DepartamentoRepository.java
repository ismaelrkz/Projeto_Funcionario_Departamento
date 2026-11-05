package br.ismaelreckziegel.funcionario_departamento.repository;

import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartamentoRepository extends ListCrudRepository<DepartamentoModel, Integer> {

    @Query("""
        SELECT d FROM DepartamentoModel d 
        WHERE REPLACE(LOWER(d.nomeDepartamento), ' ', '') 
        LIKE LOWER(CONCAT('%', REPLACE(:departamento, ' ', ''), '%'))
    """)
    public List<DepartamentoModel> findByNomeDepartamentoContaining(@Param("departamento") String departamento);

    @Query("""
        SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END
        FROM DepartamentoModel d
        WHERE REPLACE(LOWER(d.nomeDepartamento), ' ', '')
            = REPLACE(LOWER(:departamento), ' ', '')
""")
    boolean existsByNomeDepartamento(@Param("departamento") String nomeDepartamento);

    @Query("""
        SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END
        FROM DepartamentoModel d
        WHERE REPLACE(LOWER(d.nomeDepartamento), ' ', '')
            = REPLACE(LOWER(:departamento), ' ', '')
        AND d.idDepartamento <> :id
""")
    boolean existsByNomeDepartamentoAndIdDepartamentoNot(
            @Param("departamento") String nomeDepartamento,
            @Param("id") Integer idDepartamento
    );
}
