package init.fast.hablamosele.auth;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import init.fast.hablamosele.role.Role;
import init.fast.hablamosele.role.RoleRepository;
import init.fast.hablamosele.security.CustomUtilisateurDetailsService;
import init.fast.hablamosele.security.jwt.JwtUtils;
import init.fast.hablamosele.utilisateur.Utilisateur;
import init.fast.hablamosele.utilisateur.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CustomUtilisateurDetailsService customUtilisateurDetailsService;
    private final TentativeBlocageService tentativeBlocageService;

    public AuthController(UtilisateurRepository utilisateurRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils,
                          CustomUtilisateurDetailsService customUtilisateurDetailsService,
                          TentativeBlocageService tentativeBlocageService) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.customUtilisateurDetailsService = customUtilisateurDetailsService;
        this.tentativeBlocageService = tentativeBlocageService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> registerUser(@RequestBody Utilisateur utilisateurRequest, HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            if (!tentativeBlocageService.peutTenterInscription(ip)) {
                return ResponseEntity.badRequest().body("Trop de tentatives d'inscription depuis cette adresse IP, veuillez réessayer plus tard.");
            }

            // Vérification de la longueur du mot de passe
            if (utilisateurRequest.getMdp().length() < 10) {
                return ResponseEntity.badRequest().body("Erreur: Le mot de passe doit contenir au moins 10 caractères.");
            }

            // Vérifier si le nom est déjà pris
            if (utilisateurRepository.existsByEmail(utilisateurRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Erreur: Ce mail est déjà pris!");
            }

            // Création et enregistrement de l'utilisateur
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(utilisateurRequest.getEmail());
            utilisateur.setMdp(passwordEncoder.encode(utilisateurRequest.getMdp()));

            // Déterminer le rôle en fonction du nombre d'utilisateurs déjà inscrits
            Role role;
            long nbUtilisateurs = utilisateurRepository.count();
            if (nbUtilisateurs == 0) {
                // Si c'est le premier utilisateur, lui donner le rôle ROLE_ADMIN
                role = roleRepository.findByNom("ROLE_ADMIN")
                        .orElseThrow(() -> new Exception("Rôle administrateur non trouvé"));
            } else {
                // Sinon, lui donner le rôle ROLE_USER par défaut
                role = roleRepository.findByNom("ROLE_USER")
                        .orElseThrow(() -> new Exception("Rôle utilisateur non trouvé"));
            }

            // Attribuer le rôle à l'utilisateur
            utilisateur.setRoles(Collections.singleton(role));

            Utilisateur utilisateurEnregistre = utilisateurRepository.save(utilisateur);
            // Enregistrer l'utilisateur avec les rôles

            return ResponseEntity.ok("Utilisateur enregistré avec succès!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

	@PostMapping("/connexion")
	public ResponseEntity<String> authenticateUser(@RequestBody Utilisateur loginRequest, HttpServletRequest request) {
        
		try {
			String ip = request.getRemoteAddr();
	        if (!tentativeBlocageService.peutTenterConnexion(ip)) {
	            return ResponseEntity.badRequest().body("Trop de tentatives de connexion depuis cette adresse IP, veuillez réessayer plus tard.");
	        }
			UserDetails userDetails = customUtilisateurDetailsService.loadUserByUsername(loginRequest.getEmail());
			if (passwordEncoder.matches(loginRequest.getMdp(), userDetails.getPassword())) {
				List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.toList(); 
				String jwt = jwtUtils.generateJwtToken(userDetails.getUsername(), roles);
				return ResponseEntity.ok(jwt);
			} else {
				return ResponseEntity.badRequest().body("Erreur: Email ou mot de passe incorrect!");
			}
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.badRequest().body("Erreur: Email ou mot de passe incorrect!");
		}
	}
}
