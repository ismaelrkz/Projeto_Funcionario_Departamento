package br.ismaelreckziegel.funcionario_departamento.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbl_departamento")
public class DepartamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_depto")
    private Integer idDepartamento;

    @Column(name = "nome_depto", length = 50)
    private String nomeDepartamento;

    @OneToMany(mappedBy = "departamentoFuncionario", cascade = CascadeType.DETACH)
    @JsonIgnoreProperties("departamentoFuncionario")
    private List<FuncionarioModel> listaFuncionarios;

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
