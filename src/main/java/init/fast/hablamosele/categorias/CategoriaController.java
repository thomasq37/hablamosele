package init.fast.hablamosele.categorias;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Categoria ajouterCategoria(@RequestBody Categoria categoria) {
        return categoriaService.ajouterCategoria(categoria);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Categoria modifierCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaService.modifierCategoria(id, categoria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void supprimerCategoria(@PathVariable Long id) {
        categoriaService.supprimerCategoria(id);
    }

    @GetMapping
    public List<Categoria> listerCategoria() {
        return categoriaService.listerCategoria();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Categoria obtenirParIdCategoria(@PathVariable Long id) {
        return categoriaService.obtenirParIdCategoria(id);
    }
}
