package init.fast.hablamosele.recursos;

import init.fast.hablamosele.exception.RecursosNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RecursosService {

    private final RecursosRepository recursosRepository;

    public RecursosService(RecursosRepository recursosRepository) {
        this.recursosRepository = recursosRepository;
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
    public List<Recursos> listerRecursos() {
        return recursosRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Recursos obtenirParIdRecursos(Long id) {
        return recursosRepository.findById(id)
                .orElseThrow(() -> new RecursosNotFoundException(id));
    }
    /** Statistiques */
    @Transactional(readOnly = true)
    public List<RecursosStatistiquesDTO> listerStatsRecursos() {
        List<Recursos> all = recursosRepository.findAll();
        return RecursosMapper.toStatDTOList(all);
    }

    /** +1 vue (public) – incrément atomique via requête UPDATE */
    @Transactional
    public RecursosStatistiquesDTO incrementerNbVisualisations(Long id) {
        int updated = recursosRepository.incrementViews(id);
        if (updated == 0) throw new RecursosNotFoundException(id);

        Recursos r = recursosRepository.findById(id)
                .orElseThrow(() -> new RecursosNotFoundException(id));

        // sécurité anti-null
        if (r.getNbVisualisaciones() == null) r.setNbVisualisaciones(0);

        return RecursosMapper.toStatDTO(r);
    }
}
