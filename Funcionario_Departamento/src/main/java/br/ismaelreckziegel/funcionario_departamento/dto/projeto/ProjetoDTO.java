package br.ismaelreckziegel.funcionario_departamento.dto.projeto;

import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;

import java.time.LocalDate;

public record ProjetoDTO(Integer id,
                         String nome,
                         LocalDate data) {
    public ProjetoDTO(ProjetoModel model) {
        this(
                model.getIdProjeto(),
                model.getNomeProjeto(),
                model.getDataInicio()
                );
    }
}
