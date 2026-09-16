package br.ismaelreckziegel.funcionario_departamento.dto.projeto;

import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;

import java.time.LocalDate;

public record ProjetoDTO(
        Integer idProjeto,
        String nomeProjeto,
        LocalDate dataInicio
) {

    public ProjetoDTO(ProjetoModel model) {
        this(model.getIdProjeto(), model.getNomeProjeto(), model.getDataInicio());
    }
}
