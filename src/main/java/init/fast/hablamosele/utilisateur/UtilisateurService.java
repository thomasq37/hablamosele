package init.fast.hablamosele.utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurService(
    		UtilisateurRepository utilisateurRepository, 
    		PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public Utilisateur obtenirUtilisateur(String email) {
    	
		// Trouver l'utilisateur pour s'assurer que la tranche lui appartient
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return utilisateur;
	}

    public Utilisateur modifierUtilisateur(String email, Utilisateur utilisateurModifie) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifie si l'email est modifié, s'il n'est pas pris ou s'il appartient à l'utilisateur actuel
        if (utilisateurModifie.getEmail() != null && !utilisateurModifie.getEmail().isEmpty() && !utilisateur.getEmail().equals(utilisateurModifie.getEmail())) {
            boolean emailExists = utilisateurRepository.findByEmail(utilisateurModifie.getEmail()).isPresent();
            if (emailExists) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'email est déjà utilisé par un autre compte.");
            }
            utilisateur.setEmail(utilisateurModifie.getEmail());
        }

        // Vérifie si le mot de passe fait au moins dix caractères
        if (utilisateurModifie.getMdp() != null && !utilisateurModifie.getMdp().isEmpty()) {
            if (utilisateurModifie.getMdp().length() < 10) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir au moins 10 caractères.");
            }
            utilisateur.setMdp(passwordEncoder.encode(utilisateurModifie.getMdp()));
        }

        return utilisateurRepository.save(utilisateur);
    }

    public void supprimerCompte(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        utilisateurRepository.delete(utilisateur);
    }

    public void supprimerUtilisateurParAdmin(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        utilisateurRepository.delete(utilisateur);
    }
}
