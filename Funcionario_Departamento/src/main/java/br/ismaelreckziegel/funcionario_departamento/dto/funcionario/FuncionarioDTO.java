package br.ismaelreckziegel.funcionario_departamento.dto.funcionario;

import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;

public record FuncionarioDTO(
        Integer idFuncionario,
        String nomeFuncionario,
        Double salarioFuncionario,
        String nomeDepartamento,
        String nomeSupervisor
) {
    public FuncionarioDTO(FuncionarioModel model) {
        this(
                model.getIdFuncionario(),
                model.getNomeFuncionario(),
                model.getSalarioFuncionario(),
                model.getDepartamentoFuncionario() != null //TODO: estudar estrutura
                        ? model.getDepartamentoFuncionario().getNomeDepartamento() : null,
                model.getSupervisor() != null //TODO: estudar estrutura
                        ? model.getSupervisor().getNomeFuncionario() : null
        );
    }
}
