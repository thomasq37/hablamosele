package init.fast.hablamosele.recursos;

import init.fast.hablamosele.exception.ResourceNotFoundException;
import init.fast.hablamosele.statistiques.RecursosVisualisacionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Recursos> ajouterRecursos(@RequestBody Recursos recursos) {
        try {
            Recursos nouveauRecursos = recursosService.ajouterRecursos(recursos);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauRecursos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Recursos> modifierRecursos(@PathVariable Long id, @RequestBody Recursos recursos) {
        try {
            Recursos recursosModifie = recursosService.modifierRecursos(id, recursos);
            return ResponseEntity.ok(recursosModifie);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> supprimerRecursos(@PathVariable Long id) {
        try {
            recursosService.supprimerRecursos(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RecursosDTO>> listerRecursos() {
        try {
            List<RecursosDTO> recursos = recursosService.listerRecursos();
            return ResponseEntity.ok(recursos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/ultimas")
    public ResponseEntity<List<RecursosDTO>> listerDernieresInfographies() {
        try {
            List<RecursosDTO> recursos = recursosService.listerDernieresInfographies();
            return ResponseEntity.ok(recursos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Recursos> obtenirParIdRecursos(@PathVariable Long id) {
        try {
            Recursos recursos = recursosService.obtenirParIdRecursos(id);
            return ResponseEntity.ok(recursos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/infografias")
    public ResponseEntity<List<String>> obtenirInfografiasIdRecursos(@PathVariable Long id) {
        try {
            List<String> infografias = recursosService.obtenirInfografiasIdRecursos(id);
            return ResponseEntity.ok(infografias);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/ajouter-visualisation")
    public ResponseEntity<Void> ajouterVisualisacion(@PathVariable Long id, HttpServletRequest request) {
        try {
            String ipAddress = getClientIpAddress(request);
            recursosService.ajouterVisualisacion(id, ipAddress);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Méthode utilitaire pour obtenir l'IP du client
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIP = request.getHeader("X-Real-IP");
        if (xRealIP != null && !xRealIP.isEmpty()) {
            return xRealIP;
        }

        return request.getRemoteAddr();
    }

    @GetMapping("/visualisations")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RecursosVisualisacionDTO>> obtenirToutesVisualisations() {
        try {
            List<RecursosVisualisacionDTO> visualisations = recursosService.obtenirToutesVisualisations();
            return ResponseEntity.ok(visualisations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}