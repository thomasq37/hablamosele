package init.fast.hablamosele.exception;

public class NewsletterSubscriberNotFoundException extends RuntimeException {
    public NewsletterSubscriberNotFoundException(Long id) {
        super("NewsletterSubscriber introuvable avec id=" + id);
    }
}
