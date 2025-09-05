package init.fast.hablamosele.categorias;

import init.fast.hablamosele.exception.CategoriaNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Categoria ajouterCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria modifierCategoria(Long id, Categoria maj) {
        Categoria existant = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        existant.setNombre(maj.getNombre());

        return categoriaRepository.save(existant);
    }

    @Transactional
    public void supprimerCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException(id);
        }
        categoriaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Categoria> listerCategoria() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria obtenirParIdCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }
}
