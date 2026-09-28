package br.ismaelreckziegel.funcionario_departamento.dto.projeto;

import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioSimplesDTO;
import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;

import java.time.LocalDate;
import java.util.List;

public record ProjetoEquipeDTO(Integer id,
                               String nome,
                               LocalDate data) {
    public ProjetoEquipeDTO(ProjetoModel model) {
        this(
                model.getIdProjeto(),
                model.getNomeProjeto(),
                model.getDataInicio());
    }
    /*TODO: trazer valores equipeProjeto para este DTO !!!*/
}
