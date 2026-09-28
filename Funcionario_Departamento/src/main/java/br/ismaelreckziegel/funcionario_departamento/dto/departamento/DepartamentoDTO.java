package br.ismaelreckziegel.funcionario_departamento.dto.departamento;

import br.ismaelreckziegel.funcionario_departamento.dto.funcionario.FuncionarioSimplesDTO;
import br.ismaelreckziegel.funcionario_departamento.model.DepartamentoModel;

import java.util.List;

public record DepartamentoDTO(Integer idDepartamento,
                              String nomeDepartamento,
                              List<FuncionarioSimplesDTO> listaFuncionarios) {
    public DepartamentoDTO(DepartamentoModel departamento) {
        this(
                departamento.getIdDepartamento(),
                departamento.getNomeDepartamento(),
                departamento.getListaFuncionarios() != null
                        ? departamento.getListaFuncionarios().stream().map(FuncionarioSimplesDTO::new).toList()
                        : List.of() //TODO: estudar estrutura [departamento.getListaFuncionarios() != null...]
        );
    }
}
