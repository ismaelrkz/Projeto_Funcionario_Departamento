package br.ismaelreckziegel.funcionario_departamento.dto.funcionario;

public record FuncionarioRequestDTO(String nomeFuncionario,
                                    Double salarioFuncionario,
                                    Integer departamentoFuncionario,
                                    Integer supervisor) {
}
