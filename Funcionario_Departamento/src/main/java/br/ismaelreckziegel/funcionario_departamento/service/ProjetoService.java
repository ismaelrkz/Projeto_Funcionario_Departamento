package br.ismaelreckziegel.funcionario_departamento.service;

import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoEquipeDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoEquipeRequestDTO;
import br.ismaelreckziegel.funcionario_departamento.dto.projeto.ProjetoRequestDTO;
import br.ismaelreckziegel.funcionario_departamento.exceptions.BadRequestException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.ConflictException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.NotFoundException;
import br.ismaelreckziegel.funcionario_departamento.model.FuncionarioModel;
import br.ismaelreckziegel.funcionario_departamento.model.ProjetoModel;
import br.ismaelreckziegel.funcionario_departamento.repository.FuncionarioRepository;
import br.ismaelreckziegel.funcionario_departamento.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public ProjetoService(ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
        this.projetoRepository = projetoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public ProjetoDTO create(ProjetoRequestDTO projeto) {
        ProjetoModel novoProjeto = new ProjetoModel();

        if (projeto.nome() == null || projeto.nome().isBlank()) {
            throw new BadRequestException("Nome do Projeto é obrigatório!");
        }

        Optional<ProjetoModel> existingNome = projetoRepository.findByNomeProjeto(projeto.nome());
        if (existingNome.isPresent()) {
            throw new ConflictException("Projeto já criado na base de dados!");
        }
        novoProjeto.setNomeProjeto(projeto.nome());

        if(projeto.data() == null){
            throw new BadRequestException("Data inicial do projeto obrigatório");
        }
        novoProjeto.setDataInicio(projeto.data());

        ProjetoModel projetoSalvo = projetoRepository.save(novoProjeto);

        return new ProjetoDTO(projetoSalvo);
    }

    public List<ProjetoDTO> readAll(){
        List<ProjetoModel> projetos = projetoRepository.findAll();

        return projetos.stream().map(ProjetoDTO::new).toList();
    }

    public ProjetoEquipeDTO readById(Integer id){
        ProjetoModel existing = projetoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        return new ProjetoEquipeDTO(existing);
    }

    public ProjetoEquipeDTO readByName(String projeto){
        ProjetoModel existing = projetoRepository.findByNomeProjeto(projeto)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        return new ProjetoEquipeDTO(existing);
    }

    public ProjetoDTO updateById(Integer id, ProjetoRequestDTO updateProjeto){
        ProjetoModel existing = projetoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        if(updateProjeto.nome() != null && !updateProjeto.nome().isBlank()){

            Optional<ProjetoModel> existingNome = projetoRepository.findByNomeProjeto(updateProjeto.nome());

            if(existingNome.isPresent() && !existingNome.get().getIdProjeto().equals(id)){
                throw new ConflictException("Já existe outro projeto com o nome informado!");
            }

            existing.setNomeProjeto(updateProjeto.nome());
        }

        if(updateProjeto.data() != null){
            existing.setDataInicio(updateProjeto.data());
        }

        ProjetoModel projetoUpdate = projetoRepository.save(existing);

        return new ProjetoDTO(projetoUpdate);
    }

    public ProjetoEquipeDTO updateEquipeById(Integer id, ProjetoEquipeRequestDTO updateEquipeProjeto){

        ProjetoModel existing = projetoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado!"));

        if (updateEquipeProjeto.id() != null && !updateEquipeProjeto.id().isEmpty()) {

            List<Integer> ids = updateEquipeProjeto.id();

            List<FuncionarioModel> funcionariosDoBanco = funcionarioRepository.findAllById(ids);

            if (funcionariosDoBanco.size() != ids.size()) {
                throw new NotFoundException("Um ou mais funcionários informados para a equipe não foram encontrados!");
            }

            if(funcionariosDoBanco.stream().anyMatch(existing.getEquipeProjeto()::contains)){
                throw new ConflictException("Um ou mais funcionários informados já estão na equipe!");
            }

            existing.getEquipeProjeto().addAll(funcionariosDoBanco);
        }

        ProjetoModel equipeUpdate = projetoRepository.save(existing);

        return new ProjetoEquipeDTO(equipeUpdate);
    }

    public void deleteById(Integer id){
        if(!projetoRepository.existsById(id)){
            throw new NotFoundException("Projeto não encontrado!");
        }

        projetoRepository.deleteById(id);
    }

}
