package init.fast.hablamosele.niveles;

import init.fast.hablamosele.exception.NivelNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NivelService {

    private final NivelRepository nivelRepository;

    public NivelService(NivelRepository nivelRepository) {
        this.nivelRepository = nivelRepository;
    }

    @Transactional
    public Nivel ajouterNivel(Nivel nivel) {
        return nivelRepository.save(nivel);
    }

    @Transactional
    public Nivel modifierNivel(Long id, Nivel maj) {
        Nivel existant = nivelRepository.findById(id)
                .orElseThrow(() -> new NivelNotFoundException(id));

        existant.setNombre(maj.getNombre());

        return nivelRepository.save(existant);
    }

    @Transactional
    public void supprimerNivel(Long id) {
        if (!nivelRepository.existsById(id)) {
            throw new NivelNotFoundException(id);
        }
        nivelRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Nivel> listerNivel() {
        return nivelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Nivel obtenirParIdNivel(Long id) {
        return nivelRepository.findById(id)
                .orElseThrow(() -> new NivelNotFoundException(id));
    }
}
