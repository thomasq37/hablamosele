package init.fast.hablamosele.recursos;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursosController {

    private final RecursosService recursosService;

    public RecursosController(RecursosService recursosService) {
        this.recursosService = recursosService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Recursos ajouterRecursos(@RequestBody Recursos recursos) {
        return recursosService.ajouterRecursos(recursos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Recursos modifierRecursos(@PathVariable Long id, @RequestBody Recursos recursos) {
        return recursosService.modifierRecursos(id, recursos);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void supprimerRecursos(@PathVariable Long id) {
        recursosService.supprimerRecursos(id);
    }

    @GetMapping
    public List<Recursos> listerRecursos() {
        return recursosService.listerRecursos();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Recursos obtenirParIdRecursos(@PathVariable Long id) {
        return recursosService.obtenirParIdRecursos(id);
    }

    /** --- STATS (ADMIN SEULEMENT) --- */
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<RecursosStatistiquesDTO> listerStatsRecursos() {
        return recursosService.listerStatsRecursos();
    }

    @PostMapping("/{id}/ajouter-visualisation")
    public RecursosStatistiquesDTO incrementerVue(@PathVariable Long id) {
        return recursosService.incrementerNbVisualisations(id);
    }
}
