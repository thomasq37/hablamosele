package init.fast.hablamosele.recursos;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RecursosController {

    private final RecursosService recursosService;

    public RecursosController(RecursosService recursosService) {
        this.recursosService = recursosService;
    }

    @PostMapping
    public Recursos ajouterRecursos(@RequestBody Recursos recursos) {
        return recursosService.ajouterRecursos(recursos);
    }

    @PutMapping("/{id}")
    public Recursos modifierRecursos(@PathVariable Long id, @RequestBody Recursos recursos) {
        return recursosService.modifierRecursos(id, recursos);
    }

    @DeleteMapping("/{id}")
    public void supprimerRecursos(@PathVariable Long id) {
        recursosService.supprimerRecursos(id);
    }

    @GetMapping
    public List<Recursos> listerRecursos() {
        return recursosService.listerRecursos();
    }

    @GetMapping("/{id}")
    public Recursos obtenirParIdRecursos(@PathVariable Long id) {
        return recursosService.obtenirParIdRecursos(id);
    }
}
