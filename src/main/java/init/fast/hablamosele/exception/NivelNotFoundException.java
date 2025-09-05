package init.fast.hablamosele.exception;

public class NivelNotFoundException extends RuntimeException {
    public NivelNotFoundException(Long id) {
        super("Nivel introuvable avec id=" + id);
    }
}
