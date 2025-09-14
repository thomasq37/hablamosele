package init.fast.hablamosele.newsletter;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/newsletter-subscriber")
public class NewsletterSubscriberController {

    private final NewsletterSubscriberService newsletterSubscriberService;

    public NewsletterSubscriberController(NewsletterSubscriberService newsletterSubscriberService) {
        this.newsletterSubscriberService = newsletterSubscriberService;
    }

    @PostMapping("/ajouter")
    public NewsletterSubscriber ajouterNewsletterSubscriber(@RequestBody NewsletterSubscriber newsletterSubscriber) {
        return newsletterSubscriberService.ajouterNewsletterSubscriber(newsletterSubscriber);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NewsletterSubscriber modifierNewsletterSubscriber(@PathVariable Long id, @RequestBody NewsletterSubscriber newsletterSubscriber) {
        return newsletterSubscriberService.modifierNewsletterSubscriber(id, newsletterSubscriber);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void supprimerNewsletterSubscriber(@PathVariable Long id) {
        newsletterSubscriberService.supprimerNewsletterSubscriber(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<NewsletterSubscriber> listerNewsletterSubscriber() {
        return newsletterSubscriberService.listerNewsletterSubscriber();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NewsletterSubscriber obtenirParIdNewsletterSubscriber(@PathVariable Long id) {
        return newsletterSubscriberService.obtenirParIdNewsletterSubscriber(id);
    }
}
