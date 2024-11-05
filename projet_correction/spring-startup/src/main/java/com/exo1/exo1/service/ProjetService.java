package com.exo1.exo1.service;

import com.exo1.exo1.dto.ProjetDto;
import com.exo1.exo1.entity.Projet;
import com.exo1.exo1.mapper.ProjetMapper;
import com.exo1.exo1.repository.ProjetRepository;
import com.exo1.exo1.repository.TaskRepository;
import jdk.internal.org.objectweb.asm.tree.analysis.Frame;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.awt.print.Pageable;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjetService {
    JdbcTemplate jdbcTemplate;
    private ProjetRepository projetRepository;
    private ProjetMapper projetMapper;
    private TaskRepository taskRepository;

    public void refreshMaterializedView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW projet_task_number");
    }

    @Cacheable(value = "projet")
    public List<ProjetDto> findAll(Pageable pageable) {
        return projetRepository.findAll((Sort) pageable).map(projetMapper::toDto).getContent();
        //return projetMapper.toDtos(projetRepository.findAll());
    }

    public ProjetDto findById(long id) {
        return projetMapper.toDto(projetRepository.findById(id).orElse(null));
    }

    public ProjetDto save(ProjetDto projetDto) {
        Projet projet = projetMapper.toEntity(projetDto);
        projet.getTasks().stream().forEach(t -> t.setProjet(projet));
        return projetMapper.toDto(projetRepository.save(projet));
    }

    public ProjetDto update(Long id, ProjetDto projetDto) {
        Projet existingProjet = projetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet not found with id " + id));
        projetDto.setId(existingProjet.getId());
        Projet projetUpdated = projetMapper.toEntity(projetDto);
        projetUpdated.getTasks().stream().forEach(t -> {
            if(taskRepository.existsById(t.getId())) {
                t.setProjet(projetUpdated);
            }
        });
        return projetMapper.toDto(projetRepository.save(projetUpdated));
    }

    public void delete(Long id) {
        projetRepository.deleteById(id);
    }
}
