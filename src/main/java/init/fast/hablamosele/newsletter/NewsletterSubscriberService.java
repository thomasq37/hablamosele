package init.fast.hablamosele.newsletter;

import init.fast.hablamosele.exception.NewsletterSubscriberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsletterSubscriberService {

    private final NewsletterSubscriberRepository newsletterSubscriberRepository;

    public NewsletterSubscriberService(NewsletterSubscriberRepository newsletterSubscriberRepository) {
        this.newsletterSubscriberRepository = newsletterSubscriberRepository;
    }

    @Transactional
    public NewsletterSubscriber ajouterNewsletterSubscriber(NewsletterSubscriber newsletterSubscriber) {
        return newsletterSubscriberRepository.save(newsletterSubscriber);
    }

    @Transactional
    public NewsletterSubscriber modifierNewsletterSubscriber(Long id, NewsletterSubscriber maj) {
        NewsletterSubscriber existant = newsletterSubscriberRepository.findById(id)
                .orElseThrow(() -> new NewsletterSubscriberNotFoundException(id));

        existant.setEmail(maj.getEmail());
        return newsletterSubscriberRepository.save(existant);
    }

    @Transactional
    public void supprimerNewsletterSubscriber(Long id) {
        if (!newsletterSubscriberRepository.existsById(id)) {
            throw new NewsletterSubscriberNotFoundException(id);
        }
        newsletterSubscriberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<NewsletterSubscriber> listerNewsletterSubscriber() {
        return newsletterSubscriberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NewsletterSubscriber obtenirParIdNewsletterSubscriber(Long id) {
        return newsletterSubscriberRepository.findById(id)
                .orElseThrow(() -> new NewsletterSubscriberNotFoundException(id));
    }
}
