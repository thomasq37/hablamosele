package init.fast.hablamosele.niveles;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/niveles")
public class NivelController {

    private final NivelService nivelService;

    public NivelController(NivelService nivelService) {
        this.nivelService = nivelService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Nivel ajouterNivel(@RequestBody Nivel nivel) {
        return nivelService.ajouterNivel(nivel);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Nivel modifierNivel(@PathVariable Long id, @RequestBody Nivel nivel) {
        return nivelService.modifierNivel(id, nivel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void supprimerNivel(@PathVariable Long id) {
        nivelService.supprimerNivel(id);
    }

    @GetMapping
    public List<Nivel> listerNivel() {
        return nivelService.listerNivel();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Nivel obtenirParIdNivel(@PathVariable Long id) {
        return nivelService.obtenirParIdNivel(id);
    }
}
