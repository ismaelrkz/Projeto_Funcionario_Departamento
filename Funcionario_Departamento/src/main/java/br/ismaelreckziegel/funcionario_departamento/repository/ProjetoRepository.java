package br.ismaelreckziegel.funcionario_departamento.repository;

import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjetoRepository extends ListCrudRepository<ProjetoModel, Integer> {

    @Query("""
        SELECT p FROM ProjetoModel p 
        WHERE REPLACE(LOWER(p.nomeProjeto), ' ', '') 
        LIKE LOWER(CONCAT('%', REPLACE(:projeto, ' ', ''), '%'))
    """)
    public List<ProjetoModel> findByNomeProjetoContaining(@Param("projeto") String nomeProjeto);

    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM ProjetoModel p
        WHERE REPLACE(LOWER(p.nomeProjeto), ' ', '')
            = REPLACE(LOWER(:projeto), ' ', '')
""")
    boolean existsByNomeProjeto(@Param("projeto") String nomeProjeto);

    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM ProjetoModel p
        WHERE REPLACE(LOWER(p.nomeProjeto), ' ', '')
            = REPLACE(LOWER(:projeto), ' ', '')
        AND p.idProjeto <> :id
""")
    boolean existsByNomeProjetoAndIdProjetoNot(
            @Param("projeto") String nomeProjeto,
            @Param("id") Integer idProjeto
    );
}
