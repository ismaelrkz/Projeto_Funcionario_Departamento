package br.ismaelreckziegel.funcionario_departamento.dto.funcionario;

import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;

public record FuncionarioSimplesDTO(Integer id,
                                    String nome) {
    public FuncionarioSimplesDTO(FuncionarioModel funcionario) {
        this(
                funcionario.getIdFuncionario(),
                funcionario.getNomeFuncionario());
    }
}
