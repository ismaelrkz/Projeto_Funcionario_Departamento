package br.ismaelreckziegel.funcionario_departamento.dto.projeto;

import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioSimplesDTO;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;

import java.time.LocalDate;
import java.util.List;

public record ProjetoEquipeDTO(Integer id,
                               String nome,
                               LocalDate data,
                               List<FuncionarioSimplesDTO> listaEquipe) {
    public ProjetoEquipeDTO(ProjetoModel projeto) {
        this(
                projeto.getIdProjeto(),
                projeto.getNomeProjeto(),
                projeto.getDataInicio(),
                projeto.getEquipeProjeto() != null
                        ? projeto.getEquipeProjeto().stream().map(FuncionarioSimplesDTO::new).toList()
                        : List.of()); //TODO: estudar estrutura
    }
}
