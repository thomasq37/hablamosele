package init.fast.hablamosele.recursos;

import init.fast.hablamosele.exception.RecursosNotFoundException;
import init.fast.hablamosele.exception.ResourceNotFoundException;
import init.fast.hablamosele.statistiques.RecursosVisualisacion;
import init.fast.hablamosele.statistiques.RecursosVisualisacionDTO;
import init.fast.hablamosele.statistiques.RecursosVisualisacionMapper;
import init.fast.hablamosele.statistiques.RecursosVisualisacionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class RecursosService {

    private final RecursosRepository recursosRepository;
    private final RecursosVisualisacionRepository recursosVisualisacionRepository;

    public RecursosService(
            RecursosRepository recursosRepository,
            RecursosVisualisacionRepository recursosVisualisacionRepository) {
        this.recursosRepository = recursosRepository;
        this.recursosVisualisacionRepository = recursosVisualisacionRepository;
    }

    @Transactional
    public Recursos ajouterRecursos(Recursos recursos) {
        return recursosRepository.save(recursos);
    }

    @Transactional
    public Recursos modifierRecursos(Long id, Recursos maj) {
        Recursos existant = recursosRepository.findById(id)
                .orElseThrow(() -> new RecursosNotFoundException(id));

        existant.setBanner(maj.getBanner());
        existant.setTitulo(maj.getTitulo());
        existant.setDescription(maj.getDescription());
        existant.setTags(maj.getTags());
        existant.setInfografias(maj.getInfografias());
        existant.setCategorias(maj.getCategorias());
        existant.setNiveles(maj.getNiveles());
        existant.setNbInfografias(maj.getNbInfografias());
        existant.setNbCahiersActivite(maj.getNbCahiersActivite());

        return recursosRepository.save(existant);
    }

    @Transactional
    public void supprimerRecursos(Long id) {
        if (!recursosRepository.existsById(id)) {
            throw new RecursosNotFoundException(id);
        }
        recursosRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RecursosDTO> listerRecursos() {
        List<Recursos> all = recursosRepository.findAll();
        return RecursosMapper.toDTOList(all);
    }

    @Transactional(readOnly = true)
    public List<RecursosDTO> listerDernieresInfographies() {
        List<Recursos> derniers = recursosRepository.findTop3ByOrderByIdDesc();
        return RecursosMapper.toDTOList(derniers);
    }


    @Transactional(readOnly = true)
    public Recursos obtenirParIdRecursos(Long id) {
        return recursosRepository.findById(id)
                .orElseThrow(() -> new RecursosNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<String> obtenirInfografiasIdRecursos(Long id) {
        // Vérifier si la ressource existe
        if (!recursosRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recursos not found with id: " + id);
        }

        // Récupérer les infographies (méthode optimisée)
        List<String> infografias = recursosRepository.findInfografiasByRecursosId(id);
        return infografias != null ? infografias : Collections.emptyList();
    }

    @Transactional
    public void ajouterVisualisacion(Long recursosId, String ipAddress) {
        // Vérifier que la ressource existe
        Recursos recursos = recursosRepository.findById(recursosId)
                .orElseThrow(() -> new ResourceNotFoundException("Recursos not found with id: " + recursosId));

        // Créer une nouvelle visualisation
        RecursosVisualisacion visualisation = new RecursosVisualisacion();
        visualisation.setRecursos(recursos);
        visualisation.setDateVisualisacion(LocalDateTime.now());
        visualisation.setIp(ipAddress);

        // Sauvegarder la visualisation
        recursosVisualisacionRepository.save(visualisation);
    }

    @Transactional(readOnly = true)
    public List<RecursosVisualisacionDTO> obtenirToutesVisualisations() {
        List<RecursosVisualisacion> visualisations = recursosVisualisacionRepository
                .findAll();

        return RecursosVisualisacionMapper.toDTOList(visualisations);
    }
}
