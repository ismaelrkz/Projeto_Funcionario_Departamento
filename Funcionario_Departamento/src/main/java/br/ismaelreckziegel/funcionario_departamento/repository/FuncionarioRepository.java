package br.ismaelreckziegel.funcionario_departamento.repository;

import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends ListCrudRepository<FuncionarioModel, Integer> {

    @Query("""
        SELECT d FROM FuncionarioModel d 
        WHERE REPLACE(LOWER(d.nomeFuncionario), ' ', '') 
        LIKE LOWER(CONCAT('%', REPLACE(:funcionario, ' ', ''), '%'))
    """)
    public List<FuncionarioModel> findByNomeFuncionarioContaining(@Param("funcionario") String funcionario);

    @Query("""
        SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
        FROM FuncionarioModel f
        WHERE REPLACE(LOWER(f.nomeFuncionario), ' ', '')
            = REPLACE(LOWER(:funcionario), ' ', '')
""")
    boolean existsByNomeFuncionario(@Param("funcionario") String nomeFuncionario);

    @Query("""
        SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
        FROM FuncionarioModel f
        WHERE REPLACE(LOWER(f.nomeFuncionario), ' ', '')
            = REPLACE(LOWER(:funcionario), ' ', '')
        AND f.idFuncionario <> :id
""")
    boolean existsByNomeFuncionarioAndIdFuncionarioNot(
            @Param("funcionario") String nomeFuncionario,
            @Param("id") Integer idFuncionario
    );

}
