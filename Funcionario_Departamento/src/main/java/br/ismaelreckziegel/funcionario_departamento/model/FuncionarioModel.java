package br.ismaelreckziegel.funcionario_departamento.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_funcionario")
public class FuncionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_func")
    private Integer idFuncionario;

    @Column(name = "nome_func", length = 50)
    private String nomeFuncionario;

    @Column(name = "salario_func")
    private Double salarioFuncionario;

    @ManyToOne
    @JoinColumn(name = "depto_func")
    @JsonIgnoreProperties("listaFuncionarios")
    private DepartamentoModel departamentoFuncionario; //relação 1:N

    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    @JsonIgnoreProperties({"supervisor", "departamentoFuncionario"})
    private FuncionarioModel supervisor;

    //TODO: criar feature que retorne os projetos que FuncionarioModel está vinculado

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public Double getSalarioFuncionario() {
        return salarioFuncionario;
    }

    public void setSalarioFuncionario(Double salarioFuncionario) {
        this.salarioFuncionario = salarioFuncionario;
    }

    public DepartamentoModel getDepartamentoFuncionario() {
        return departamentoFuncionario;
    }

    public void setDepartamentoFuncionario(DepartamentoModel departamentoFuncionario) {
        this.departamentoFuncionario = departamentoFuncionario;
    }

    public FuncionarioModel getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(FuncionarioModel supervisor) {
        this.supervisor = supervisor;
    }
}
