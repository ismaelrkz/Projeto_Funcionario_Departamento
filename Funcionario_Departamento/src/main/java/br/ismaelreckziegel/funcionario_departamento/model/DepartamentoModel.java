package br.ismaelreckziegel.funcionario_departamento.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_departamento")
public class DepartamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_depto")
    private Integer idDepartamento;

    @Column(name = "nome_depto")
    private String nomeDepartamento;

    @OneToMany(mappedBy = "departamentoFuncionario")
    private List<FuncionarioModel> listaFuncionarios = new ArrayList<>();

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }

    public List<FuncionarioModel> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(List<FuncionarioModel> listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }
}
