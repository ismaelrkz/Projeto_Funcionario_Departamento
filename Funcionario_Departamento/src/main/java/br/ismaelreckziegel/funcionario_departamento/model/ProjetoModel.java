package br.ismaelreckziegel.funcionario_departamento.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_projeto")
public class ProjetoModel {

    @Id
    @Column(name = "id_projeto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProjeto;

    @Column(name = "nome_projeto", nullable = false, length = 50)
    private String nomeProjeto;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @ManyToMany
    @JoinTable(name = "tbl_funcionario_projeto",
            joinColumns = @JoinColumn(name = "id_projeto"),
            inverseJoinColumns = @JoinColumn(name = "id_func"))
    private List<FuncionarioModel> equipeProjeto;

    public Integer getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public List<FuncionarioModel> getEquipeProjeto() {
        return equipeProjeto;
    }

    public void setEquipeProjeto(List<FuncionarioModel> equipeProjeto) {
        this.equipeProjeto = equipeProjeto;
    }
}
